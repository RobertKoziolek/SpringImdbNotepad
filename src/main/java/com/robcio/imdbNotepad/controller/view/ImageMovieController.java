package com.robcio.imdbNotepad.controller.view;

import com.robcio.imdbNotepad.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ImageMovieController {

    private final MovieService movieService;

    @Autowired
    public ImageMovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String showImageView(final Model model) {
        model.addAttribute("movies", movieService.getAllByType());
        return "image_view";
    }

}
