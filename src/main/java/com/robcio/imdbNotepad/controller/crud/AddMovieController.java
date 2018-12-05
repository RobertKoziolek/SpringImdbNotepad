package com.robcio.imdbNotepad.controller.crud;

import com.robcio.imdbNotepad.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class AddMovieController {

    private final MovieService movieService;

    @Autowired
    public AddMovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    public String add(@RequestParam final String imdbUrl) {
        movieService.add(imdbUrl);
        return "redirect:/details";
    }
}
