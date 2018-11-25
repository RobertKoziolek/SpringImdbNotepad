package com.robcio.imdbNotepad.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.response.MovieInformation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

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
            final Elements type = document.getElementsByAttributeValue("type", "application/ld+json");

            final ObjectMapper objectMapper = new ObjectMapper();
            final MovieInformation movieInformation = objectMapper.readValue(type.html(), MovieInformation.class);
            return Movie.builder()
                        .name(movieInformation.getName())
                        .description(movieInformation.getDescription())
                        .duration(movieInformation.getDuration())
                        .dateCreated(movieInformation.getDateCreated())
                        .rating(movieInformation.getRating())
                        .imageUrl(movieInformation.getImage())
                        .genres(movieInformation.getGenre()
                                                .stream()
                                                .reduce(String::concat)
                                                .get())
                        .url(imdbUrl)
                        .build();
        } catch (final IOException e) {
            throw new RuntimeException("Could not connect to: " + imdbUrl);
        }
    }

    @Async
    public CompletableFuture<Movie> parseAsync(final String imdbUrl) {
        return CompletableFuture.completedFuture(parse(imdbUrl));
    }

    public void adjustLanguage(final Movie movie, final String languageCode) {
        final Movie parsed = parse(movie.getUrl());
        movie.setName(parsed.getName());
    }

}
