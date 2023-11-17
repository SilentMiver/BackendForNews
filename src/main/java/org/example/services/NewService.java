package org.example.services;

import org.example.dtos.NewDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface NewService {
    List<NewDTO> searchAll(String query);

    List<NewDTO> search(String query, int page);

    List<NewDTO> findAll(String query);

    List<NewDTO> find(String query, int page);

    List<NewDTO> findRatingAll();

    List<NewDTO> searchRatingAll();

}
