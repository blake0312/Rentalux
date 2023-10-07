package com.kenzie.appserver.service.model;

public class Reservation {
    private String id;
    private String customerId;
    private boolean payed;
    private String vehicleId;
    private String startData;
    private String endData;

    public Reservation(String id, String customerId, boolean payed, String vehicleId, String startData, String endData) {
        this.id = id;
        this.customerId = customerId;
        this.payed = payed;
        this.vehicleId = vehicleId;
        this.startData = startData;
        this.endData = endData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getStartData() {
        return startData;
    }

    public void setStartData(String startData) {
        this.startData = startData;
    }

    public String getEndData() {
        return endData;
    }

    public void setEndData(String endData) {
        this.endData = endData;
    }
}
