package com.robcio.imdbNotepad.service.update;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.service.ImdbParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
class UpdateComponent {

    private final ImdbParserService imdbParserService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    UpdateComponent(final ImdbParserService imdbParserService,
                    final ApplicationEventPublisher eventPublisher) {
        this.imdbParserService = imdbParserService;
        this.eventPublisher = eventPublisher;
    }

    @Async
    ListenableFuture update(final Movie movie) {
        final Movie updatedMovie = imdbParserService.parse(movie.getUrl());
        eventPublisher.publishEvent(updatedMovie);
        return AsyncResult.forValue(updatedMovie);
    }
}
