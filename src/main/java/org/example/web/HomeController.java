package org.example.web;

import io.micrometer.common.util.StringUtils;
import org.example.dtos.NewDTO;
import org.example.exceptions.ClientException;
import org.example.services.NewRatingService;
import org.example.services.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final NewService newService;
    private final NewRatingService service;
    @Autowired
    public HomeController(NewService newService, NewRatingService service) {
        this.newService = newService;
        this.service = service;
    }
    @GetMapping("/all")
    public String getAllNews(@RequestParam(name = "dayFilter", required = false , defaultValue = "day") String dayFilter,
                               @RequestParam(name = "queryFilter", required = false, defaultValue = "Россия") String queryFilter,

                               Model model) {

        Iterable<NewDTO> news = null;

            // если день и запрос
            if (StringUtils.isNotBlank(dayFilter) && StringUtils.isNotBlank(queryFilter)) {
                validateQuery(queryFilter);

                news = newService.getAll(queryFilter, queryFilter);
//            } else if (StringUtils.isNotBlank(dayFilter)) {
//                news = newService.finaAllByBrandName(dayFilter);
//            } else if (StringUtils.isNotBlank(queryFilter)) {
//                news = newService.finaAllByPrice(Integer.parseInt(queryFilter));
//            } else {
//                news = newService.getAll();
            }
            model.addAttribute("allNews", news);

            return "news/home";




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
