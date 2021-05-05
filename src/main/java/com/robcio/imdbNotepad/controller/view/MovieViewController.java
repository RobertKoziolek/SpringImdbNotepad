package com.robcio.imdbNotepad.controller.view;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.enumeration.MovieSorting;
import com.robcio.imdbNotepad.enumeration.WatchedSorting;
import com.robcio.imdbNotepad.service.FilterService;
import com.robcio.imdbNotepad.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.List;

abstract class MovieViewController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private FilterService filterService;

    //TODO trailer if available
    void prepareModel(final Model model) {
        final List<Movie> movieList = movieService.getAll();
        model.addAttribute("noMovies", movieList.isEmpty());
        model.addAttribute("movies", movieList);
        model.addAttribute("genres", filterService.getDistinctGenres());
        //TODO profile selection
        model.addAttribute("activeGenres", filterService.getGenres());
        model.addAttribute("watchedSortTypes", WatchedSorting.values());
        model.addAttribute("sortTypes", MovieSorting.values());
    }
}
