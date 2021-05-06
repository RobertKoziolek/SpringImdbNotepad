package com.robcio.imdbNotepad.controller;

import com.robcio.imdbNotepad.enumeration.SortingCriteria;
import com.robcio.imdbNotepad.enumeration.WatchedCriteria;
import com.robcio.imdbNotepad.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class SortingMovieController {

    private final SettingService settingService;

    @Autowired
    public SortingMovieController(final SettingService settingService) {
        this.settingService = settingService;
    }

    @PutMapping("/sorting")
    public String changeSorting(@RequestParam final SortingCriteria sorting, @RequestParam final String view) {
        settingService.setSetting(sorting);
        return "redirect:" + view;
    }

    @PutMapping("/sorting/watched")
    public String setHideWatched(@RequestParam final WatchedCriteria watchedCriteria, @RequestParam final String view) {
        settingService.setSetting(watchedCriteria);
        return "redirect:" + view;
    }
}
