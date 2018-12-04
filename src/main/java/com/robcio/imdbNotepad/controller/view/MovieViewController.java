package com.robcio.imdbNotepad.controller.view;

import com.robcio.imdbNotepad.enumeration.MovieSorting;
import com.robcio.imdbNotepad.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.Set;

abstract class MovieViewController {

    @Autowired
    private MovieService movieService;

    void prepareModel(final Model model, final Set<String> genres) {
        model.addAttribute("movies", movieService.getAll(genres));
        model.addAttribute("genres", movieService.getDistinctGenres());
        model.addAttribute("sortTypes", MovieSorting.values());
    }
}
