package com.robcio.imdbNotepad.controller;

import com.robcio.imdbNotepad.criteria.OwnershipCriteria;
import com.robcio.imdbNotepad.criteria.SortingCriteria;
import com.robcio.imdbNotepad.criteria.WatchedCriteria;
import com.robcio.imdbNotepad.service.SessionService;
import com.robcio.imdbNotepad.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class SortingMovieController {

    @Autowired
    private SettingService settingService;
    @Autowired
    private SessionService sessionService;

    @PutMapping("/sorting")
    public String changeSorting(@RequestParam final SortingCriteria sorting, @RequestParam final String view) {
        settingService.setSetting(sorting);
        //TODO move these to session
        return "redirect:" + view;
    }

    @PutMapping("/sorting/watched")
    public String setHideWatched(@RequestParam final WatchedCriteria watchedCriteria, @RequestParam final String view) {
        settingService.setSetting(watchedCriteria);
        return "redirect:" + view;
    }

    @PutMapping("/sorting/ownership")
    public String setProfileOwnershipFilter(@RequestParam final OwnershipCriteria ownershipCriteria, @RequestParam final String view) {
        settingService.setSetting(ownershipCriteria);
        return "redirect:" + view;
    }
}
