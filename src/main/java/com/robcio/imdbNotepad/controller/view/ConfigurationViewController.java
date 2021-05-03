package com.robcio.imdbNotepad.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ConfigurationViewController extends MovieViewController{

    @GetMapping("/configuration")
    public String showTableView(final Model model) {
        prepareModel(model);
        return "configuration_view";
    }
}
