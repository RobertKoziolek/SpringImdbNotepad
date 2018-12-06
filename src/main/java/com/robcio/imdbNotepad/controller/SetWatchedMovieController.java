package com.robcio.imdbNotepad.controller;

import com.robcio.imdbNotepad.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class SetWatchedMovieController {

    private final MovieService movieService;

    @Autowired
    public SetWatchedMovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @PutMapping("/watched/{id}")
    public String setWatched(@PathVariable final Long id, @RequestParam final Boolean watched) {
        movieService.setWatched(id, watched);
        return "redirect:/table";
    }
}
