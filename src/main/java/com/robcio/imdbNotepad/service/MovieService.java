package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieService {

    private final MovieRepository movieRepository;

    private final MovieCreationService movieCreationService;

    @Autowired
    public MovieService(final MovieRepository movieRepository, final MovieCreationService movieCreationService) {
        this.movieRepository = movieRepository;
        this.movieCreationService = movieCreationService;
    }

    public void add(final String imdbUrl) {
        final Movie movie = movieCreationService.create(imdbUrl);
        movieRepository.save(movie);
    }

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }
}
