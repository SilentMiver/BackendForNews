package org.example.web;

import org.example.dtos.NewDTO;
import org.example.exceptions.ClientException;
import org.example.services.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/new")
public class NewController {
    private final NewService newService;

    @Autowired
    public NewController(NewService newService) {
        this.newService = newService;
    }

    @GetMapping("/search/all")
    public String searchAll(@RequestParam("q") String query, @RequestParam(value = "d", required = false, defaultValue = "day") String day, Model model) {
        validateQuery(query);
        model.addAttribute("all", newService.searchAndSaveAllForDisplay(query, day));

        return "news/index"; // Возвращает имя представления
    }

    @GetMapping("/search")
    public Iterable<NewDTO> search(@RequestParam("q") String query, @RequestParam("p") int page, @RequestParam(value = "d", required = false, defaultValue = "day") String day) {
        validateQuery(query);
        validatePage(page);
        return newService.searchAndSave(query, page, day);
    }

    @GetMapping("/find/all")
    public Iterable<NewDTO> findAll(@RequestParam("q") String query, @RequestParam(value = "d", required = false, defaultValue = "day") String day) {
        validateQuery(query);
        return newService.findAll(query, day);
    }

    @GetMapping("/find/all/sort/asc/{sortField}")
    public Iterable<NewDTO> findSortAscAll(@RequestParam("q") String query, @PathVariable("sortField") String sortField, @RequestParam(value = "d", required = false, defaultValue = "day") String day) {
        validateQuery(query);
        return newService.findAll(query, Sort.by(Sort.Direction.ASC, sortField), day);
    }

    @GetMapping("/find/all/sort/desc/{sortField}")
    public Iterable<NewDTO> findSortDescAll(@RequestParam("q") String query, @PathVariable(value = "sortField") String sortField, @RequestParam(value = "d", required = false, defaultValue = "day") String day) {
        validateQuery(query);
        return newService.findAll(query, Sort.by(Sort.Direction.DESC, sortField), day);
    }

    @GetMapping("/find")
    public Iterable<NewDTO> find(@RequestParam("q") String query, @RequestParam("p") int page, @RequestParam(value = "d", required = false, defaultValue = "day") String day) {
        validateQuery(query);
        validatePage(page);
        return newService.find(query, page, day);
    }

    @GetMapping("/find/sort/asc/{sortField}")
    public Iterable<NewDTO> findSortAsc(@RequestParam("q") String query, @RequestParam("p") int page, @PathVariable("sortField") String sortField, @RequestParam(value = "d", required = false, defaultValue = "day") String day) {
        validateQuery(query);
        validatePage(page);
        return newService.find(query, page, Sort.by(Sort.Direction.ASC, sortField), day);
    }

    @GetMapping("/find/sort/desc/{sortField}")
    public Iterable<NewDTO> findSortDesc(@RequestParam("q") String query, @RequestParam("p") int page, @PathVariable("sortField") String sortField, @RequestParam(value = "d", required = false, defaultValue = "day") String day) {
        validateQuery(query);
        validatePage(page);
        return newService.find(query, page, Sort.by(Sort.Direction.DESC, sortField), day);
    }

    @GetMapping("/get")
    public Iterable<NewDTO> get(@RequestParam("q") String query, @RequestParam("p") int page, @RequestParam(value = "d", required = false, defaultValue = "day") String day) {
        validateQuery(query);
        validatePage(page);
        return newService.get(query, page, day);
    }

    @GetMapping("/get/all")
    public Iterable<NewDTO> getAll(@RequestParam("q") String query, @RequestParam(value = "d", required = false, defaultValue = "day") String day) {
        validateQuery(query);
        return newService.getAll(query, day);
    }

    private void validateQuery(String query) {
        if (query.length() < 3) {
            throw new ClientException.InvalidInputException("The length of the request must be greater than or equal to 3.");
        }
    }

    private void validatePage(int page) {
        if (page < 0) {
            throw new ClientException.InvalidInputException("Page cannot be less than 0.");
        }
    }
}
