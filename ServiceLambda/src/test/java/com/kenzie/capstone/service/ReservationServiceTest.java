package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.reservationDao;
import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.model.ReservationRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationServiceTest {

    /** ------------------------------------------------------------------------
     *  expenseService.getExpenseById
     *  ------------------------------------------------------------------------ **/

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
        ArgumentCaptor<String> dataCaptor = ArgumentCaptor.forClass(String.class);

        // GIVEN
        String data = "somedata";

        // WHEN
        ExampleData response = this.reservationService.setExampleData(data);

        // THEN
        verify(reservationDao, times(1)).setExampleData(idCaptor.capture(), dataCaptor.capture());

        assertNotNull(idCaptor.getValue(), "An ID is generated");
        assertEquals(data, dataCaptor.getValue(), "The data is saved");

        assertNotNull(response, "A response is returned");
        assertEquals(idCaptor.getValue(), response.getId(), "The response id should match");
        assertEquals(data, response.getData(), "The response data should match");
    }

    @Test
    void getDataTest() {
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);

        // GIVEN
        String id = "fakeid";
        String data = "somedata";
        ReservationRecord record = new ReservationRecord();
        record.setId(id);
        record.setData(data);


        when(reservationDao.getExampleData(id)).thenReturn(Arrays.asList(record));

        // WHEN
        ExampleData response = this.reservationService.getExampleData(id);

        // THEN
        verify(reservationDao, times(1)).getExampleData(idCaptor.capture());

        assertEquals(id, idCaptor.getValue(), "The correct id is used");

        assertNotNull(response, "A response is returned");
        assertEquals(id, response.getId(), "The response id should match");
        assertEquals(data, response.getData(), "The response data should match");
    }

    // Write additional tests here

}