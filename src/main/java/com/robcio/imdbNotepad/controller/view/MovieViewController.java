package com.robcio.imdbNotepad.controller.view;

import com.robcio.imdbNotepad.enumeration.MovieSorting;
import com.robcio.imdbNotepad.enumeration.WatchedSorting;
import com.robcio.imdbNotepad.service.FilterService;
import com.robcio.imdbNotepad.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

abstract class MovieViewController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private FilterService filterService;

    //TODO trailer if available
    //TODO in case of no movies present in db should not even let genres modal open. Add some info to add movies
    void prepareModel(final Model model) {
        model.addAttribute("movies", movieService.getAll());
        model.addAttribute("genres", filterService.getDistinctGenres());
        model.addAttribute("activeGenres", filterService.getGenres());
        model.addAttribute("watchedSortTypes", WatchedSorting.values());
        model.addAttribute("sortTypes", MovieSorting.values());
    }
}
