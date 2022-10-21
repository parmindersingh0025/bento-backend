package com.Bento.Bento.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Bento.Bento.models.Recipe;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {
	Optional<Recipe> findByTitle(String title);

	
}
