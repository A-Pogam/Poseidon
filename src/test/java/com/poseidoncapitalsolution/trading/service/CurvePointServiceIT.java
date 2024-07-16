package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.CurvePoint;
import com.poseidoncapitalsolution.trading.service.contracts.ICurvePointService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "file:src/main/resources/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CurvePointServiceIT {

    @Autowired
    private ICurvePointService iCurvePointService;

    @BeforeEach
    public void setUp() {
        CurvePoint curvePoint1 = new CurvePoint();
        curvePoint1.setCurveId(1);
        curvePoint1.setTerm(10.0);
        curvePoint1.setValue(100.0);
        iCurvePointService.save(curvePoint1);

        CurvePoint curvePoint2 = new CurvePoint();
        curvePoint2.setCurveId(2);
        curvePoint2.setTerm(20.0);
        curvePoint2.setValue(200.0);
        iCurvePointService.save(curvePoint2);
    }

    @AfterEach
    public void tearDown() {
        iCurvePointService.findAll().forEach(cp -> iCurvePointService.deleteById(cp.getCurveId()));
    }

    @Test
    public void getCurvePointById_returnCurvePoint() {
        CurvePoint curvePoint = iCurvePointService.findById(1);

        assertThat(curvePoint).isNotNull();
        assertThat(curvePoint.getTerm()).isEqualTo(10.0);
        assertThat(curvePoint.getValue()).isEqualTo(100.0);
    }

    @Test
    public void getCurvePointById_returnNull() {
        CurvePoint curvePoint = iCurvePointService.findById(0);

        assertThat(curvePoint).isNull();
    }

    @Test
    public void addOrUpdateCurvePoint_returnCurvePoint() {
        CurvePoint newCurvePoint = new CurvePoint();
        newCurvePoint.setTerm(50.0);
        newCurvePoint.setValue(500.0);

        iCurvePointService.save(newCurvePoint);

        CurvePoint savedCurvePoint = iCurvePointService.findById(newCurvePoint.getCurveId());
        assertThat(savedCurvePoint).isNotNull();
        assertThat(savedCurvePoint.getTerm()).isEqualTo(50.0);
        assertThat(savedCurvePoint.getValue()).isEqualTo(500.0);
    }

    @Test
    public void updateCurvePoint_returnUpdatedCurvePoint() {
        Integer existingCurveId = 1;
        CurvePoint updatedCurvePoint = new CurvePoint();
        updatedCurvePoint.setCurveId(existingCurveId);
        updatedCurvePoint.setTerm(20.0);
        updatedCurvePoint.setValue(200.0);

        iCurvePointService.update(existingCurveId, updatedCurvePoint);

        CurvePoint curvePoint = iCurvePointService.findById(existingCurveId);
        assertThat(curvePoint).isNotNull();
        assertThat(curvePoint.getTerm()).isEqualTo(20.0);
        assertThat(curvePoint.getValue()).isEqualTo(200.0);
    }

    @Test
    public void deleteCurvePoint_deleteCurvePoint() {
        Integer curvePointIdToDelete = 2;
        CurvePoint curvePointToDelete = iCurvePointService.findById(curvePointIdToDelete);

        assertThat(curvePointToDelete).isNotNull();

        iCurvePointService.deleteById(curvePointIdToDelete);

        CurvePoint deletedCurvePoint = iCurvePointService.findById(curvePointIdToDelete);
        assertThat(deletedCurvePoint).isNull();
    }

    @Test
    public void findByCurveId_returnCurvePoints() {
        List<CurvePoint> curvePoints = iCurvePointService.findByCurveId(1);

        assertThat(curvePoints).isNotNull();
        assertThat(curvePoints.size()).isGreaterThanOrEqualTo(1);
        assertThat(curvePoints.get(0).getCurveId()).isEqualTo(1);
    }
}
