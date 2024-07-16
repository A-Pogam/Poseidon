package com.poseidoncapitalsolution.trading.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.poseidoncapitalsolution.trading.model.Rating;
import com.poseidoncapitalsolution.trading.service.contracts.IRatingService;

@SpringBootTest
@TestPropertySource(locations = "file:src/main/resources/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RatingServiceIT {

	@Autowired
	private IRatingService iRatingService;
	
	@BeforeAll
	public void fillRatingTable() {
		for (int i = 1; i <= 3; i++) {
			iRatingService.save(new Rating(null, "moodysRating" + i, "sandPRating" + i, "FitchRating" + i, i));
		}
	}
	
	@AfterAll
	public void resetRatingTable() {
		iRatingService.resetRatingTestTable();
	}
	
	@Test
	public void getRatings_returnRatings() {
		List<Rating> ratings = iRatingService.findAll();
		
		assertThat(ratings).isNotEmpty();
		assertThat(ratings.getFirst().getId()).isEqualTo(1);
		assertThat(ratings.getFirst().getMoodysRating()).isEqualTo("moodysRating1");
		assertThat(ratings.getFirst().getSandPRating()).isEqualTo("sandPRating1");
		assertThat(ratings.getFirst().getFitchRating()).isEqualTo("FitchRating1");
		assertThat(ratings.getFirst().getOrderNumber()).isEqualTo(1);
	}

	@Test
	public void getRatingById_returnRating() {
		Rating rating = iRatingService.findById(1);
		
		assertThat(rating.getId()).isEqualTo(1);
		assertThat(rating.getMoodysRating()).isEqualTo("moodysRating1");
		assertThat(rating.getSandPRating()).isEqualTo("sandPRating1");
		assertThat(rating.getFitchRating()).isEqualTo("FitchRating1");
		assertThat(rating.getOrderNumber()).isEqualTo(1);
	}
	
	@Test
	public void getRatingById_returnNull() {
		Rating rating = iRatingService.findById(0);
		
		assertThat(rating).isEqualTo(null);
	}

	@Test
	public void saveRating_returnRating() {
		Rating newRating = new Rating(null, "NewMoodysRating", "NewSandPRating", "NewFitchRating", 42);
		
		Rating ratingAdded = iRatingService.save(newRating);
		
		assertThat(ratingAdded.getMoodysRating()).isEqualTo(newRating.getMoodysRating());
		assertThat(ratingAdded.getSandPRating()).isEqualTo(newRating.getSandPRating());
		assertThat(ratingAdded.getFitchRating()).isEqualTo(newRating.getFitchRating());
		assertThat(ratingAdded.getOrderNumber()).isEqualTo(newRating.getOrderNumber());
	}
	
	@Test
	public void updateRating_returnUpdatedBid() {
	    Integer existingRatingId = 3;
	    Rating updatedRating = new Rating(null, "UpdatedMoodysRating", "UpdatedSandPRating", "UpdatedFitchRating", 33);
	
	    iRatingService.update(existingRatingId, updatedRating);
	
	    Rating rating = iRatingService.findById(existingRatingId);
	    assertThat(rating).isNotNull();
	    assertThat(rating.getMoodysRating()).isEqualTo(updatedRating.getMoodysRating());
	    assertThat(rating.getSandPRating()).isEqualTo(updatedRating.getSandPRating());
	    assertThat(rating.getFitchRating()).isEqualTo(updatedRating.getFitchRating());
	    assertThat(rating.getOrderNumber()).isEqualTo(updatedRating.getOrderNumber());
	}
	
	@Test
	public void deleteRating_deleteRating() {
		Rating ratingToDelete = iRatingService.findById(2);
		
		iRatingService.deleteById(2);
		
		assertThat(!iRatingService.findAll().contains(ratingToDelete));
	}
}
