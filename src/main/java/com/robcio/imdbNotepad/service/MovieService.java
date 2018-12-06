package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.enumeration.MovieSorting;
import com.robcio.imdbNotepad.repository.MovieRepository;
import com.robcio.imdbNotepad.service.util.UrlRefiner;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.SetUtils;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MovieService {

    private final MovieRepository movieRepository;
    private final FilterService filterService;
    private final ImdbParserService imdbParserService;

    @Setter
    private MovieSorting movieSorting;

    @Autowired
    public MovieService(final MovieRepository movieRepository,
                        final FilterService filterService,
                        final ImdbParserService imdbParserService) {
        this.movieRepository = movieRepository;
        this.filterService = filterService;
        this.imdbParserService = imdbParserService;

        movieSorting = MovieSorting.NAME;
    }

    public void add(final String imdbUrl) {
        final Movie movie = imdbParserService.parse(imdbUrl);
        movieRepository.save(movie);
    }

    public List<Movie> getAll() {
        Stream<Movie> stream = findAll().stream();
        final Set<String> genres = filterService.getGenres();
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

    private List<Movie> findAll() {
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
        final Movie movie = get(id);
        movie.setWatched(true);
        movieRepository.save(movie);
    }

    public Set<String> getDistinctGenres() {
        final List<Movie> all = findAll();
        return all.stream()
                  .map(Movie::getGenres)
                  .reduce(new TreeSet<String>() {
                  }, (genres1, genres2) -> {
                      genres1.addAll(genres2);
                      return genres1;
                  });
    }

    public boolean exists(final String imdbUrl) throws URISyntaxException {
        return movieRepository.findByUrl(UrlRefiner.refine(imdbUrl))
                              .isPresent();
    }
}
