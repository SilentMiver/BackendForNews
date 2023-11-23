package org.example.controllers;

import org.example.dtos.NewDTO;
import org.example.services.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/new")
public class NewController {
    private final NewService newService;

    @Autowired
    public NewController(NewService newService) {
        this.newService = newService;
    }

    @GetMapping("/search/all")
    public Iterable<NewDTO> searchAll(@RequestParam("q") String query, @RequestParam(value = "d") String day) {
        return newService.searchAndSaveAll(query, day);
    }

    @GetMapping("/search")
    public Iterable<NewDTO> search(@RequestParam("q") String query, @RequestParam("p") int page, @RequestParam(value = "d") String day) {
        return newService.searchAndSave(query, page, day);
    }

    @GetMapping("/find/all")
    public Iterable<NewDTO> findAll(@RequestParam("q") String query, @RequestParam(value = "d") String day) {
        return newService.findAll(query, day);
    }

    @GetMapping("/find/sort/asc/{sortField}/all")
    public Iterable<NewDTO> findSortAscAll(@RequestParam("q") String query, @PathVariable("sortField") String sortField, @RequestParam(value = "d") String day) {
        return newService.findAll(query, Sort.by(Sort.Direction.ASC, sortField), day);
    }

    @GetMapping("/find/sort/desc/{sortField}/all")
    public Iterable<NewDTO> findSortDescAll(@RequestParam("q") String query, @PathVariable("sortField") String sortField, @RequestParam(value = "d") String day) {
        return newService.findAll(query, Sort.by(Sort.Direction.DESC, sortField), day);
    }

    @GetMapping("/find")
    public Iterable<NewDTO> find(@RequestParam("q") String query, @RequestParam("p") int page, @RequestParam(value = "d") String day) {
        return newService.find(query, page, day);
    }

    @GetMapping("/find/sort/asc/{sortField}")
    public Iterable<NewDTO> findSortAsc(@RequestParam("q") String query, @RequestParam("p") int page, @PathVariable("sortField") String sortField, @RequestParam(value = "d") String day) {
        return newService.find(query, page, Sort.by(Sort.Direction.ASC, sortField), day);
    }

    @GetMapping("/find/sort/desc/{sortField}")
    public Iterable<NewDTO> findSortDesc(@RequestParam("q") String query, @RequestParam("p") int page, @PathVariable("sortField") String sortField, @RequestParam(value = "d") String day) {
        return newService.find(query, page, Sort.by(Sort.Direction.DESC, sortField), day);
    }

    @GetMapping("/get")
    public Iterable<NewDTO> get(@RequestParam("q") String query, @RequestParam("p") int page, @RequestParam(value = "d") String day) {
        return newService.get(query, page, day);
    }

    @GetMapping("/get/all")
    public Iterable<NewDTO> getAll(@RequestParam("q") String query, @RequestParam(value = "d") String day) {
        return newService.getAll(query, day);
    }
}
