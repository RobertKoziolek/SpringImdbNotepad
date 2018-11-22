package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MovieCreationService {

    public Movie create(final String imdbUrl){
        try {
            final Document document = Jsoup.connect(imdbUrl).get();
            final String name = document.title();
            final Movie movie = new Movie();
            movie.setName(name);
            movie.setUrl(imdbUrl);
            return movie;
        } catch (final IOException e) {
            throw new RuntimeException("Could not connect to provided URL");
        }
    }

}
