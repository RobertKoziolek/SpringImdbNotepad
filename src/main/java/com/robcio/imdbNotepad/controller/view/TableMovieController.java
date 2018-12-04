package com.robcio.imdbNotepad.controller.view;

import com.robcio.imdbNotepad.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.SetUtils;

import java.util.Set;

@Controller
@RequestMapping("/")
public class TableMovieController {

    private static final Logger logger = LoggerFactory.getLogger(TableMovieController.class);

    private final MovieService movieService;

    @Autowired
    public TableMovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/details")
    public String showTableView(final Model model, @RequestParam(required = false) final Set<String> genres) {
        if (!SetUtils.isEmpty(genres)) {
            logger.debug("Returning genres {}", genres);
            model.addAttribute("movies", movieService.getByGenres(genres));
        } else {
            model.addAttribute("movies", movieService.getAllByType());
        }
        model.addAttribute("genres", movieService.getDistinctGenres());
        return "table_view";
    }

}
