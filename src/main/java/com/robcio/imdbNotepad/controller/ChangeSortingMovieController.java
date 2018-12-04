package com.robcio.imdbNotepad.controller;

import com.robcio.imdbNotepad.enumeration.MovieSorting;
import com.robcio.imdbNotepad.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class ChangeSortingMovieController {

    private final MovieService movieService;

    @Autowired
    public ChangeSortingMovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @PutMapping("/sorting")
    public String changeSorting(@RequestParam final MovieSorting sorting, @RequestParam final String view) {
        movieService.setMovieSorting(sorting);
        return "redirect:"+view;
    }
}
