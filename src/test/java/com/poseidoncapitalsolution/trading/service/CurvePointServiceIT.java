package com.poseidoncapitalsolution.trading.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.poseidoncapitalsolution.trading.model.CurvePoint;
import com.poseidoncapitalsolution.trading.service.contracts.ICurvePointService;

@SpringBootTest
@TestPropertySource(locations = "file:src/main/resources/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CurvePointServiceIT {

	@Autowired
	private ICurvePointService iCurvePointService;
	
	@BeforeAll
	public void fillCurvePointTable() {
		for (int i = 1; i <= 3; i++) {
			iCurvePointService.save(new CurvePoint(null, Double.valueOf(i), Double.valueOf(i+1)));
		}
	}
	
	@AfterAll
	public void resetCurvePointable() {
		iCurvePointService.resetCurvePointTestTable();
	}
	
	@Test
	public void getCurvePoints_returnCurvePoints() {
		List<CurvePoint> curvePoints = iCurvePointService.findAll();
		
		assertThat(curvePoints).isNotEmpty();
		assertThat(curvePoints.getFirst().getCurveId()).isEqualTo(1);
		assertThat(curvePoints.getFirst().getTerm()).isEqualTo(1d);
		assertThat(curvePoints.getFirst().getValue()).isEqualTo(2d);
	}

	@Test
	public void getCurvePointById_returnCurvePoint() {
		CurvePoint curvePoint = iCurvePointService.findById(1);
		
		assertThat(curvePoint.getCurveId()).isEqualTo(1);
		assertThat(curvePoint.getTerm()).isEqualTo(1d);
		assertThat(curvePoint.getValue()).isEqualTo(2d);
	}
	
	@Test
	public void getCurvePointById_returnNull() {
		CurvePoint curvePoint = iCurvePointService.findById(0);
		
		assertThat(curvePoint).isEqualTo(null);
	}
	
	@Test
	public void getCurvePointByCurvePointId_returnCurvePoint() {
		List<CurvePoint> curvePoints = iCurvePointService.findByCurveId(1);
		
		assertThat(curvePoints).isNotEmpty();
		assertThat(curvePoints.getFirst().getCurveId()).isEqualTo(1);
	}

	@Test
	public void saveCurvePoint_returnCurvePoint() {
		CurvePoint newCurvePoint = new CurvePoint(null, 33d, 34d);
		
		CurvePoint curvePointAdded = iCurvePointService.save(newCurvePoint);
		
		assertThat(curvePointAdded.getTerm()).isEqualTo(newCurvePoint.getTerm());
		assertThat(curvePointAdded.getValue()).isEqualTo(newCurvePoint.getValue());
	}
	
	@Test
	public void updateCurvePoint_returnUpdatedBid() {
	    Integer existingCurvePointId = 3;
	    CurvePoint updatedCurvePoint = new CurvePoint(null, 43d, 44d);
	
	    iCurvePointService.update(existingCurvePointId, updatedCurvePoint);
	
	    CurvePoint curvePoint = iCurvePointService.findById(existingCurvePointId);
	    assertThat(curvePoint).isNotNull();
	    assertThat(curvePoint.getTerm()).isEqualTo(updatedCurvePoint.getTerm());
	    assertThat(curvePoint.getValue()).isEqualTo(updatedCurvePoint.getValue());
	}

	@Test
	public void deleteCurvePoint_deleteCurvePoint() {
		CurvePoint curvePointToDelete = iCurvePointService.findById(2);
		iCurvePointService.deleteById(2);
		
		assertThat(!iCurvePointService.findAll().contains(curvePointToDelete));
	}
}
