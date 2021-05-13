package com.robcio.imdbNotepad.controller.crud;

import com.robcio.imdbNotepad.service.MovieService;
import com.robcio.imdbNotepad.service.SessionService;
import com.robcio.imdbNotepad.util.UrlRefiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

@Controller
@RequestMapping("/")
public class AddMovieController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private SessionService sessionService;

    @PostMapping("/add")
    public String add(@RequestParam final String imdbUrl, @RequestParam final String view) {
        final Long id = sessionService.getProfile().getId();
        movieService.add(imdbUrl, id);
        //TODO personal description on why its being saved
        return "redirect:" + view;
    }

    @PostMapping("/add/multi")
    public String addMulti(@RequestParam final String imdbUrls, @RequestParam final String view) {
        final Long id = sessionService.getProfile().getId();
        final List<String> split = UrlRefiner.split(imdbUrls);
        final Predicate<String> check = this::checkUrl;

        split.stream().distinct().filter(check.negate()).forEach(imdbUrl->movieService.addAsync(imdbUrl, id));
        return "redirect:" + view;
    }

    @PutMapping("/add/check")
    @ResponseBody
    public String check(HttpServletRequest request, @RequestParam final String imdbUrl) {
        Locale locale = RequestContextUtils.getLocale(request);
        final String message = ResourceBundle.getBundle("messages", locale)
                                             .getString("modal.add.url.error");
        try {
            if (movieService.exists(imdbUrl))
                return message;
            else
                return "";
        } catch (URISyntaxException e) {
            return message;
        }
    }

    public Boolean checkUrl(final String imdbUrl) {
        try {
            return movieService.exists(imdbUrl);
        } catch (URISyntaxException e) {
            return true;
        }
    }
}
