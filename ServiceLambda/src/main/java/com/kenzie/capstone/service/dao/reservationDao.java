package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.kenzie.capstone.service.model.ReservationData;
import com.kenzie.capstone.service.model.ReservationRecord;

import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public class reservationDao {
    private DynamoDBMapper mapper;

    /**
     * Allows access to and manipulation of Match objects from the data store.
     *
     * @param mapper Access to DynamoDB
     */
    public reservationDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public ReservationData storeExampleData(ReservationData exampleData) {
        try {
            mapper.save(exampleData, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "id",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id has already been used");
        }

        return exampleData;
    }

    public List<ReservationRecord> getReservationData(String id) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        if(!id.equals("all")){
            scanExpression.withFilterExpression("vehicleId = :value");
            scanExpression.withExpressionAttributeValues(Map.of(":value", new AttributeValue().withS(id)));
            scanExpression.withConsistentRead(false);
        }

        return mapper.scan(ReservationRecord.class, scanExpression);
    }
    public ReservationRecord getReservationHashKey(String id){
        ReservationRecord record = new ReservationRecord();
        record.setId(id);

        DynamoDBQueryExpression<ReservationRecord> queryExpression = new DynamoDBQueryExpression<ReservationRecord>()
                .withHashKeyValues(record);

        return mapper.query(ReservationRecord.class, queryExpression).get(0);
    }
    public ReservationRecord setReservationData(String id, String customerId, boolean payed, String vehicleId,
                                                String startData, String endData) {
        ReservationRecord reservationRecord = new ReservationRecord();
        reservationRecord.setId(id);
        reservationRecord.setCustomerId(customerId);
        reservationRecord.setPayed(payed);
        reservationRecord.setVehicleId(vehicleId);
        reservationRecord.setStartData(startData);
        reservationRecord.setEndData(endData);


        try {
            mapper.save(reservationRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "id",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id already exists");
        }

        return reservationRecord;
    }

    public void deleteReservation(String id){
        ReservationRecord record = getReservationHashKey(id);
        mapper.delete(record);
    }

    public void deleteReservation(ReservationRecord record){
        mapper.delete(record);
    }

    public ReservationRecord updateReservationData(String id, String customerId, boolean payed, String vehicleId,
                                                String startData, String endData) {
        ReservationRecord reservationRecord = getReservationHashKey(id);

        if(!vehicleId.equals(reservationRecord.getVehicleId())){
            deleteReservation(reservationRecord);
            reservationRecord = setReservationData(id, customerId, payed, vehicleId, startData, endData);
        } else {
            reservationRecord.setCustomerId(customerId);
            reservationRecord.setPayed(payed);
            reservationRecord.setStartData(startData);
            reservationRecord.setEndData(endData);

            updateReservationData(reservationRecord);
        }

        return reservationRecord;
    }

    public void updateReservationData(ReservationRecord record){

        try {
            mapper.save(record);
        } catch (DynamoDBMappingException e) {
           throw new RuntimeException("Error updating", e.getCause());
        }
    }
}
