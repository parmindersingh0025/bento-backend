package com.Bento.Bento.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends MongoRepository<com.Bento.Bento.models.FileDB, String> {

}