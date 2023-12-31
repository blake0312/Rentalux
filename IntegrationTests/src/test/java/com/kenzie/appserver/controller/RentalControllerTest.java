package com.kenzie.appserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.LambdaReservationCreateRequest;
import com.kenzie.appserver.controller.model.LambdaReservationResponse;
import com.kenzie.appserver.controller.model.RentalCreateRequest;
import com.kenzie.appserver.controller.model.RentalResponse;
import com.kenzie.appserver.repositories.model.VehicleType;
import com.kenzie.appserver.service.RentalService;
import com.kenzie.appserver.service.model.Reservation;
import com.kenzie.appserver.service.model.Vehicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        List<String> images = new ArrayList<>();
        images.add("image1");
        images.add("image2");
        String name = mockNeat.strings().valStr();
        Vehicle vehicle = new Vehicle(UUID.randomUUID().toString(), name, "v-8 twin turbo", 190000.00,
                0.0, VehicleType.SEDAN, "Porsche", images);

        Vehicle persistedVehicle = rentalService.addNewVehicle(vehicle);
        mvc.perform(get("/rental/{id}", persistedVehicle.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .isString())
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(status().is2xxSuccessful());

        rentalService.deleteVehicle(persistedVehicle.getId());
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

        MvcResult mvcResult = mvc.perform(post("/rental")
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
                        .value(is(vehicleType.name())))
                .andExpect(jsonPath("make")
                        .value(is(make)))
                .andExpect(jsonPath("images")
                        .value(is(images)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Gson gson = new Gson();
        RentalResponse response = gson.fromJson(mvcResult.getResponse().getContentAsString(), RentalResponse.class);

        rentalService.deleteVehicle(response.getId());
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

        mapper.registerModule(new JavaTimeModule());

        Vehicle vehicle1 = rentalService.addNewVehicle(new Vehicle(UUID.randomUUID().toString(), name, description, retailPrice, mileage, vehicleType, make, images));
        Vehicle vehicle2 = rentalService.addNewVehicle(new Vehicle(UUID.randomUUID().toString(), name, description, retailPrice, mileage, vehicleType, make, images));

        mvc.perform(get("/rental/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().is2xxSuccessful());

        rentalService.deleteVehicle(vehicle1.getId());
        rentalService.deleteVehicle(vehicle2.getId());
    }

    @Test
    void addNewReservations() throws Exception {
        // GIVEN
        LambdaReservationCreateRequest lambdaReservationCreateRequest = new LambdaReservationCreateRequest();

        String id = "id";

        lambdaReservationCreateRequest.setId(id);
        lambdaReservationCreateRequest.setCustomerId("customerId");
        lambdaReservationCreateRequest.setPayed(true);
        lambdaReservationCreateRequest.setVehicleId("vehicleId");
        lambdaReservationCreateRequest.setStartData("startDate");
        lambdaReservationCreateRequest.setEndData("endDate");

        mapper.registerModule(new JavaTimeModule());

        MvcResult mvcResult = mvc.perform(post("/rental/reservation")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(lambdaReservationCreateRequest)))
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("customerId")
                        .value(is("customerId")))
                .andExpect(jsonPath("payed")
                        .value(is(true)))
                .andExpect(jsonPath("vehicleId")
                        .value(is("vehicleId")))
                .andExpect(jsonPath("startData")
                        .value(is("startDate")))
                .andExpect(jsonPath("endData")
                        .value(is("endDate")))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Gson gson = new Gson();

        LambdaReservationResponse lambdaReservationResponse = gson.fromJson(mvcResult.getResponse().getContentAsString(),
                LambdaReservationResponse.class);

        rentalService.deleteReservation(lambdaReservationResponse.getId());

    }

    @Test
    void updateReservations() throws Exception {
        Reservation reservation = new Reservation("id", "customerId", false, "vehicleId", "startDate", "endDate");
        Reservation reservationResponse = rentalService.addNewReservation(reservation);
        // GIVEN
        LambdaReservationCreateRequest lambdaReservationCreateRequest = new LambdaReservationCreateRequest();
        ;

        lambdaReservationCreateRequest.setId(reservationResponse.getId());
        lambdaReservationCreateRequest.setCustomerId("customer");
        lambdaReservationCreateRequest.setPayed(true);
        lambdaReservationCreateRequest.setVehicleId("vehicle");
        lambdaReservationCreateRequest.setStartData("start");
        lambdaReservationCreateRequest.setEndData("end");

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(put("/rental/reservation/{id}", reservationResponse.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(lambdaReservationCreateRequest)))
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("customerId")
                        .value(is("customer")))
                .andExpect(jsonPath("payed")
                        .value(is(true)))
                .andExpect(jsonPath("vehicleId")
                        .value(is("vehicle")))
                .andExpect(jsonPath("startData")
                        .value(is("start")))
                .andExpect(jsonPath("endData")
                        .value(is("end")))
                .andExpect(status().is2xxSuccessful());

        rentalService.deleteReservation(reservationResponse.getId());

    }

    @Test
    void getAllReservation() throws Exception {

        Reservation reservation1 = new Reservation("id", "customerId", false, "vehicleId",
                "startDate", "endDate");
        Reservation reservationResponse1 = rentalService.addNewReservation(reservation1);
        Reservation reservationResponse2 = rentalService.addNewReservation(reservation1);

        // GIVEN

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(get("/rental/reservation/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().is2xxSuccessful());
        rentalService.deleteReservation(reservationResponse1.getId());
        rentalService.deleteReservation(reservationResponse2.getId());
    }

    @Test
    void deleteReservation() throws Exception {
        // GIVEN
        Reservation reservation1 = new Reservation("id", "customerId", false, "vehicleId",
                "startDate", "endDate");
        Reservation reservationResponse1 = rentalService.addNewReservation(reservation1);

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(delete("/rental/reservation/{id}", reservationResponse1.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllCustomerReservations() throws Exception {
        Reservation reservation1 = new Reservation("id", "customerId", false, "vehicleId",
                "startDate", "endDate");
        Reservation reservation2 = new Reservation("id", "snorlax", false, "vehicleId",
                "startDate", "endDate");
        Reservation reservationResponse1 = rentalService.addNewReservation(reservation1);
        Reservation reservationResponse2 = rentalService.addNewReservation(reservation2);

        // GIVEN

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(get("/rental/reservation/customer/{id}", "snorlax")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().is2xxSuccessful());
        rentalService.deleteReservation(reservationResponse1.getId());
        rentalService.deleteReservation(reservationResponse2.getId());

    }
}