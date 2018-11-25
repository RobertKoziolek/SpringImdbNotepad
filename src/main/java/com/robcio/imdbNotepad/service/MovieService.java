package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

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

    public void add(final String imdbUrl, String description) {
        final Movie movie = imdbParserService.parse(imdbUrl);
        movie.setDescription(description);
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

    public void adjustLanguage(final String languageCode) {
        //TODO movie title language adjustment
//        final List<Movie> all = movieRepository.findAll();
//        all.forEach(m -> imdbParserService.adjustLanguage(m, languageCode));
//        imdbParserService.adjustLanguage(all.get(0), languageCode);
        logger.debug("Language code is {}", languageCode);
    }

    public void edit(final Long id, final Movie editData) {
        final Movie movie = get(id);
        final String description = editData.getDescription();
        if (!StringUtils.isEmpty(description)) {
            movie.setDescription(description);
        }
        movieRepository.save(movie);
    }
}
