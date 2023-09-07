package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.VehicleRecord;
import com.kenzie.appserver.repositories.RentalRepository;
import com.kenzie.appserver.repositories.model.VehicleType;
import com.kenzie.appserver.service.model.Vehicle;

import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.ExampleData;
import org.springframework.stereotype.Service;

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
        ExampleData dataFromLambda = lambdaServiceClient.getExampleData(id);

        // Example getting data from the local repository
        Vehicle dataFromDynamo = rentalRepository
                .findById(id)
                .map(example -> new Vehicle(example.getId(), example.getName(),
                        example.getDescription(),example.getRetalPrice(),
                        example.getMileage(),example.getVehicleType(),
                        example.getMake(), example.getImages()))
                .orElse(null);

        return dataFromDynamo;
    }

    public Vehicle addNewExample(String name) {
        // Example sending data to the lambda
        ExampleData dataFromLambda = lambdaServiceClient.setExampleData(name);

        // Example sending data to the local repository
        VehicleRecord vehicleRecord = new VehicleRecord();
        vehicleRecord.setId(dataFromLambda.getId());
        vehicleRecord.setName(dataFromLambda.getData());
        rentalRepository.save(vehicleRecord);

        Vehicle vehicle = new Vehicle(dataFromLambda.getId(), name, "",
                10.0, 2424.2, VehicleType.COUPE, "", "" );
        return vehicle;
    }
}
