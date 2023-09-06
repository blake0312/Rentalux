package com.kenzie.capstone.service;

import com.kenzie.capstone.service.model.ExampleData;
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

    public ExampleData getExampleData(String id) {
        List<ReservationRecord> records = reservationDao.getExampleData(id);
        if (records.size() > 0) {
            return new ExampleData(records.get(0).getId(), records.get(0).getData());
        }
        return null;
    }

    public ExampleData setExampleData(String data) {
        String id = UUID.randomUUID().toString();
        ReservationRecord record = reservationDao.setExampleData(id, data);
        return new ExampleData(id, data);
    }
}
