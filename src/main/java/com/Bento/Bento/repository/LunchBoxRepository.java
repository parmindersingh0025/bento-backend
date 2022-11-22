package com.Bento.Bento.repository;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Bento.Bento.models.LunchBox;


@Repository
public interface LunchBoxRepository extends MongoRepository<LunchBox, String> {

	LunchBox findTopByParentIdOrderByCreatedAtDesc(String parentId);

	Optional<LunchBox> findByParentIdAndRecipeId(String parentId, String recipeId);

	List<LunchBox> findByParentId(String parentId);
	
}