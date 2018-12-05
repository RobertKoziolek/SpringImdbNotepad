package com.robcio.imdbNotepad.service.update;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class UpdateService {

    private static final Logger logger = LoggerFactory.getLogger(UpdateService.class);

    private final MovieRepository movieRepository;
    private final UpdateComponent updateComponent;

    private final List<ListenableFuture> futureList;

    @Autowired
    public UpdateService(final MovieRepository movieRepository, final UpdateComponent updateComponent) {
        this.movieRepository = movieRepository;
        this.updateComponent = updateComponent;

        futureList = new LinkedList<>();
    }

    public void validateForUpdate(){
        futureList.removeIf(Future::isDone);
        if (!futureList.isEmpty()) {
            throw new IllegalStateException("Previous update not done");
        }
    }

    @Async
    public void updateAll() {
        final List<Movie> movies = movieRepository.findAll();
        futureList.addAll(movies.stream()
                                .map(updateComponent::update)
                                .collect(Collectors.toList()));
        logger.debug("Updating {} movies", movies.size());
    }

}
