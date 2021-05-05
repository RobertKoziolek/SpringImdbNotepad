package com.robcio.imdbNotepad.controller.view;

import com.robcio.imdbNotepad.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ConfigurationViewController extends MovieViewController{

    @Autowired
    private ProfileService profileService;

    @GetMapping("/configuration")
    public String showTableView(final Model model) {
        prepareModel(model);
        model.addAttribute("profiles", profileService.getAll());
        return "configuration_view";
    }
}
