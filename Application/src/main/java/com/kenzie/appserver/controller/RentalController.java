package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.LambdaReservationCreateRequest;
import com.kenzie.appserver.controller.model.LambdaReservationResponse;
import com.kenzie.appserver.controller.model.RentalCreateRequest;
import com.kenzie.appserver.controller.model.RentalResponse;
import com.kenzie.appserver.service.RentalService;

import com.kenzie.appserver.service.model.Reservation;
import com.kenzie.appserver.service.model.Vehicle;
import com.kenzie.appserver.service.model.VehicleWithLambdaInfo;
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
        VehicleWithLambdaInfo vehicle = rentalService.findById(id);
        if (vehicle == null) {
            return ResponseEntity.notFound().build();
        }

        RentalResponse rentalResponse = rentalResponseHelperWithLambda(vehicle);

        return ResponseEntity.ok(rentalResponse);
    }

    @PostMapping
    public ResponseEntity<RentalResponse> addNewRental(@RequestBody RentalCreateRequest rentalCreateRequest) {
        Vehicle vehicle = rentalService.addNewVehicle(convertToVehicle(rentalCreateRequest));

        RentalResponse rentalResponse = rentalResponseHelperNonLambda(vehicle);

        return ResponseEntity.ok(rentalResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RentalResponse>> getAll() {
        List<Vehicle> vehicle = rentalService.getAllVehicles();

        return ResponseEntity.ok(vehicle.stream()
                .map(this::rentalResponseHelperNonLambda)
                .collect(Collectors.toList()));

    }

    @PostMapping("/reservation")
    public ResponseEntity<LambdaReservationResponse> addNewReservation(@RequestBody LambdaReservationCreateRequest lambdaReservationCreateRequest) {
        Reservation data = rentalService.addNewReservation(convertToReservation(lambdaReservationCreateRequest));

        return ResponseEntity.ok(convertToReservationResponse(data));
    }

    @PutMapping("/reservation/{id}")
    public ResponseEntity<LambdaReservationResponse> updateReservation(@PathVariable String id, @RequestBody LambdaReservationCreateRequest lambdaReservationCreateRequest) {
        Reservation data = convertToReservationUpdate(id, lambdaReservationCreateRequest);
        Reservation reservationDataReturn = rentalService.updateReservation(data);

        return ResponseEntity.ok(convertToReservationResponse(reservationDataReturn));
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String id) {
        rentalService.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservation/all")
    public ResponseEntity<List<LambdaReservationResponse>> getAllReservation() {
        List<Reservation> reservationData = rentalService.getAllReservation("all");

        return ResponseEntity.ok(reservationData.stream()
                .map(this::convertToReservationResponse)
                .collect(Collectors.toList()));
    }


    public Vehicle convertToVehicle(RentalCreateRequest rentalCreateRequest) {
        Vehicle vehicle = new Vehicle(UUID.randomUUID().toString(), rentalCreateRequest.getName(),
                rentalCreateRequest.getDescription(), rentalCreateRequest.getRetailPrice(),
                rentalCreateRequest.getMileage(), rentalCreateRequest.getVehicleType(),
                rentalCreateRequest.getMake(), rentalCreateRequest.getImages());
        return vehicle;
    }

    public RentalResponse rentalResponseHelperWithLambda(VehicleWithLambdaInfo withLambdaInfo) {
        //Combine data from both vehicle and reservation data into rental response
        Vehicle vehicle = withLambdaInfo.getVehicle();

        List<Reservation> dataFromLambda = withLambdaInfo.getData();

        //Rental response is only set with vehicle data currently
        RentalResponse rentalResponse = rentalResponseHelperNonLambda(vehicle);

        //Add the attributes from data to response class and add them here with other setters.
        rentalResponse.setReservations(dataFromLambda);

        return rentalResponse;
    }

    public RentalResponse rentalResponseHelperNonLambda(Vehicle vehicle) {
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

    public Reservation convertToReservation(LambdaReservationCreateRequest request) {
        String id = "Jacobus";

        return new Reservation(id, request.getCustomerId(), request.isPayed(),
                request.getVehicleId(), request.getStartData(), request.getEndData());
    }

    public Reservation convertToReservationUpdate(String id, LambdaReservationCreateRequest request) {

        return new Reservation(id, request.getCustomerId(), request.isPayed(),
                request.getVehicleId(), request.getStartData(), request.getEndData());
    }

    public LambdaReservationResponse convertToReservationResponse(Reservation data) {
        LambdaReservationResponse converted = new LambdaReservationResponse();
        converted.setId(data.getId());
        converted.setCustomerId(data.getCustomerId());
        converted.setPayed(data.isPayed());
        converted.setVehicleId(data.getVehicleId());
        converted.setStartData(data.getStartData());
        converted.setEndData(data.getEndData());

        return converted;
    }
}
