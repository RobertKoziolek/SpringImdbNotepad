package com.robcio.imdbNotepad.controller.crud;

import com.robcio.imdbNotepad.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Controller
@RequestMapping("/")
public class AddMovieController {

    private final MovieService movieService;

    @Autowired
    public AddMovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    public String add(@RequestParam final String imdbUrl, @RequestParam final String view) {
        //TODO multi add
        movieService.add(imdbUrl);
        return "redirect:" + view;
    }

    @PutMapping("/add/check")
    @ResponseBody
    public Boolean check(@RequestParam final String imdbUrl) {
        try {
            return movieService.exists(imdbUrl);
        } catch (URISyntaxException e) {
            return true;
        }
    }
}
