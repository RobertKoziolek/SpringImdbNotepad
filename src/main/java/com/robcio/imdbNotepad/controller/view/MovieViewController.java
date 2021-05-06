package com.robcio.imdbNotepad.controller.view;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.enumeration.SortingCriteria;
import com.robcio.imdbNotepad.enumeration.WatchedCriteria;
import com.robcio.imdbNotepad.service.FilterService;
import com.robcio.imdbNotepad.service.MovieService;
import com.robcio.imdbNotepad.service.ProfileService;
import com.robcio.imdbNotepad.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.List;

abstract class MovieViewController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private FilterService filterService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private SettingService settingService;

    //TODO trailer if available
    void prepareModel(final Model model) {
        final WatchedCriteria watchedCriteria = settingService.getSetting(WatchedCriteria.class);
        final SortingCriteria sortingCriteria = settingService.getSetting(SortingCriteria.class);
        final List<Movie> movieList = movieService.getAll();
        model.addAttribute("noMovies", movieList.isEmpty());
        model.addAttribute("movies", movieList);
        model.addAttribute("genres", filterService.getDistinctGenres());
        model.addAttribute("profiles", profileService.getAll());
        //TODO profile selection
        model.addAttribute("activeGenres", filterService.getGenres());
        model.addAttribute("watchedSortTypes", WatchedCriteria.values());
        model.addAttribute("activeWatchedOption", watchedCriteria);
        model.addAttribute("sortTypes", SortingCriteria.values());
        model.addAttribute("activeSortOption", sortingCriteria);
    }
}
