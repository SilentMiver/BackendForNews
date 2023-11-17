package org.example.repositories;

import org.example.models.New;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewRepository extends MongoRepository<New,String> {
}
