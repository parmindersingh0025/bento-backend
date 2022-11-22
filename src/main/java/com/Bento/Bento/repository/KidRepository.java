package com.Bento.Bento.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Bento.Bento.models.Kid;
import com.Bento.Bento.models.UserModel;

@Repository
public interface KidRepository extends MongoRepository<Kid, String> {

	Optional<Kid> findByParentId(String parentId);
	
}
