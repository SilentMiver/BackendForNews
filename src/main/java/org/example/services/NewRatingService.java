package org.example.services;

import org.example.dtos.NewRatingDTO;

import java.util.List;

public interface NewRatingService {
    List<NewRatingDTO> findRatingAll();

    List<NewRatingDTO> searchRatingAll();
}
