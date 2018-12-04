package com.robcio.imdbNotepad.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@RequestMapping("/")
public class ImageMovieController extends MovieViewController{

    @GetMapping("/")
    public String showImageView(final Model model, @RequestParam(required = false) final Set<String> genres) {
        prepareModel(model, genres);
        return "image_view";
    }
}
