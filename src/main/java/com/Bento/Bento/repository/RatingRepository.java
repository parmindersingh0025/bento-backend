package com.Bento.Bento.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Bento.Bento.models.Rating;


@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

	List<Rating> findByParentId(String parentId);

	List<Rating> findByParentIdAndKidName(String parentId,String kidName);
	
}