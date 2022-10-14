package com.Bento.Bento.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Bento.Bento.models.Ingredients;


@Repository
public interface IngredientsRepository extends MongoRepository<Ingredients, String> {
	
	Optional<Ingredients> findByTitle(String title);

}