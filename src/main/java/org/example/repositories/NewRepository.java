package org.example.repositories;

import org.example.models.New;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NewRepository extends MongoRepository<New, Long> {
    @Query("{'title' : { '$regex': '?0', '$options': 'i' }}")
    List<New> findByTitleRegexIgnoreCase(@Param("keyword") String keyword);

    @Query("{'title' : { '$regex': '?0', '$options': 'i' }}")
    List<New> findByTitleRegexIgnoreCase(@Param("keyword") String keyword, Sort sort);

    @Query("{'title' : { '$regex': '?0', '$options': 'i' }}")
    Page<New> findPageByTitleRegexIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

}
