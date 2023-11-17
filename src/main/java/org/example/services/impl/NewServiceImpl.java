package org.example.services.impl;

import org.example.dtos.NewDTO;
import org.example.repositories.NewRepository;
import org.example.services.NewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewServiceImpl implements NewService {
    private NewRepository newRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public NewServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setNewRepository(NewRepository newRepository) {
        this.newRepository = newRepository;
    }


    @Override
    public List<NewDTO> searchAll(String query) {
        return null;
    }

    @Override
    public Optional<NewDTO> search(String query) {
        return Optional.empty();
    }
}
