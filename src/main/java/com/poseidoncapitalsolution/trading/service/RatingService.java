package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.Rating;
import com.poseidoncapitalsolution.trading.repository.contracts.RatingRepository;
import com.poseidoncapitalsolution.trading.service.contracts.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService implements IRatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating findById(Integer id) {
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        return optionalRating.orElse(null);
    }

    @Override
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public void update(Integer id, Rating rating) {
        rating.setId(id);
        ratingRepository.save(rating);
    }

    @Override
    public void deleteById(Integer id) {
        ratingRepository.deleteById(id);
    }
    
    @Override
	public void resetRatingTestTable() {
    	ratingRepository.resetRatingTestTable();
	}
}
