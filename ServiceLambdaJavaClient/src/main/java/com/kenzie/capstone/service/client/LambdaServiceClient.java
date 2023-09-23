package com.kenzie.capstone.service.client;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.ReservationData;

import java.util.List;


public class LambdaServiceClient {

    private static final String GET_RESERVATION_ENDPOINT = "reservation/{id}";
    private static final String SET_RESERVATION_ENDPOINT = "reservation";
    private static final String UPDATE_RESERVATION_ENDPOINT = "reservation/update";
    private static final String DELETE_RESERVATION_ENDPOINT = "reservation/delete/{id}";
    private ObjectMapper mapper;

    public LambdaServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public List<ReservationData> getReservationData(String id) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_RESERVATION_ENDPOINT.replace("{id}", id));
        List<ReservationData> reservationData;
        try {
            reservationData = mapper.readValue(response, new TypeReference<List<ReservationData>>() {});
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return reservationData;
    }

    public ReservationData setReservationData(String data) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.postEndpoint(SET_RESERVATION_ENDPOINT, data);
        ReservationData reservationData;
        try {
            reservationData = mapper.readValue(response, ReservationData.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return reservationData;
    }
    public ReservationData updateReservationData(String data) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.postEndpoint(UPDATE_RESERVATION_ENDPOINT, data);
        ReservationData reservationData;
        try {
            reservationData = mapper.readValue(response, ReservationData.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return reservationData;
    }
    public void deleteReservationData(String id) {
        EndpointUtility endpointUtility = new EndpointUtility();
        endpointUtility.getEndpoint(DELETE_RESERVATION_ENDPOINT.replace("{id}", id));
    }
}
