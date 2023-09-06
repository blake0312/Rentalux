package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.RentalRecord;
import com.kenzie.appserver.repositories.RentalRepository;
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
                .map(example -> new Vehicle(example.getId(), example.getName()))
                .orElse(null);

        return dataFromDynamo;
    }

    public Vehicle addNewExample(String name) {
        // Example sending data to the lambda
        ExampleData dataFromLambda = lambdaServiceClient.setExampleData(name);

        // Example sending data to the local repository
        RentalRecord rentalRecord = new RentalRecord();
        rentalRecord.setId(dataFromLambda.getId());
        rentalRecord.setName(dataFromLambda.getData());
        rentalRepository.save(rentalRecord);

        Vehicle vehicle = new Vehicle(dataFromLambda.getId(), name);
        return vehicle;
    }
}
