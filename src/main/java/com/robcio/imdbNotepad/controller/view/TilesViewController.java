package com.robcio.imdbNotepad.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TilesViewController extends MovieViewController{

    @GetMapping("/tiles")
    public String showTilesView(final Model model) {
        prepareModel(model);
        return "tiles_view";
    }
}
