package com.poseidoncapitalsolution.trading.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.poseidoncapitalsolution.trading.constant.SqlQuery;
import com.poseidoncapitalsolution.trading.model.CurvePoint;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {
    List<CurvePoint> findByCurveId(Integer curveId);
    
    @Modifying
	@Transactional
	@Query(value = SqlQuery.truncateCurvePointTestTable, nativeQuery = true)
    void resetCurvePointTestTable();
}
