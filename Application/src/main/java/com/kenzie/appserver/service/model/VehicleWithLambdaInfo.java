package com.kenzie.appserver.service.model;

import com.kenzie.capstone.service.model.ReservationData;

import java.util.List;

public class VehicleWithLambdaInfo {
    private final Vehicle vehicle;
    private final List<Reservation> data;

    public VehicleWithLambdaInfo(Vehicle vehicle, List<Reservation> data) {
        this.vehicle = vehicle;
        this.data = data;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public List<Reservation> getData() {
        return data;
    }
}
