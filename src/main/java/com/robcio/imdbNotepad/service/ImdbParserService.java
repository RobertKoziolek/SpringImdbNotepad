package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ImdbParserService {

    public Movie parse(final String imdbUrl) {
        return parse(imdbUrl, "en");
    }

    private Movie parse(final String imdbUrl, final String languageCode) {
        try {
            final Document document = Jsoup.connect(imdbUrl)
                                           .header("Accept-Language", languageCode)
                                           .get();
            final String name = document.title();
            final Movie movie = new Movie();
            movie.setName(name);
            movie.setUrl(imdbUrl);
            return movie;
        } catch (final IOException e) {
            throw new RuntimeException("Could not connect to provided URL");
        }
    }

    public void adjustLanguage(final Movie movie, final String languageCode) {
        final String url = movie.getUrl();
        final Movie parsed = parse(url);
        movie.setName(parsed.getName());
    }

}
