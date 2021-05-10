package com.robcio.imdbNotepad.controller;

import com.robcio.imdbNotepad.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DefaultViewController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/")
    public String showDefaultView() {
        if (sessionService.noProfileSelected()){
            return "redirect:/profile/select";
        }
        final String lastView = sessionService.getLastView();

        //TODO remember last page
        return "redirect:/"+lastView;
    }
}
