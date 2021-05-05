package com.robcio.imdbNotepad.controller.crud;

import com.robcio.imdbNotepad.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ConsolidatedProfileController {

    private final ProfileService profileService;

    @Autowired
    public ConsolidatedProfileController(final ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/add")
    public String add(@RequestParam String name, @RequestParam final String view) {
        profileService.add(name);
        return "redirect:" + view;
    }

    @DeleteMapping("/remove/{id}")
    public String remove(@PathVariable final Long id, @RequestParam final String view) {
        profileService.remove(id);
        return "redirect:" + view;
    }
}
