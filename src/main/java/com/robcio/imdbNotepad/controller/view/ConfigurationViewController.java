package com.robcio.imdbNotepad.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ConfigurationViewController  {

    @GetMapping("/configuration")
    public String showTableView(final Model model) {
//        model.addAttribute("editDisabled", updateService.isUpdating());
        return "configuration_view";
    }
}
