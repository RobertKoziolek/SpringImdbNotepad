package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);


    private final MovieRepository movieRepository;

    private final ImdbParserService imdbParserService;

    @Autowired
    public MovieService(final MovieRepository movieRepository, final ImdbParserService imdbParserService) {
        this.movieRepository = movieRepository;
        this.imdbParserService = imdbParserService;
    }

    public void add(final String imdbUrl) {
        final Movie movie = imdbParserService.parse(imdbUrl);
        movieRepository.save(movie);
    }

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    public Movie get(final Long id) {
        return movieRepository.findById(id)
                              .orElseThrow(() -> new RuntimeException("Could not find the movie with id " + id));
    }

    public void remove(final Long id) {
        movieRepository.deleteById(id);
    }

    public void edit(final Long id, final Movie editData) {
        final Movie movie = get(id);
        final String description = editData.getDescription();
        if (!StringUtils.isEmpty(description)) {
            movie.setDescription(description);
        }
        movieRepository.save(movie);
    }

    public void updateAll() {
        final List<Movie> all = getAll();
        final List<CompletableFuture<Movie>> futures = new LinkedList<>();
        logger.debug("Updating");
        all.forEach(movie -> {
            logger.debug("Downloading {}", movie.getName());
            final CompletableFuture<Movie> movieFuture = imdbParserService.parseAsync(movie.getUrl());
            futures.add(movieFuture);
        });
        logger.debug("Waiting for imdb");
        final CompletableFuture[] completableFutures = futures.toArray(new CompletableFuture[0]);
        CompletableFuture.allOf(completableFutures)
                         .join();
        try {
            for (final CompletableFuture<Movie> future : futures) {
                final Movie movie = future.get();
                movieRepository.save(movie);
            }
        } catch (final Exception e) {
            logger.error("Could not complete update");
        }
        all.forEach(movieRepository::delete);
        logger.debug("Finished");
    }

}
