package com.robcio.imdbNotepad.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ImageMovieController extends MovieViewController{

    @GetMapping("/")
    public String showImageView(final Model model) {
        prepareModel(model);
        return "image_view";
    }
}
