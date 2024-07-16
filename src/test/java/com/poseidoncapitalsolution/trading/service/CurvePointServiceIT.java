package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.CurvePoint;
import com.poseidoncapitalsolution.trading.repository.contracts.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceIT {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    private CurvePoint curvePoint;

    @BeforeEach
    public void setUp() {
        curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setTerm(10.0);
        curvePoint.setValue(100.0);
    }

    @Test
    public void testFindAll() {
        List<CurvePoint> curvePoints = Arrays.asList(curvePoint, new CurvePoint());
        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        List<CurvePoint> result = curvePointService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(curvePoint));

        CurvePoint result = curvePointService.findById(1);

        assertNotNull(result);
        assertEquals(curvePoint.getCurveId(), result.getCurveId());
        verify(curvePointRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testFindById_NotFound() {
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());

        CurvePoint result = curvePointService.findById(1);

        assertNull(result);
        verify(curvePointRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testSave() {
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint result = curvePointService.save(curvePoint);

        assertNotNull(result);
        assertEquals(curvePoint.getCurveId(), result.getCurveId());
        verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
    }

    @Test
    public void testUpdate() {
        curvePointService.update(1, curvePoint);

        verify(curvePointRepository, times(1)).save(curvePoint);
        assertEquals(1, curvePoint.getCurveId());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(curvePointRepository).deleteById(anyInt());

        curvePointService.deleteById(1);

        verify(curvePointRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void testFindByCurveId() {
        List<CurvePoint> curvePoints = Arrays.asList(curvePoint, new CurvePoint());
        when(curvePointRepository.findByCurveId(anyInt())).thenReturn(curvePoints);

        List<CurvePoint> result = curvePointService.findByCurveId(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(curvePointRepository, times(1)).findByCurveId(anyInt());
    }
}
