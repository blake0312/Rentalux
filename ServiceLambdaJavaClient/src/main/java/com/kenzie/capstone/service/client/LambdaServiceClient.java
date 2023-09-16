package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.ReservationData;


public class LambdaServiceClient {

    private static final String GET_RESERVATION_ENDPOINT = "reservation/{id}";
    private static final String SET_RESERVATION_ENDPOINT = "reservation";

    private ObjectMapper mapper;

    public LambdaServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public ReservationData getReservationData(String id) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_RESERVATION_ENDPOINT.replace("{id}", id));
        ReservationData exampleData;
        try {
            exampleData = mapper.readValue(response, ReservationData.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return exampleData;
    }

    public ReservationData setReservationData(String data) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.postEndpoint(SET_RESERVATION_ENDPOINT, data);
        ReservationData exampleData;
        try {
            exampleData = mapper.readValue(response, ReservationData.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return exampleData;
    }
}
