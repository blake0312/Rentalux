package com.kenzie.capstone.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ReservationData {

    private String id;
    private String customerId;
    private boolean payed;
    private String vehicleId;
    private String startData;
    private String endData;


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ReservationData(@JsonProperty("id") String id, @JsonProperty("customerId") String customerId,
                           @JsonProperty("payed")boolean payed, @JsonProperty("vehicleId") String vehicleId,
                           @JsonProperty("startData") String startData, @JsonProperty("endData") String endData) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationData that = (ReservationData) o;
        return id.equals(that.id) && customerId.equals(that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId);
    }
}
