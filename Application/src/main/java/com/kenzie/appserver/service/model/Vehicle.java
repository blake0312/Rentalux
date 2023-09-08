package com.kenzie.appserver.service.model;

import com.kenzie.appserver.repositories.model.VehicleType;

import java.util.List;
import java.util.UUID;

public class Vehicle {
    String id;
    String name;
    String description;
    Double retailPrice;
    Double mileage;
    VehicleType vehicleType;
    String make;
    List<String> images;

    public Vehicle(String id, String name, String description, Double retailPrice, Double mileage,
                   VehicleType vehicleType, String make, List<String> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.retailPrice = retailPrice;
        this.mileage = mileage;
        this.vehicleType = vehicleType;
        this.make = make;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public Double getMileage() {
        return mileage;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getMake() {
        return make;
    }

    public List<String> getImages() {
        return images;
    }
}
