package com.robcio.imdbNotepad.controller.view;

import com.robcio.imdbNotepad.criteria.OwnershipCriteria;
import com.robcio.imdbNotepad.criteria.SortingCriteria;
import com.robcio.imdbNotepad.criteria.WatchedCriteria;
import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Set;

abstract class MovieViewController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private FilterService filterService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SettingService settingService;

    //TODO perhaps something that specifically takes care of preparing the basic view model
    void prepareModel(final Model model) {
        final WatchedCriteria watchedCriteria = settingService.getSetting(WatchedCriteria.class);
        final SortingCriteria sortingCriteria = settingService.getSetting(SortingCriteria.class);
        final OwnershipCriteria ownershipCriteria = settingService.getSetting(OwnershipCriteria.class);
        final Set<String> activeGenres = settingService.getSettingSet("genres");
        final List<Movie> movieList = movieService.getAll();
        model.addAttribute("noMovies", movieList.isEmpty());
        model.addAttribute("movies", movieList);

        model.addAttribute("genres", filterService.getDistinctGenres());
        model.addAttribute("activeGenres", activeGenres);

        model.addAttribute("selectedProfile", sessionService.getProfile());
        model.addAttribute("profiles", profileService.getAllAsMap());

        model.addAttribute("watchedSortTypes", WatchedCriteria.values());
        model.addAttribute("activeWatchedOption", watchedCriteria);

        model.addAttribute("sortTypes", SortingCriteria.values());
        model.addAttribute("activeSortOption", sortingCriteria);

        model.addAttribute("ownerships", OwnershipCriteria.values());
        model.addAttribute("activeOwnership", ownershipCriteria);
    }

    abstract String getViewName();

    void customizeModel(final Model model){
    }

    public String showView(final Model model) {
        final String viewName = getViewName();
        sessionService.setLastView(viewName);
        if (sessionService.noProfileSelected()){
            return "redirect:/profile/select";
        }
        prepareModel(model);
        customizeModel(model);
        return viewName;
    }
}
