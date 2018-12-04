package com.robcio.imdbNotepad.controller.view;

import com.robcio.imdbNotepad.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.thymeleaf.util.SetUtils;

import java.util.Set;

abstract class MovieViewController {

    private static final Logger logger = LoggerFactory.getLogger(MovieViewController.class);

    @Autowired
    private MovieService movieService;

    void prepareModel(final Model model, final Set<String> genres){
        if (!SetUtils.isEmpty(genres)) {
            logger.debug("Returning genres {}", genres);
            model.addAttribute("movies", movieService.getByGenres(genres));
        } else {
            model.addAttribute("movies", movieService.getAllByType());
        }
        model.addAttribute("genres", movieService.getDistinctGenres());
    }
}
