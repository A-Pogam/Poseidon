package com.poseidoncapitalsolution.trading.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import com.poseidoncapitalsolution.trading.model.CurvePoint;

import java.util.List;

public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {
    List<CurvePoint> findByCurveId(Integer curveId);
}
