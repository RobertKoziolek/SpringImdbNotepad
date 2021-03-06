package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.repository.MovieRepository;
import com.robcio.imdbNotepad.util.UrlRefiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;
    private final FilterService filterService;
    private final SortService sortService;
    private final ImdbParserService imdbParserService;

    @Autowired
    public MovieService(final MovieRepository movieRepository,
                        final FilterService filterService,
                        final SortService sortService,
                        final ImdbParserService imdbParserService) {
        this.movieRepository = movieRepository;
        this.filterService = filterService;
        this.sortService = sortService;
        this.imdbParserService = imdbParserService;
    }

    public void add(final String imdbUrl, final Long profileId) {
        final Movie movie = imdbParserService.parse(imdbUrl);
        movie.setProfileId(profileId);
        logger.debug("Adding {}", movie.getName());
        movieRepository.save(movie);
    }

    @Async
    public void addAsync(final String imdbUrl, final Long profileId) {
        add(imdbUrl, profileId);
    }

    public List<Movie> getAll() {
        Stream<Movie> stream = findAll().stream();
        stream = filterService.filter(stream);
        stream = sortService.sort(stream);
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
        final Long profileId = editData.getProfileId();
        if (!StringUtils.isEmpty(description)) {
            movie.setDescription(description);
        }
        if (!Objects.isNull(profileId)) {
            movie.setProfileId(profileId);
        }
        movieRepository.save(movie);
    }

    public boolean exists(final String imdbUrl) throws URISyntaxException {
        return movieRepository.findByUrl(UrlRefiner.refine(imdbUrl))
                              .isPresent();
    }

    public void setWatched(final Long id, final Boolean watched) {
        final Movie movie = get(id);
        movie.setWatched(watched);
        movieRepository.save(movie);
    }
}
