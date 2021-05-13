package com.robcio.imdbNotepad.controller.crud;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.service.MovieService;
import com.robcio.imdbNotepad.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/")
public class EditMovieController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private ProfileService profileService;

    @GetMapping("/edit/{id}")
    public String getEditView(@PathVariable final Long id, final Model model) {
        final Map<Long, String> profiles = profileService.getAllAsMap();
        profiles.put(null, "");
        model.addAttribute("movie", movieService.get(id));
        model.addAttribute("profiles", profiles);
        return "edit";
    }

    @PutMapping("/edit/{id}")
    public String editMovie(@PathVariable final Long id, @ModelAttribute Movie movie) {
        movieService.edit(id, movie);
        return "redirect:/table";
    }
}
