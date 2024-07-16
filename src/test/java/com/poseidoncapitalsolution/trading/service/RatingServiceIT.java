package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.Rating;
import com.poseidoncapitalsolution.trading.repository.contracts.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceIT {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    private Rating rating;

    @BeforeEach
    public void setUp() {
        rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Aaa");
        rating.setSandPRating("AAA");
        rating.setFitchRating("AAA");
        rating.setOrderNumber(1);
    }

    @Test
    public void testFindAll() {
        List<Rating> ratings = Arrays.asList(rating, new Rating());
        when(ratingRepository.findAll()).thenReturn(ratings);

        List<Rating> result = ratingService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(rating));

        Rating result = ratingService.findById(1);

        assertNotNull(result);
        assertEquals(rating.getId(), result.getId());
        verify(ratingRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testFindById_NotFound() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());

        Rating result = ratingService.findById(1);

        assertNull(result);
        verify(ratingRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testSave() {
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating result = ratingService.save(rating);

        assertNotNull(result);
        assertEquals(rating.getId(), result.getId());
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    public void testUpdate() {
        ratingService.update(1, rating);

        verify(ratingRepository, times(1)).save(rating);
        assertEquals(1, rating.getId());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(ratingRepository).deleteById(anyInt());

        ratingService.deleteById(1);

        verify(ratingRepository, times(1)).deleteById(anyInt());
    }
}
