package com.robcio.imdbNotepad.controller;

import com.robcio.imdbNotepad.enumeration.MovieSorting;
import com.robcio.imdbNotepad.enumeration.WatchedSorting;
import com.robcio.imdbNotepad.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class SortingMovieController {

    private final SortService sortService;

    @Autowired
    public SortingMovieController(final SortService sortService) {
        this.sortService = sortService;
    }

    @PutMapping("/sorting")
    public String changeSorting(@RequestParam final MovieSorting sorting, @RequestParam final String view) {
        sortService.setMovieSorting(sorting);
        return "redirect:" + view;
    }

    @PutMapping("/sorting/watched")
    public String setHideWatched(@RequestParam final WatchedSorting watchedSorting, @RequestParam final String view) {
        sortService.setWatchedSorting(watchedSorting);
        return "redirect:" + view;
    }
}
