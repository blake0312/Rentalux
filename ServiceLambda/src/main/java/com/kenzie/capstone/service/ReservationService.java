package com.kenzie.capstone.service;

import com.amazonaws.services.dynamodbv2.xspec.B;
import com.kenzie.capstone.service.model.ReservationData;
import com.kenzie.capstone.service.dao.reservationDao;
import com.kenzie.capstone.service.model.ReservationRecord;

import javax.inject.Inject;

import java.util.List;
import java.util.UUID;

public class ReservationService {

    private reservationDao reservationDao;

    @Inject
    public ReservationService(reservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ReservationData getReservationData(String id) {
        List<ReservationRecord> records = reservationDao.getReservationData(id);
        if (records.size() > 0) {
            return new ReservationData(records.get(0).getId(), records.get(0).getCustomerId(), records.get(0).isPayed(),
                    records.get(0).getVehicleId(), records.get(0).getStartData(), records.get(0).getEndData());
        }
        return null;
    }

    public ReservationData setReservationData(String customerID, boolean payed, String vehicleId, String startDate, String endDate) {
        String id = UUID.randomUUID().toString();
        ReservationRecord record = reservationDao.setReservationData(id, customerID, payed, vehicleId, startDate, endDate);
        return new ReservationData(id, customerID, payed, vehicleId, startDate, endDate);
    }
}
