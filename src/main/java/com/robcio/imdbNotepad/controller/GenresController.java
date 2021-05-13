package com.robcio.imdbNotepad.controller;

import com.robcio.imdbNotepad.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@RequestMapping("/")
public class GenresController {

    @Autowired
    private SettingService settingService;


    @PutMapping("/genres")
    public String changeSelectedGenres(@RequestParam(required = false) final Set<String> genres,
                                @RequestParam final String view) {
        settingService.setSettingSet("genres", genres);
        return "redirect:" + view;
    }
}
