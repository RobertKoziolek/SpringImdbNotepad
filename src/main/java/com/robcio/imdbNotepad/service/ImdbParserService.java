package com.robcio.imdbNotepad.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.response.MovieInformation;
import com.robcio.imdbNotepad.util.UrlRefiner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

@Component
public class ImdbParserService {

    private final ObjectMapper objectMapper;

    public ImdbParserService() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    public Movie parse(final String imdbUrl) {
        try {
            final Document document = Jsoup.connect(imdbUrl)
                                           .get();
            final Elements type = document.getElementsByAttributeValue("type", "application/ld+json");
            final MovieInformation movieInformation = objectMapper.readValue(type.html(), MovieInformation.class);
            final String name = movieInformation.getName();
            final String datePublished = movieInformation.getDatePublished();
            return Movie.builder()
                        .url(UrlRefiner.refine(imdbUrl))
                        .name(name)
                        .type(movieInformation.getType())
                        .description(movieInformation.getDescription())
                        .duration(movieInformation.getDuration())
                        .datePublished(datePublished)
                        .rating(movieInformation.getRating())
                        .imageUrl(movieInformation.getImage())
                        .genres(movieInformation.getGenre())
                        .watched(false)
                        .build();
        } catch (final JsonProcessingException e) {
            throw new RuntimeException("Could not read json info from: " + imdbUrl);
        } catch (final IOException e) {
            throw new RuntimeException("Could not connect to: " + imdbUrl);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Could not refine url");
        }
    }

    @Async
    public CompletableFuture<Movie> parseAsync(final String imdbUrl) {
        return CompletableFuture.completedFuture(parse(imdbUrl));
    }
}
