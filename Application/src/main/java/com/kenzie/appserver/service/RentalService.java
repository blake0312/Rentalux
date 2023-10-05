package com.kenzie.appserver.service;

import com.google.gson.Gson;
import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.repositories.model.VehicleRecord;
import com.kenzie.appserver.repositories.RentalRepository;
import com.kenzie.appserver.service.model.Vehicle;

import com.kenzie.appserver.service.model.VehicleWithLambdaInfo;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.ReservationData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
        List<ReservationData> dataFromLambda = lambdaServiceClient.getReservationData(id);

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

    public List<Vehicle> getAllVehicles() {
        Iterable<VehicleRecord> vehicleRecords = rentalRepository.findAll();
        List<Vehicle> vehicles = new ArrayList<>();
        for (VehicleRecord vehicleRecord : vehicleRecords) {
            vehicles.add(convertToVehicle(vehicleRecord));
        }
        return vehicles;
    }

    public ReservationData addNewReservation(ReservationData reservationData){
        Gson gson = new Gson();
        String data = gson.toJson(reservationData);

        return lambdaServiceClient.setReservationData(data);
    }

    public ReservationData updateReservation(ReservationData reservationData){
        Gson gson = new Gson();
        String data = gson.toJson(reservationData);

        return lambdaServiceClient.updateReservationData(data);
    }

    public void deleteReservation(String id){
        cache.evict(id);
        lambdaServiceClient.deleteReservationData(id);
    }

    public List<ReservationData> getAllReservation(String id){

        List<ReservationData> data = cache.get(id);

        if(data != null){
            return data;
        }

        data = lambdaServiceClient.getReservationData(id);

        if(data != null){
            cache.add(id, data);
        }

        return data;
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
}
