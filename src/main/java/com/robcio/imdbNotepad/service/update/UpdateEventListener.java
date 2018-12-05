package com.robcio.imdbNotepad.service.update;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateEventListener {

    private static final Logger logger = LoggerFactory.getLogger(UpdateEventListener.class);

    private final MovieRepository movieRepository;

    @Autowired
    public UpdateEventListener(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @EventListener
    protected void handleUpdatedMovie(final Movie movie) {
        final Optional<Movie> oldMovieOptional = movieRepository.findByHash(movie.getHash());
        if (oldMovieOptional.isPresent()) {
            final Movie oldMovie = oldMovieOptional.get();
            movie.setWatched(oldMovie.getWatched());
            movieRepository.delete(oldMovie);
            movieRepository.save(movie);
            logger.debug("Finished updating {}", movie.getName());
        } else {
            logger.error("Update failed for {}, could not find previous record", movie.getName());
        }
    }
}
