package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.vehicleRecord;
import com.kenzie.appserver.repositories.rentalRepository;
import com.kenzie.appserver.service.model.Vehicle;

import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.ExampleData;
import org.springframework.stereotype.Service;

@Service
public class rentalService {
    private rentalRepository rentalRepository;
    private LambdaServiceClient lambdaServiceClient;

    public rentalService(rentalRepository rentalRepository, LambdaServiceClient lambdaServiceClient) {
        this.rentalRepository = rentalRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }

    public Vehicle findById(String id) {

        // Example getting data from the lambda
        ExampleData dataFromLambda = lambdaServiceClient.getExampleData(id);

        // Example getting data from the local repository
        Vehicle dataFromDynamo = rentalRepository
                .findById(id)
                .map(example -> new Vehicle(example.getId(), example.getName()))
                .orElse(null);

        return dataFromDynamo;
    }

    public Vehicle addNewExample(String name) {
        // Example sending data to the lambda
        ExampleData dataFromLambda = lambdaServiceClient.setExampleData(name);

        // Example sending data to the local repository
        vehicleRecord vehicleRecord = new vehicleRecord();
        vehicleRecord.setId(dataFromLambda.getId());
        vehicleRecord.setName(dataFromLambda.getData());
        rentalRepository.save(vehicleRecord);

        Vehicle vehicle = new Vehicle(dataFromLambda.getId(), name);
        return vehicle;
    }
}
