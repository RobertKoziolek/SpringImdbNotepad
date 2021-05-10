package com.robcio.imdbNotepad.controller.crud;

import com.robcio.imdbNotepad.entity.Profile;
import com.robcio.imdbNotepad.service.ProfileService;
import com.robcio.imdbNotepad.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ConsolidatedProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/add")
    public String add(@RequestParam String name, @RequestParam final String view) {
        profileService.add(name);
        //TODO color selection for profile so that it can be easily seen in table/tiles view who added it
        return "redirect:" + view;
    }

    @DeleteMapping("/remove/{id}")
    public String remove(@PathVariable final Long id, @RequestParam final String view) {
        profileService.remove(id);
        return "redirect:" + view;
    }

    @GetMapping("/select")
    public String showTableView(final Model model) {
        final List<Profile> profiles = profileService.getAll();
        model.addAttribute("noProfiles", profiles.isEmpty());
        model.addAttribute("profiles", profiles);
        return "profile_selection_view";
    }

    @PutMapping("/select/{id}")
    public String selectProfile(final Model model, @PathVariable final Long id) {
        sessionService.setProfile(id);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(final Model model) {
        sessionService.setProfile(0L);
        return "redirect:/";
    }
}
