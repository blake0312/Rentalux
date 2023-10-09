package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.reservationDao;
import com.kenzie.capstone.service.model.ReservationData;
import com.kenzie.capstone.service.model.ReservationRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationServiceTest {

    /**
     * ------------------------------------------------------------------------
     * expenseService.getExpenseById
     * ------------------------------------------------------------------------
     **/

    private reservationDao reservationDao;
    private ReservationService reservationService;

    @BeforeAll
    void setup() {
        this.reservationDao = mock(reservationDao.class);
        this.reservationService = new ReservationService(reservationDao);
    }

    @Test
    void setDataTest() {
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> customerIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> isPayedCaptor = ArgumentCaptor.forClass(Boolean.class);
        ArgumentCaptor<String> vehicleIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> startDateCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> endDateCaptor = ArgumentCaptor.forClass(String.class);

        // WHEN
        ReservationData response = this.reservationService.setReservationData("customerId",
                true, "vehicleId", "startDate", "endDate");

        // THEN
        verify(reservationDao, times(1)).setReservationData(idCaptor.capture(), customerIdCaptor.capture(), isPayedCaptor.capture(),
                vehicleIdCaptor.capture(), startDateCaptor.capture(), endDateCaptor.capture());

        assertNotNull(response, "A response is returned");
        assertNotNull(idCaptor.getValue(), "An ID is generated");
        assertEquals(response.getCustomerId(), customerIdCaptor.getValue(), "Customer id matches");
        assertEquals(response.getId(), idCaptor.getValue(), "Reservation id matches");
        assertEquals(response.isPayed(), isPayedCaptor.getValue(), "isPayed matches");
        assertEquals(response.getStartData(), startDateCaptor.getValue(), "start date matches");
        assertEquals(response.getEndData(), endDateCaptor.getValue(), "end date matches");

    }

    @Test
    void getDataTest() {
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);

        // GIVEN
        String id = "fakeid";
        String data = "somedata";

        ReservationRecord record = new ReservationRecord();
        record.setId(id);
        record.setCustomerId(data);
        record.setPayed(true);
        record.setVehicleId("vehicleId");
        record.setStartData("start");
        record.setEndData("end");


        when(reservationDao.getReservationData(id)).thenReturn(Arrays.asList(record));

        // WHEN
        List<ReservationData> response = this.reservationService.getReservationData(id);

        // THEN
        verify(reservationDao, times(1)).getReservationData(idCaptor.capture());

        assertEquals(id, idCaptor.getValue(), "The correct id is used");
        assertNotNull(response, "A response is returned");
        assertEquals(1, response.size());
        assertEquals(id, response.get(0).getId());
        assertEquals(data, response.get(0).getCustomerId());
    }

    // Write additional tests here
    @Test
    void deleteReservationDataWithReservationRecordTest() {
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        // GIVEN
        String id = "fakeid";
        String data = "somedata";

        ReservationRecord record = new ReservationRecord();
        record.setId(id);
        record.setCustomerId(data);
        record.setPayed(true);
        record.setVehicleId("vehicleId");
        record.setStartData("start");
        record.setEndData("end");

        // WHEN
        this.reservationService.deleteReservation(record.getId());

        // THEN
        verify(reservationDao, times(1)).deleteReservation(idCaptor.capture());
    }
    @Test
    void reservationsForCustomerIdTest(){
        String id = "fakeid";
        String data = "somedata";

        ReservationRecord record = new ReservationRecord();
        record.setId(id);
        record.setCustomerId(data);
        record.setPayed(true);
        record.setVehicleId("vehicleId");
        record.setStartData("start");
        record.setEndData("end");

        ReservationRecord record2 = new ReservationRecord();
        record2.setId(id);
        record2.setCustomerId(data);
        record2.setPayed(true);
        record2.setVehicleId("vehicleId");
        record2.setStartData("start");
        record2.setEndData("end");

        List<ReservationRecord> records = new ArrayList<>();
        records.add(record);
        records.add(record2);

        when(reservationDao.getReservationCustomerId(any())).thenReturn(records);

        List<ReservationData> dataList = reservationService.getReservationData("customerId," + "life");

        Assertions.assertEquals(2,dataList.size());

    }
    @Test
    void getReservationForCustomerIdTestInvalid() {

        List<ReservationData> dataList = reservationService.getReservationData("GhostFace," + "life");

        Assertions.assertEquals(0, dataList.size());

    }
}