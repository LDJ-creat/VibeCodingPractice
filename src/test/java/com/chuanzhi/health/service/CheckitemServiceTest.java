package com.chuanzhi.health.service;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chuanzhi.health.dao.CheckitemDao;
import com.chuanzhi.health.model.Checkitem;

@ExtendWith(MockitoExtension.class)
class CheckitemServiceTest {

    @Mock
    private CheckitemDao checkitemDao;

    @InjectMocks
    private CheckitemService checkitemService;

    private Checkitem checkitem;

    @BeforeEach
    void setUp() {
        checkitem = new Checkitem();
        checkitem.setCid(1);
        checkitem.setCcode("C001");
        checkitem.setCname("Test Item");
    }

    @Test
    void add() {
        ArgumentCaptor<Checkitem> checkitemCaptor = ArgumentCaptor.forClass(Checkitem.class);
        checkitemService.add(checkitem);
        
        verify(checkitemDao).add(checkitemCaptor.capture());
        Checkitem captured = checkitemCaptor.getValue();

        assertNotNull(captured.getCreateDate());
        assertEquals("1", captured.getStatus());
        assertEquals("admin", captured.getOptionUser());
    }

    @Test
    void update() {
        ArgumentCaptor<Checkitem> checkitemCaptor = ArgumentCaptor.forClass(Checkitem.class);
        checkitemService.update(checkitem);

        verify(checkitemDao).update(checkitemCaptor.capture());
        Checkitem captured = checkitemCaptor.getValue();

        assertNotNull(captured.getUpdDate());
        assertEquals("admin", captured.getOptionUser());
    }

    @Test
    void deleteById() {
        int id = 1;
        checkitemService.deleteById(id);
        verify(checkitemDao, times(1)).deleteById(id);
    }

    @Test
    void findById() {
        when(checkitemDao.findById(1)).thenReturn(checkitem);
        Checkitem found = checkitemService.findById(1);
        assertEquals("Test Item", found.getCname());
    }

    @Test
    void findAll() {
        when(checkitemDao.findAll()).thenReturn(Collections.singletonList(checkitem));
        List<Checkitem> all = checkitemService.findAll();
        assertEquals(1, all.size());
    }

    @Test
    void findByCondition() {
        String query = "Test";
        when(checkitemDao.findByCondition(query)).thenReturn(Collections.singletonList(checkitem));
        List<Checkitem> found = checkitemService.findByCondition(query);
        assertEquals(1, found.size());
    }
}