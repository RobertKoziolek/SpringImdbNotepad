package com.robcio.imdbNotepad.controller.crud;

import com.robcio.imdbNotepad.service.update.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UpdateMovieController {

    private final UpdateService updateService;

    @Autowired
    public UpdateMovieController(final UpdateService updateService) {
        this.updateService = updateService;
    }

    @PutMapping("/update")
    public String updateAll() {
        updateService.validateForUpdate();
        updateService.updateAll();
        return "redirect:/details";
    }
}
