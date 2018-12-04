package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MovieService {

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

    public List<Movie> getAllByType() {
        final List<Movie> all = getAll();
        //TODO sorting strategy (held in cookies?)
        all.sort(Comparator.comparing(Movie::getType));
        return all;
    }

    public List<Movie> getByGenres(final Set<String> genres) {
        final List<Movie> all = getAll();
        return all.stream()
                  .filter(movie -> !Collections.disjoint(movie.getGenres(), genres))
                  .collect(Collectors.toList());
    }


    public Set<String> getDistinctGenres() {
        final List<Movie> all = getAll();
        return all.stream()
                  .map(Movie::getGenres)
                  .reduce(new TreeSet<String>() {
                  }, (genres1, genres2) -> {
                      genres1.addAll(genres2);
                      return genres1;
                  });
    }

    private List<Movie> getAll() {
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

    public void markAsWatched(final Long id) {
        final Movie movie = get(id);
        movie.setType(Movie.WATCHED);
        movieRepository.save(movie);
    }
}
