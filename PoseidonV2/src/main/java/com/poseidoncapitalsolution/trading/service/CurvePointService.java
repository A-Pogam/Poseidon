package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.CurvePoint;
import com.poseidoncapitalsolution.trading.repository.contracts.CurvePointRepository;
import com.poseidoncapitalsolution.trading.service.contracts.ICurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService implements ICurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Override
    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint findById(Integer id) {
        Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(id);
        return optionalCurvePoint.orElse(null);
    }

    @Override
    public CurvePoint save(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public void update(Integer id, CurvePoint curvePoint) {
        curvePoint.setId(id);
        curvePointRepository.save(curvePoint);
    }

    @Override
    public void deleteById(Integer id) {
        curvePointRepository.deleteById(id);
    }

    @Override
    public List<CurvePoint> findByCurveId(Integer curveId) {
        return curvePointRepository.findByCurveId(curveId);
    }
}
