package com.robcio.imdbNotepad.controller;

import com.robcio.imdbNotepad.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String showNotepad(final Model model) {
        model.addAttribute("movies", movieService.getAll());
        return "home";
    }

    @PostMapping("/add")
    public String add(@RequestParam final String imdbUrl) {
        movieService.add(imdbUrl);
        return "redirect:/";
    }

    @DeleteMapping("/remove/{id}")
    public String remove(@PathVariable final Long id){
        movieService.remove(id);
        return "redirect:/";
    }

}
