package com.robcio.imdbNotepad.controller;

import com.robcio.imdbNotepad.entity.Movie;
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

    @GetMapping("/details")
    public String showNotepad(final Model model) {
        model.addAttribute("movies", movieService.getAll());
        return "home";
    }

    @GetMapping("/")
    public String showNotepadImages(final Model model) {
        model.addAttribute("movies", movieService.getAll());
        return "image_view";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("movie", movieService.get(id));
        return "edit";
    }

    @PostMapping("/add")
    public String add(@RequestParam final String imdbUrl) {
        movieService.add(imdbUrl);
        return "redirect:/";
    }

    @DeleteMapping("/remove/{id}")
    public String remove(@PathVariable final Long id) {
        movieService.remove(id);
        return "redirect:/";
    }

    @PutMapping("/update")
    public String updateInfo() {
        movieService.updateAll();
        return "redirect:/";
    }

    @PutMapping("/edit/{id}")
    public String adjustLanguage(@PathVariable final Long id, @ModelAttribute Movie movie) {
        movieService.edit(id, movie);
        return "redirect:/";
    }

    @PutMapping("/watched/{id}")
    public String markAsWatched(@PathVariable final Long id) {
        movieService.markAsWatched(id);
        return "redirect:/";
    }

}
