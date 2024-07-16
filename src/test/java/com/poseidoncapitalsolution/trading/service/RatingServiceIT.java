package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.Rating;
import com.poseidoncapitalsolution.trading.service.contracts.IRatingService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "file:src/main/resources/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RatingServiceIT {

    @Autowired
    private IRatingService iRatingService;

    @BeforeAll
    public void fillRatingTable() {
        for (int i = 1; i <= 3; i++) {
            Rating rating = new Rating();
            rating.setMoodysRating("Moodys" + i);
            rating.setSandPRating("S&P" + i);
            rating.setFitchRating("Fitch" + i);
            rating.setOrderNumber(i);
            iRatingService.save(rating);
        }
    }

    @AfterAll
    public void resetRatingTable() {
        iRatingService.findAll().forEach(rating -> iRatingService.deleteById(rating.getId()));
    }

    @Test
    public void getRatingById_returnRating() {
        Rating rating = iRatingService.findById(1);

        assertThat(rating).isNotNull();
        assertThat(rating.getMoodysRating()).isEqualTo("Moodys1");
        assertThat(rating.getSandPRating()).isEqualTo("S&P1");
        assertThat(rating.getFitchRating()).isEqualTo("Fitch1");
        assertThat(rating.getOrderNumber()).isEqualTo(1);
    }

    @Test
    public void getRatingById_returnNull() {
        Rating rating = iRatingService.findById(0);

        assertThat(rating).isNull();
    }

    @Test
    public void addOrUpdateRating_returnRating() {
        Rating newRating = new Rating();
        newRating.setMoodysRating("NewMoodys");
        newRating.setSandPRating("NewS&P");
        newRating.setFitchRating("NewFitch");
        newRating.setOrderNumber(99);

        iRatingService.save(newRating);

        List<Rating> ratings = iRatingService.findAll();
        Rating ratingAdded = ratings.stream()
                .filter(r -> "NewMoodys".equals(r.getMoodysRating()))
                .findFirst()
                .orElse(null);

        assertThat(ratingAdded).isNotNull();
        assertThat(ratingAdded.getMoodysRating()).isEqualTo(newRating.getMoodysRating());
        assertThat(ratingAdded.getSandPRating()).isEqualTo(newRating.getSandPRating());
        assertThat(ratingAdded.getFitchRating()).isEqualTo(newRating.getFitchRating());
        assertThat(ratingAdded.getOrderNumber()).isEqualTo(newRating.getOrderNumber());
    }

    @Test
    public void updateRating_returnUpdatedRating() {
        Integer existingRatingId = 1;
        Rating updatedRating = new Rating();
        updatedRating.setId(existingRatingId);
        updatedRating.setMoodysRating("UpdatedMoodys");
        updatedRating.setSandPRating("UpdatedS&P");
        updatedRating.setFitchRating("UpdatedFitch");
        updatedRating.setOrderNumber(100);

        iRatingService.update(existingRatingId, updatedRating);

        Rating rating = iRatingService.findById(existingRatingId);
        assertThat(rating).isNotNull();
        assertThat(rating.getMoodysRating()).isEqualTo("UpdatedMoodys");
        assertThat(rating.getSandPRating()).isEqualTo("UpdatedS&P");
        assertThat(rating.getFitchRating()).isEqualTo("UpdatedFitch");
        assertThat(rating.getOrderNumber()).isEqualTo(100);
    }

    @Test
    public void deleteRating_deleteRating() {
        Integer ratingIdToDelete = 2;
        Rating ratingToDelete = iRatingService.findById(ratingIdToDelete);

        assertThat(ratingToDelete).isNotNull();

        iRatingService.deleteById(ratingIdToDelete);

        Rating deletedRating = iRatingService.findById(ratingIdToDelete);
        assertThat(deletedRating).isNull();
    }
}
