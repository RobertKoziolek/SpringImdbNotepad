package com.robcio.imdbNotepad.controller.crud;

import com.robcio.imdbNotepad.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class RemoveMovieController {

    private final MovieService movieService;

    @Autowired
    public RemoveMovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @DeleteMapping("/remove/{id}")
    public String remove(@PathVariable final Long id, @RequestParam final String view) {
        movieService.remove(id);
        return "redirect:" + view;
    }
}
