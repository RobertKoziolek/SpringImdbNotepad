package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class UpdateService {

    private static final Logger logger = LoggerFactory.getLogger(UpdateService.class);

    private final MovieRepository movieRepository;
    private final ImdbParserService imdbParserService;

    @Autowired
    public UpdateService(final MovieRepository movieRepository, final ImdbParserService imdbParserService) {
        this.movieRepository = movieRepository;
        this.imdbParserService = imdbParserService;
    }

    public void updateAll() {
        logger.debug("Updating");
        final List<Movie> all = movieRepository.findAll();
        final List<CompletableFuture<Movie>> futures = getCompletableFutures(all);
        substituteNewInfo(all, futures);
        logger.debug("Finished");
    }

    private void substituteNewInfo(List<Movie> oldMovies, List<CompletableFuture<Movie>> futures) {
        final Set<String> watched = oldMovies.stream()
                                       .filter(m -> Objects.equals(m.getType(), Movie.WATCHED))
                                       .map(Movie::getName)
                                       .collect(Collectors.toSet());

        final List<Movie> updatedMovies = futures.stream()
                                          .map(this::map)
                                          .collect(Collectors.toList());
        if (updatedMovies.size() != oldMovies.size()){
            throw new IllegalStateException("Update failed, received diffirent amount of movies than before");
        }
        movieRepository.saveAll(updatedMovies);
        updatedMovies.forEach(movie -> {
                logger.debug("Saving " + movie.getName());
                if (watched.contains(movie.getName())) {
                    movie.setType(Movie.WATCHED);
                }
                movieRepository.save(movie);
        });
        movieRepository.deleteAll(oldMovies);
        //TODO use events to allow users to do stuff while update takes place
    }

    private Movie map(final CompletableFuture<Movie> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Could not map a future to a movie");
        }
    }

    private List<CompletableFuture<Movie>> getCompletableFutures(final List<Movie> all) {
        final List<CompletableFuture<Movie>> futures = new LinkedList<>();
        all.forEach(movie -> {
            logger.debug("Downloading {}", movie.getName());
            final CompletableFuture<Movie> movieFuture = imdbParserService.parseAsync(movie.getUrl());
            futures.add(movieFuture);
        });
        logger.debug("Waiting for imdb");
        final CompletableFuture[] completableFutures = futures.toArray(new CompletableFuture[0]);
        CompletableFuture.allOf(completableFutures)
                         .join();
        return futures;
    }
}
