package com.robcio.imdbNotepad.controller;

import com.robcio.imdbNotepad.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MarkWatchedMovieController {

    private final MovieService movieService;

    @Autowired
    public MarkWatchedMovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @PutMapping("/watched/{id}")
    public String markAsWatched(@PathVariable final Long id) {
        movieService.markAsWatched(id);
        return "redirect:/details";
    }
}
