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

    @GetMapping("/search/all")
    public Iterable<NewDTO> searchAll(@RequestParam("q") String query) {
        return newService.searchAll(query);
    }

    @GetMapping("/search")
    public Iterable<NewDTO> search(@RequestParam("q") String query, @RequestParam("p") int page) {
        return newService.search(query, page);
    }

    @GetMapping("/find/all")
    public Iterable<NewDTO> findAll(@RequestParam("q") String query) {
        return newService.findAll(query);
    }

    @GetMapping("/find")
    public Iterable<NewDTO> find(@RequestParam("q") String query, @RequestParam("p") int page) {
        return newService.find(query, page);
    }

}
