package org.example.web;

import org.example.dtos.NewRatingDTO;
import org.example.services.NewRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rating")
public class NewRatingController {
    private final NewRatingService service;

    @Autowired
    public NewRatingController(NewRatingService service) {
        this.service = service;
    }
    @GetMapping("/search/all")
    public Iterable<NewRatingDTO> searchAll() {
        return service.searchRatingAndSaveAll();
    }
    @GetMapping("/find/all")
    public Iterable<NewRatingDTO> findAll() {
        return service.findRatingAll();
    }

}
