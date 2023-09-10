package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RentalCreateRequest;
import com.kenzie.appserver.controller.model.RentalResponse;
import com.kenzie.appserver.repositories.model.VehicleRecord;
import com.kenzie.appserver.service.RentalService;

import com.kenzie.appserver.service.model.Vehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rental")
public class RentalController {

    private RentalService rentalService;

    RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> get(@PathVariable("id") String id) {

        Vehicle vehicle = rentalService.findById(id);
        if (vehicle == null) {
            return ResponseEntity.notFound().build();
        }

        RentalResponse rentalResponse = rentalResponseHelper(vehicle);

        return ResponseEntity.ok(rentalResponse);
    }

    @PostMapping
    public ResponseEntity<RentalResponse> addNewRental(@RequestBody RentalCreateRequest rentalCreateRequest) {
        Vehicle vehicle = rentalService.addNewVehicle(convertToVehicle(rentalCreateRequest));

        RentalResponse rentalResponse = rentalResponseHelper(vehicle);

        return ResponseEntity.ok(rentalResponse);
    }

    @PostMapping("/all")
    public ResponseEntity<List<RentalResponse>> getAll(){
        List<Vehicle> vehicle = rentalService.getAll();

        return ResponseEntity.ok(vehicle.stream()
                .map(this::rentalResponseHelper)
                .collect(Collectors.toList()));

    }

    public Vehicle convertToVehicle(RentalCreateRequest rentalCreateRequest){
        Vehicle vehicle = new Vehicle(UUID.randomUUID().toString(), rentalCreateRequest.getName(),
                rentalCreateRequest.getDescription(), rentalCreateRequest.getRetailPrice(),
                rentalCreateRequest.getMileage(),rentalCreateRequest.getVehicleType(),
                rentalCreateRequest.getMake(), rentalCreateRequest.getImages());
        return  vehicle;
    }

    public RentalResponse rentalResponseHelper(Vehicle vehicle){

        RentalResponse rentalResponse = new RentalResponse();
        rentalResponse.setId(vehicle.getId());
        rentalResponse.setName(vehicle.getName());
        rentalResponse.setDescription(vehicle.getDescription());
        rentalResponse.setMake(vehicle.getMake());
        rentalResponse.setImages(vehicle.getImages());
        rentalResponse.setMileage(vehicle.getMileage());
        rentalResponse.setRetailPrice(vehicle.getRetailPrice());
        rentalResponse.setVehicleType(vehicle.getVehicleType());

        return rentalResponse;
    }
}
