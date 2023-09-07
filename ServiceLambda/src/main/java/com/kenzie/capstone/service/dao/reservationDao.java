package com.kenzie.capstone.service.dao;

import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.model.ReservationRecord;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;

import java.util.List;

public class reservationDao {
    private DynamoDBMapper mapper;

    /**
     * Allows access to and manipulation of Match objects from the data store.
     * @param mapper Access to DynamoDB
     */
    public reservationDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public ExampleData storeExampleData(ExampleData exampleData) {
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

    public List<ReservationRecord> getExampleData(String id) {
        ReservationRecord reservationRecord = new ReservationRecord();
        reservationRecord.setId(id);

        DynamoDBQueryExpression<ReservationRecord> queryExpression = new DynamoDBQueryExpression<ReservationRecord>()
                .withHashKeyValues(reservationRecord)
                .withConsistentRead(false);

        return mapper.query(ReservationRecord.class, queryExpression);
    }

    public ReservationRecord setExampleData(String id, String data) {
        ReservationRecord reservationRecord = new ReservationRecord();
        reservationRecord.setId(id);
        reservationRecord.setData(data);

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
}
