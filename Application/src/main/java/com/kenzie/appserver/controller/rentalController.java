package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RentalCreateRequest;
import com.kenzie.appserver.controller.model.RentalResponse;
import com.kenzie.appserver.service.rentalService;

import com.kenzie.appserver.service.model.Vehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/example")
public class rentalController {

    private rentalService rentalService;

    rentalController(rentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> get(@PathVariable("id") String id) {

        Vehicle vehicle = rentalService.findById(id);
        if (vehicle == null) {
            return ResponseEntity.notFound().build();
        }

        RentalResponse rentalResponse = new RentalResponse();
        rentalResponse.setId(vehicle.getId());
        rentalResponse.setName(vehicle.getName());
        return ResponseEntity.ok(rentalResponse);
    }

    @PostMapping
    public ResponseEntity<RentalResponse> addNewExample(@RequestBody RentalCreateRequest rentalCreateRequest) {
        Vehicle vehicle = rentalService.addNewExample(rentalCreateRequest.getName());

        RentalResponse rentalResponse = new RentalResponse();
        rentalResponse.setId(vehicle.getId());
        rentalResponse.setName(vehicle.getName());

        return ResponseEntity.ok(rentalResponse);
    }
}
