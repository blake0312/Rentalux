package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "VehicleRecord")
public class VehicleRecord {

    String id;
    String name;
    String description;
    Double retailPrice;
    Double mileage;
    VehicleType vehicleType;
    String make;
    String images;

    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }


     public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute(attributeName = "retailPrice")
    public Double getRetalPrice() {
        return retailPrice;
    }


    public void setRetalPrice(Double retalPrice) {
        this.retailPrice = retalPrice;
    }

    @DynamoDBAttribute(attributeName = "mileage")
    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }
    @DynamoDBAttribute(attributeName = "vehicleType")
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @DynamoDBAttribute(attributeName = "make")
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @DynamoDBAttribute(attributeName = "images")
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VehicleRecord vehicleRecord = (VehicleRecord) o;
        return Objects.equals(id, vehicleRecord.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);

    }
}
