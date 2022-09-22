package com.Bento.Bento.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Bento.Bento.models.UserModel;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {

	Optional<UserModel> findByUserName(String username);
	
}
