package com.kenzie.appserver.service;

import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.repositories.RentalRepository;
import com.kenzie.appserver.repositories.model.VehicleRecord;
import com.kenzie.appserver.repositories.model.VehicleType;
import com.kenzie.appserver.service.model.Vehicle;
import com.kenzie.appserver.service.model.VehicleWithLambdaInfo;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class RentalServiceTest {
    private RentalRepository rentalRepository;
    private RentalService rentalService;
    private LambdaServiceClient lambdaServiceClient;
    private CacheStore cache;

    @BeforeEach
    void setup() {
        rentalRepository = mock(RentalRepository.class);
        lambdaServiceClient = mock(LambdaServiceClient.class);
        cache = mock(CacheStore.class);
        rentalService = new RentalService(rentalRepository, lambdaServiceClient, cache);
    }
    /** ------------------------------------------------------------------------
     *  exampleService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findById() {
        // GIVEN
        String id = randomUUID().toString();

        VehicleRecord record = new VehicleRecord();
        record.setId(id);
        record.setName("concertname");

        // WHEN
        when(rentalRepository.findById(id)).thenReturn(Optional.of(record));
        VehicleWithLambdaInfo vehicle = rentalService.findById(id);

        // THEN
        Assertions.assertNotNull(vehicle, "The object is returned");
        Assertions.assertEquals(record.getId(), vehicle.getVehicle().getId(), "The id matches");
        Assertions.assertEquals(record.getName(), vehicle.getVehicle().getName(), "The name matches");
    }

    @Test
    void findById_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(rentalRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        VehicleWithLambdaInfo vehicle = rentalService.findById(id);

        // THEN
        Assertions.assertNull(vehicle.getVehicle(), "The example is null when not found");
    }

    @Test
    void addNewVehicle(){
        // GIVEN
        String id = randomUUID().toString();
        List<String> images = new ArrayList<>();
        images.add("image1");
        images.add("image2");

        VehicleRecord vehicleRecord = new VehicleRecord();
        vehicleRecord.setId(id);
        vehicleRecord.setName("Porsche");
        vehicleRecord.setDescription("911");
        vehicleRecord.setRetalPrice(180000.00);
        vehicleRecord.setMileage(0.0);
        vehicleRecord.setVehicleType(VehicleType.COUPE);
        vehicleRecord.setMake("Porsche");
        vehicleRecord.setImages(images);

        Vehicle newVehicle = new Vehicle(vehicleRecord.getId(), vehicleRecord.getName(), vehicleRecord.getDescription(),
                vehicleRecord.getRetalPrice(), vehicleRecord.getMileage(), vehicleRecord.getVehicleType(),
                vehicleRecord.getMake(),vehicleRecord.getImages());

        //when(rentalRepository.save(vehicleRecord)).thenReturn(vehicleRecord);

        // WHEN
        Vehicle vehicle = rentalService.addNewVehicle(newVehicle);

        // THEN
        verify(rentalRepository, times(1)).save(any());
        Assertions.assertEquals(vehicle, newVehicle, "Vehicle added");
    }

    @Test
    void getAllVehicles() {
        // GIVEN
        String id = randomUUID().toString();
        List<String> images = new ArrayList<>();
        images.add("image1");
        images.add("image2");

        VehicleRecord vehicleRecord1 = new VehicleRecord();
        vehicleRecord1.setId(rentalService.toString());
        vehicleRecord1.setName("Porsche");
        vehicleRecord1.setDescription("911");
        vehicleRecord1.setRetalPrice(180000.00);
        vehicleRecord1.setMileage(0.0);
        vehicleRecord1.setVehicleType(VehicleType.COUPE);
        vehicleRecord1.setMake("Porsche");
        vehicleRecord1.setImages(images);

        VehicleRecord vehicleRecord2 = new VehicleRecord();
        vehicleRecord2.setId(rentalService.toString());
        vehicleRecord2.setName("Ferrari");
        vehicleRecord2.setDescription("Portofino");
        vehicleRecord2.setRetalPrice(246102.00);
        vehicleRecord2.setMileage(0.0);
        vehicleRecord2.setVehicleType(VehicleType.COUPE);
        vehicleRecord2.setMake("Ferrari");
        vehicleRecord2.setImages(images);

        List<VehicleRecord> vehicleList = new ArrayList<>();
        vehicleList.add(vehicleRecord1);
        vehicleList.add(vehicleRecord2);
        when(rentalRepository.findAll()).thenReturn(vehicleList);

        // WHEN
        List<Vehicle> vehicles = rentalService.getAllVehicles();

        // THEN
        Assertions.assertNotNull(vehicles, "The concert list is returned");
        Assertions.assertEquals(2, vehicles.size(), "There are two concerts");

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == vehicleRecord1.getId()) {
                Assertions.assertEquals(vehicleRecord1.getId(), vehicle.getId(), "The vehicle id matches");
                Assertions.assertEquals(vehicleRecord1.getName(), vehicle.getName(), "The vehicle name matches");
                Assertions.assertEquals(vehicleRecord1.getDescription(), vehicle.getDescription(), "The vehicle description matches");
                Assertions.assertEquals(vehicleRecord1.getRetalPrice(), vehicle.getRetailPrice(), "The vehicle price matches");
                Assertions.assertEquals(vehicleRecord1.getMileage(), vehicle.getMileage(), "The mileage matches");
                Assertions.assertEquals(vehicleRecord1.getVehicleType(), vehicle.getVehicleType(), "The vehicle type matches");
                Assertions.assertEquals(vehicleRecord1.getMake(), vehicle.getMake(), "The vehicle make matches");
                Assertions.assertEquals(vehicleRecord1.getImages(), vehicle.getImages(), "The vehicle images match");
            } else if (vehicle.getId() == vehicleRecord2.getId()) {
                Assertions.assertEquals(vehicleRecord2.getId(), vehicle.getId(), "The vehicle id matches");
                Assertions.assertEquals(vehicleRecord2.getName(), vehicle.getName(), "The vehicle name matches");
                Assertions.assertEquals(vehicleRecord2.getDescription(), vehicle.getDescription(), "The vehicle description matches");
                Assertions.assertEquals(vehicleRecord2.getRetalPrice(), vehicle.getRetailPrice(), "The vehicle price matches");
                Assertions.assertEquals(vehicleRecord2.getMileage(), vehicle.getMileage(), "The mileage matches");
                Assertions.assertEquals(vehicleRecord2.getVehicleType(), vehicle.getVehicleType(), "The vehicle type matches");
                Assertions.assertEquals(vehicleRecord2.getMake(), vehicle.getMake(), "The vehicle make matches");
                Assertions.assertEquals(vehicleRecord2.getImages(), vehicle.getImages(), "The vehicle images match");
            } else {
                Assertions.assertTrue(false, "Vehicle that was not in the records!");
            }
        }
    }

}
