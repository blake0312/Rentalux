package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RentalRepository;
import com.kenzie.appserver.repositories.model.RentalRecord;
import com.kenzie.appserver.service.model.Vehicle;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RentalServiceTest {
    private RentalRepository rentalRepository;
    private RentalService rentalService;
    private LambdaServiceClient lambdaServiceClient;

    @BeforeEach
    void setup() {
        rentalRepository = mock(RentalRepository.class);
        lambdaServiceClient = mock(LambdaServiceClient.class);
        rentalService = new RentalService(rentalRepository, lambdaServiceClient);
    }
    /** ------------------------------------------------------------------------
     *  exampleService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findById() {
        // GIVEN
        String id = randomUUID().toString();

        RentalRecord record = new RentalRecord();
        record.setId(id);
        record.setName("concertname");

        // WHEN
        when(rentalRepository.findById(id)).thenReturn(Optional.of(record));
        Vehicle vehicle = rentalService.findById(id);

        // THEN
        Assertions.assertNotNull(vehicle, "The object is returned");
        Assertions.assertEquals(record.getId(), vehicle.getId(), "The id matches");
        Assertions.assertEquals(record.getName(), vehicle.getName(), "The name matches");
    }

    @Test
    void findByConcertId_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(rentalRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        Vehicle vehicle = rentalService.findById(id);

        // THEN
        Assertions.assertNull(vehicle, "The example is null when not found");
    }

}
