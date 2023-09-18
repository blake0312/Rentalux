package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "Reservations")
public class ReservationRecord {

    private String id;
    private String customerId;
    private boolean payed;
    private String vehicleId;
    private String startData;
    private String endData;


    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "customerId")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @DynamoDBAttribute(attributeName = "payed")
    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    @DynamoDBRangeKey(attributeName = "vehicleId")
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    @DynamoDBAttribute(attributeName = "startDate")
    public String getStartData() {
        return startData;
    }

    public void setStartData(String startData) {
        this.startData = startData;
    }

    @DynamoDBAttribute(attributeName = "endDate")
    public String getEndData() {
        return endData;
    }

    public void setEndData(String endData) {
        this.endData = endData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationRecord that = (ReservationRecord) o;
        return id.equals(that.id) && customerId.equals(that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId);
    }
}