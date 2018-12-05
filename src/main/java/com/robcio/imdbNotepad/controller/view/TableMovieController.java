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

    @Autowired
    private UpdateService updateService;

    @GetMapping("/details")
    public String showTableView(final Model model) {
        prepareModel(model);
        model.addAttribute("editDisabled", updateService.isUpdating());
        return "table_view";
    }
}
