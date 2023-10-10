package com.kenzie.appserver.service;

import com.google.gson.Gson;
import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.repositories.model.VehicleRecord;
import com.kenzie.appserver.repositories.RentalRepository;
import com.kenzie.appserver.service.model.Reservation;
import com.kenzie.appserver.service.model.Vehicle;

import com.kenzie.appserver.service.model.VehicleWithLambdaInfo;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.ReservationData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RentalService {
    private RentalRepository rentalRepository;
    private LambdaServiceClient lambdaServiceClient;
    private CacheStore cache;

    public RentalService(RentalRepository rentalRepository, LambdaServiceClient lambdaServiceClient, CacheStore cache) {
        this.rentalRepository = rentalRepository;
        this.lambdaServiceClient = lambdaServiceClient;
        this.cache = cache;
    }

    public VehicleWithLambdaInfo findById(String id) {
        List<Reservation> dataFromLambda = lambdaServiceClient.getReservationData(id).stream()
                .map(this::convertToReservation)
                .collect(Collectors.toList());

        Vehicle dataFromDynamo = rentalRepository
                .findById(id)
                .map(this::convertToVehicle)
                .orElse(null);

        return new VehicleWithLambdaInfo(dataFromDynamo, dataFromLambda);
    }

    public Vehicle addNewVehicle(Vehicle vehicle) {
        VehicleRecord vehicleRecord = convertToVehicleRecord(vehicle);
        rentalRepository.save(vehicleRecord);

        return vehicle;
    }

    public void deleteVehicle(String id) {
        rentalRepository.deleteById(id);
    }

    public List<Vehicle> getAllVehicles() {
        Iterable<VehicleRecord> vehicleRecords = rentalRepository.findAll();
        List<Vehicle> vehicles = new ArrayList<>();
        for (VehicleRecord vehicleRecord : vehicleRecords) {
            vehicles.add(convertToVehicle(vehicleRecord));
        }
        return vehicles;
    }

    public Reservation addNewReservation(Reservation reservation){
        Gson gson = new Gson();
        String data = gson.toJson(reservation);
        cache.evict("all");
        return convertToReservation(lambdaServiceClient.setReservationData(data));
    }

    public Reservation updateReservation(Reservation reservation){
        Gson gson = new Gson();
        String data = gson.toJson(reservation);
        cache.evict("all");
        return convertToReservation(lambdaServiceClient.updateReservationData(data));
    }

    public void deleteReservation(String id){
        cache.evict("all");
        lambdaServiceClient.deleteReservationData(id);
    }

    public List<Reservation> getAllReservation(String id){

        List<Reservation> data = cache.get(id);

        if(data != null){
            return data;
        }

        data = lambdaServiceClient.getReservationData(id).stream()
                .map(this::convertToReservation)
                .collect(Collectors.toList());

        if(data != null){
            cache.add(id, data);
        }

        return data;
    }

    public List<Reservation> getAllCustomerReservations(String id) {
        return lambdaServiceClient.getReservationData("customerId," + id)
                .stream()
                .map(this::convertToReservation)
                .collect(Collectors.toList());
    }

    public Vehicle convertToVehicle(VehicleRecord vehicleRecord) {
        Vehicle vehicle = new Vehicle(vehicleRecord.getId(), vehicleRecord.getName(),
                vehicleRecord.getDescription(), vehicleRecord.getRetalPrice(),
                vehicleRecord.getMileage(), vehicleRecord.getVehicleType(),
                vehicleRecord.getMake(), vehicleRecord.getImages());
        return vehicle;
    }

    public VehicleRecord convertToVehicleRecord(Vehicle vehicle) {
        VehicleRecord vehicleRecord = new VehicleRecord();
        vehicleRecord.setId(vehicle.getId());
        vehicleRecord.setName(vehicle.getName());
        vehicleRecord.setDescription(vehicle.getDescription());
        vehicleRecord.setRetalPrice(vehicle.getRetailPrice());
        vehicleRecord.setMileage(vehicle.getMileage());
        vehicleRecord.setVehicleType(vehicle.getVehicleType());
        vehicleRecord.setMake(vehicle.getMake());
        vehicleRecord.setImages(vehicle.getImages());
        return vehicleRecord;
    }

    public Reservation convertToReservation(ReservationData reservationData){
        return new Reservation(reservationData.getId(), reservationData.getCustomerId(),
                reservationData.isPayed(), reservationData.getVehicleId(), reservationData.getStartData(),
                reservationData.getEndData());

    }
}
