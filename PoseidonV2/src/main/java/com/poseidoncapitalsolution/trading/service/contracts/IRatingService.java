package com.poseidoncapitalsolution.trading.service.contracts;

import com.poseidoncapitalsolution.trading.model.Rating;

import java.util.List;

public interface IRatingService {
    List<Rating> findAll();

    Rating findById(Integer id);

    Rating save(Rating rating);

    void update(Integer id, Rating rating);

    void deleteById(Integer id);
}
