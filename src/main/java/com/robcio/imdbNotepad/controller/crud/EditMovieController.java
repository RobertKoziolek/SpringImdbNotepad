package com.robcio.imdbNotepad.controller.crud;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class EditMovieController {

    private final MovieService movieService;

    @Autowired
    public EditMovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/edit/{id}")
    public String getEditView(@PathVariable final Long id, final Model model) {
        model.addAttribute("movie", movieService.get(id));
        return "edit";
    }

    @PutMapping("/edit/{id}")
    public String editMovie(@PathVariable final Long id, @ModelAttribute Movie movie) {
        movieService.edit(id, movie);
        return "redirect:/table";
    }
}
