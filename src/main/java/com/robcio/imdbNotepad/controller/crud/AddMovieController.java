package com.robcio.imdbNotepad.controller.crud;

import com.robcio.imdbNotepad.service.MovieService;
import com.robcio.imdbNotepad.util.UrlRefiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Predicate;

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
        movieService.add(imdbUrl);
        //TODO personal description on why its being saved
        //TODO profile info on who adds it so that can be filtered later
        return "redirect:" + view;
    }

    @PostMapping("/add/multi")
    public String addMulti(@RequestParam final String imdbUrls, @RequestParam final String view) {
        final List<String> split = UrlRefiner.split(imdbUrls);
        final Predicate<String> check = this::check;
        split.stream().distinct().filter(check.negate()).forEach(movieService::addAsync);
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
