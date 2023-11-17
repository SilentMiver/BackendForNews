package org.example.services;

import org.example.dtos.NewDTO;

import java.util.List;
import java.util.Optional;

public interface NewService {
    List<NewDTO> searchAll(String query);
    Optional<NewDTO> search(String query);

}
