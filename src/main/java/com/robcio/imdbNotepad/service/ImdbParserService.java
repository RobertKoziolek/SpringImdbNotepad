package com.robcio.imdbNotepad.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.response.MovieInformation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class ImdbParserService {

    private final ObjectMapper objectMapper;

    public ImdbParserService(){
        objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    public Movie parse(final String imdbUrl) {
        return parse(imdbUrl, "en");
    }

    private Movie parse(final String imdbUrl, final String languageCode) {
        try {
            final Document document = Jsoup.connect(imdbUrl)
                                           .header("Accept-Language", languageCode)
                                           .get();
            final Elements type = document.getElementsByAttributeValue("type", "application/ld+json");
            final MovieInformation movieInformation = objectMapper.readValue(type.html(), MovieInformation.class);
            return Movie.builder()
                        .name(movieInformation.getName())
                        .type(movieInformation.getType())
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
        } catch (final JsonProcessingException e) {
            throw new RuntimeException("Could not read json info from: " + imdbUrl);
        } catch (final IOException e) {
            throw new RuntimeException("Could not connect to: " + imdbUrl);
        }
    }

    @Async
    public CompletableFuture<Movie> parseAsync(final String imdbUrl) {
        return CompletableFuture.completedFuture(parse(imdbUrl));
    }

}
