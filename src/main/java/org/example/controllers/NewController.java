package org.example.controllers;

import org.example.dtos.NewDTO;
import org.example.services.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/new")
public class NewController {
    private final NewService newService;

    @Autowired
    public NewController(NewService newService) {
        this.newService = newService;
    }
    @GetMapping()
    public Iterable<NewDTO> searchAll(@RequestParam String query){
        return newService.searchAll(query);
    }

}
