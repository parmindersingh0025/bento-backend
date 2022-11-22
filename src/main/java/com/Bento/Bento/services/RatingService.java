package com.Bento.Bento.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bento.Bento.models.Rating;
import com.Bento.Bento.models.Recipe;
import com.Bento.Bento.repository.RatingRepository;
import com.Bento.Bento.repository.RecipeRepository;

@Service
public class RatingService {

	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	public void addRating(String recipeId, String rating, String kidName, String parentId) {
		// TODO Auto-generated method stub
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		Recipe recipe = new Recipe();
		if(recipeOptional.isPresent()) {
			recipe = recipeOptional.get();
		}
		Rating ratingObject = new Rating();
		ratingObject.setParentId(rating);
		ratingObject.setKidName(kidName);
		ratingObject.setParentId(parentId);
		ratingObject.setRating(rating);
		ratingObject.setRecipeName(recipe.getTitle());
		ratingObject.setCreatedAt(LocalDate.now());
		ratingRepository.save(ratingObject);
	}

	public List<Rating> getRating(String parentId, String kidName) {
		// TODO Auto-generated method stub
		List<Rating> rating = ratingRepository.findByParentIdAndKidName(parentId,kidName);
		return rating;
	}
	
}
