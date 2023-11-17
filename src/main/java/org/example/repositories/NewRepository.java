package org.example.repositories;

import org.example.models.New;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NewRepository extends MongoRepository<New,Long> {
    @Query("{'title' : { '$regex': '?0', '$options': 'i' }}")
    List<New> findByTitleRegexIgnoreCase(String keyword);
    @Query("{'title' : { '$regex': '?0', '$options': 'i' }}")
    Page<New> findPageByTitleRegexIgnoreCase(String keyword, Pageable pageable);
}
