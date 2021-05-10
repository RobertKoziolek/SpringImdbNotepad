package com.robcio.imdbNotepad.controller.view;

import com.robcio.imdbNotepad.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TilesViewController extends MovieViewController{

    @Autowired
    private SessionService sessionService;

    @GetMapping("/tiles")
    public String showTilesView(final Model model) {
        return showView(model);
    }

    @Override
    String getViewName() {
        return "tiles_view";
    }
}
