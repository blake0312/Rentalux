package com.kenzie.capstone.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.xspec.B;
import com.kenzie.capstone.service.model.ReservationData;
import com.kenzie.capstone.service.dao.reservationDao;
import com.kenzie.capstone.service.model.ReservationRecord;

import javax.inject.Inject;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReservationService {

    private reservationDao reservationDao;
    private DynamoDBMapper mapper;

    @Inject
    public ReservationService(reservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationData> getReservationData(String id) {
        List<ReservationRecord> records = reservationDao.getReservationData(id);
        if (records.size() > 0) {
            return records.stream()
                    .map(s -> new ReservationData(s.getId(), s.getCustomerId(), s.isPayed(),
                            s.getVehicleId(), s.getStartData(), s.getEndData()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public ReservationData setReservationData(String customerID, boolean payed, String vehicleId, String startDate, String endDate) {
        String id = UUID.randomUUID().toString();
        ReservationRecord record = reservationDao.setReservationData(id, customerID, payed, vehicleId, startDate, endDate);
        return new ReservationData(id, customerID, payed, vehicleId, startDate, endDate);
    }

    public ReservationData updateReservationData(String reservationId, String customerId, boolean payed, String vehicleId, String startDate, String endDate){
        ReservationRecord record = reservationDao.setReservationData(reservationId,customerId, payed, vehicleId, startDate, endDate);
        return new ReservationData(record.getId(), record.getCustomerId(), record.isPayed(),
                record.getVehicleId(), record.getStartData(), record.getEndData());
    }

    public void deleteReservation(String id){
        reservationDao.deleteReservation(id);
    }
}
