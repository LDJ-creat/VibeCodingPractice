package com.chuanzhi.health.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.chuanzhi.health.dao.ExamResultDao;
import com.chuanzhi.health.model.ExamResult;

class ExamResultServiceTest {

    @InjectMocks
    private ExamResultService examResultService;

    @Mock
    private ExamResultDao examResultDao;

    @Mock
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void recordExamResults_Success() {
        ExamResult result = new ExamResult();
        result.setAppointmentId(1);
        result.setCheckitemId(1);
        result.setResultValue("100");
        result.setExamDate(new Date());
        List<ExamResult> results = Collections.singletonList(result);

        doNothing().when(examResultDao).addBatch(results);
        when(appointmentService.completeAppointment(1)).thenReturn(true);

        boolean success = examResultService.recordExamResults(results);

        assertTrue(success);
        verify(examResultDao, times(1)).addBatch(results);
        verify(appointmentService, times(1)).completeAppointment(1);
    }

    @Test
    void recordExamResults_Fail_EmptyList() {
        boolean success = examResultService.recordExamResults(Collections.emptyList());
        assertFalse(success);
        verify(examResultDao, never()).addBatch(any());
        verify(appointmentService, never()).completeAppointment(anyInt());
    }

    @Test
    void findByAppointmentId_Success() {
        ExamResult result = new ExamResult();
        result.setAppointmentId(1);
        when(examResultDao.findByAppointmentId(1)).thenReturn(Collections.singletonList(result));

        List<ExamResult> report = examResultService.findByAppointmentId(1);

        assertFalse(report.isEmpty());
        assertEquals(1, report.get(0).getAppointmentId());
        verify(examResultDao, times(1)).findByAppointmentId(1);
    }
}