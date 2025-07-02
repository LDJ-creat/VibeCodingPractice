package com.chuanzhi.health.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.chuanzhi.health.dao.AppointmentDao;
import com.chuanzhi.health.model.Appointment;

class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentDao appointmentDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAppointment_Success() {
        when(appointmentDao.add(any(Appointment.class))).thenReturn(1);
        Date futureDate = new Date(System.currentTimeMillis() + 86400000); // Tomorrow
        Appointment result = appointmentService.createAppointment(1, 1, futureDate);
        assertNotNull(result);
        assertEquals("pending", result.getStatus());
        verify(appointmentDao, times(1)).add(any(Appointment.class));
    }

    @Test
    void createAppointment_Fail_PastDate() {
        Date pastDate = new Date(System.currentTimeMillis() - 86400000); // Yesterday
        Appointment result = appointmentService.createAppointment(1, 1, pastDate);
        assertNull(result);
        verify(appointmentDao, never()).add(any(Appointment.class));
    }

    @Test
    void cancelAppointment_Success() {
        when(appointmentDao.updateStatus(1, "cancelled")).thenReturn(1);
        boolean result = appointmentService.cancelAppointment(1);
        assertTrue(result);
        verify(appointmentDao, times(1)).updateStatus(1, "cancelled");
    }

    @Test
    void findByUserId_Success() {
        Appointment appointment = new Appointment();
        appointment.setId(1);
        appointment.setUserId(1);
        when(appointmentDao.findByUserIdWithCheckgroupName(1)).thenReturn(Collections.singletonList(appointment));
        List<Appointment> result = appointmentService.findByUserId(1);
        assertFalse(result.isEmpty());
        assertEquals(1, result.get(0).getId());
        verify(appointmentDao, times(1)).findByUserIdWithCheckgroupName(1);
    }

    @Test
    void completeAppointment_Success() {
        when(appointmentDao.updateStatus(1, "completed")).thenReturn(1);
        boolean result = appointmentService.completeAppointment(1);
        assertTrue(result);
        verify(appointmentDao, times(1)).updateStatus(1, "completed");
    }
}