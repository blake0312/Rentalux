package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;

import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "VehicleRecord")
public class VehicleRecord {

    String id;
    String name;
    String description;
    Double retailPrice;
    Double mileage;
    VehicleType vehicleType;
    String make;
    List<String> images;

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
    @DynamoDBTypeConvertedEnum
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
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

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
