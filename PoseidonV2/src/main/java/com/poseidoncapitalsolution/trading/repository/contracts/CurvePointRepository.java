package com.poseidoncapitalsolution.trading.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import com.poseidoncapitalsolution.trading.model.CurvePoint;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {
    List<CurvePoint> findByCurveId(Integer curveId);
}
