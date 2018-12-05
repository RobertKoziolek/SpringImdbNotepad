package com.robcio.imdbNotepad.controller;

import com.robcio.imdbNotepad.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@RequestMapping("/")
public class GenresController {

    private final FilterService filterService;

    @Autowired
    public GenresController(final FilterService filterService) {
        this.filterService = filterService;
    }

    @PutMapping("/genres")
    public String changeSorting(@RequestParam(required = false) final Set<String> genres,
                                @RequestParam final String view) {
        filterService.setGenres(genres);
        return "redirect:" + view;
    }
}
