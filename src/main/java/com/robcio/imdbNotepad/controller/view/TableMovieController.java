package com.robcio.imdbNotepad.controller.view;

import com.robcio.imdbNotepad.service.update.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TableMovieController extends MovieViewController{
    //TODO export to file, simple with names and years of release and one with links to reimport again
    @Autowired
    private UpdateService updateService;

    @GetMapping("/table")
    public String showTableView(final Model model) {
        return showView(model);
    }

    @Override
    String getViewName() {
        return "table_view";
    }

    @Override
    void customizeModel(final Model model){
        model.addAttribute("editDisabled", updateService.isUpdating());
    }
}
