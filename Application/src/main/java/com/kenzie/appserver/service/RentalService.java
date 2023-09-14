package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.VehicleRecord;
import com.kenzie.appserver.repositories.RentalRepository;
import com.kenzie.appserver.repositories.model.VehicleType;
import com.kenzie.appserver.service.model.Vehicle;

import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.ExampleData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentalService {
    private RentalRepository rentalRepository;
    private LambdaServiceClient lambdaServiceClient;

    public RentalService(RentalRepository rentalRepository, LambdaServiceClient lambdaServiceClient) {
        this.rentalRepository = rentalRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }

    public Vehicle findById(String id) {

        // Example getting data from the lambda
        //ExampleData dataFromLambda = lambdaServiceClient.getExampleData(id);

        // Example getting data from the local repository
        Vehicle dataFromDynamo = rentalRepository
                .findById(id)
                .map(vehicleRecord -> new Vehicle(vehicleRecord.getId(), vehicleRecord.getName(),
                        vehicleRecord.getDescription(), vehicleRecord.getRetalPrice(),
                        vehicleRecord.getMileage(), vehicleRecord.getVehicleType(),
                        vehicleRecord.getMake(), vehicleRecord.getImages()))
                .orElse(null);

        return dataFromDynamo;
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
