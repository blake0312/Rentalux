package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.RentalCreateRequest;
import com.kenzie.appserver.repositories.model.VehicleType;
import com.kenzie.appserver.service.RentalService;
import com.kenzie.appserver.service.model.Vehicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
class RentalControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    RentalService rentalService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getById_Exists() throws Exception {

        String name = mockNeat.strings().valStr();

        Vehicle persistedVehicle = rentalService.addNewExample(name);
        mvc.perform(get("/example/{id}", persistedVehicle.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .isString())
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void addNewRental_CreateSuccessful() throws Exception {
        String name = mockNeat.strings().valStr();
        String description = mockNeat.strings().valStr();
        Double retailPrice = 0.0;
        Double mileage = 0.0;
        VehicleType vehicleType = VehicleType.COUPE;
        String make = mockNeat.strings().valStr();
        List<String> images = Collections.emptyList();

        RentalCreateRequest rentalCreateRequest = new RentalCreateRequest();
        rentalCreateRequest.setName(name);
        rentalCreateRequest.setDescription(description);
        rentalCreateRequest.setRetailPrice(retailPrice);
        rentalCreateRequest.setMileage(mileage);
        rentalCreateRequest.setVehicleType(vehicleType);
        rentalCreateRequest.setMake(make);
        rentalCreateRequest.setImages(images);

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(post("/rental")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(rentalCreateRequest)))
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(jsonPath("description")
                        .value(is(description)))
                .andExpect(jsonPath("retailPrice")
                        .value(is(retailPrice)))
                .andExpect(jsonPath("mileage")
                        .value(is(mileage)))
                .andExpect(jsonPath("vehicleType")
                        .value(is(vehicleType)))
                .andExpect(jsonPath("make")
                        .value(is(make)))
                .andExpect(jsonPath("images")
                        .value(is(images)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getAllRental_Successful() throws Exception {
        String name = mockNeat.strings().valStr();
        String description = mockNeat.strings().valStr();
        Double retailPrice = 0.0;
        Double mileage = 0.0;
        VehicleType vehicleType = VehicleType.COUPE;
        String make = mockNeat.strings().valStr();
        List<String> images = Collections.emptyList();

        RentalCreateRequest rentalCreateRequest = new RentalCreateRequest();
        rentalCreateRequest.setName(name);
        rentalCreateRequest.setDescription(description);
        rentalCreateRequest.setRetailPrice(retailPrice);
        rentalCreateRequest.setMileage(mileage);
        rentalCreateRequest.setVehicleType(vehicleType);
        rentalCreateRequest.setMake(make);
        rentalCreateRequest.setImages(images);

        mapper.registerModule(new JavaTimeModule());

        rentalService.addNewVehicle(new Vehicle(UUID.randomUUID().toString(), name,description,retailPrice, mileage, vehicleType, make, images));

        mvc.perform(get("/rental/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().is2xxSuccessful());
    }
}