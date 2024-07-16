package com.poseidoncapitalsolution.trading.service.contracts;

import com.poseidoncapitalsolution.trading.model.CurvePoint;
import java.util.List;

public interface ICurvePointService {
	List<CurvePoint> findAll();

	CurvePoint findById(Integer id);

	CurvePoint save(CurvePoint curvePoint);

	void update(Integer id, CurvePoint curvePoint);

	void deleteById(Integer id);

	List<CurvePoint> findByCurveId(Integer curveId);

	void resetCurvePointTestTable();
}
