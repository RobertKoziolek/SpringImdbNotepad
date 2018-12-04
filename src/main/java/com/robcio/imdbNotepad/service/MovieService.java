package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.enumeration.MovieSorting;
import com.robcio.imdbNotepad.repository.MovieRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.SetUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MovieService {

    private final MovieRepository movieRepository;
    private final ImdbParserService imdbParserService;

    @Setter
    private MovieSorting movieSorting;

    @Autowired
    public MovieService(final MovieRepository movieRepository, final ImdbParserService imdbParserService) {
        this.movieRepository = movieRepository;
        this.imdbParserService = imdbParserService;

        movieSorting = MovieSorting.NAME;
    }

    public void add(final String imdbUrl) {
        final Movie movie = imdbParserService.parse(imdbUrl);
        movieRepository.save(movie);
    }

    public List<Movie> getAll(final Set<String> genres) {
        Stream<Movie> stream = getAll().stream();
        if (!SetUtils.isEmpty(genres)) {
            stream = stream.filter(movie -> !Collections.disjoint(movie.getGenres(), genres));
        }
        stream = stream.sorted(movieSorting.getComparator());
        return stream.collect(Collectors.toList());
    }

    public Movie get(final Long id) {
        return movieRepository.findById(id)
                              .orElseThrow(() -> new RuntimeException("Could not find the movie with id " + id));
    }

    private List<Movie> getAll() {
        return movieRepository.findAll();
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
        //TODO watched should be a boolean value not overriding type
        final Movie movie = get(id);
        movie.setType(Movie.WATCHED);
        movieRepository.save(movie);
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
}
