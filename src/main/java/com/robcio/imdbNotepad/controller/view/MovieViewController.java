package com.robcio.imdbNotepad.controller.view;

import com.robcio.imdbNotepad.enumeration.MovieSorting;
import com.robcio.imdbNotepad.service.FilterService;
import com.robcio.imdbNotepad.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

abstract class MovieViewController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private FilterService filterService;

    void prepareModel(final Model model) {
        model.addAttribute("movies", movieService.getAll());
        model.addAttribute("genres", movieService.getDistinctGenres());
        model.addAttribute("activeGenres", filterService.getGenres());
        model.addAttribute("sortTypes", MovieSorting.values());
    }
}
