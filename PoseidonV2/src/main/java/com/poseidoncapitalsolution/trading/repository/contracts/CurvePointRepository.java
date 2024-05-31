package com.poseidoncapitalsolution.trading.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poseidoncapitalsolution.trading.model.CurvePoint;

public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}