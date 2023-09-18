package com.kenzie.appserver.service.model;

import com.kenzie.capstone.service.model.ReservationData;

public class VehicleWithLambdaInfo {
    private final Vehicle vehicle;
    private final ReservationData data;

    public VehicleWithLambdaInfo(Vehicle vehicle, ReservationData data) {
        this.vehicle = vehicle;
        this.data = data;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ReservationData getData() {
        return data;
    }
}
