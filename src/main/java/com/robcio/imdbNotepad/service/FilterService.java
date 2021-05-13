package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.criteria.OwnershipCriteria;
import com.robcio.imdbNotepad.criteria.WatchedCriteria;
import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.SetUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
public class FilterService {

    private static final Logger logger = LoggerFactory.getLogger(FilterService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SettingService settingService;

    @Autowired
    private SessionService sessionService;

    Stream<Movie> filter(final Stream<Movie> stream) {
        Stream<Movie> tempStream = stream;

        final Set<String> genres = settingService.getSettingSet("genres");
        if (!SetUtils.isEmpty(genres)) {
            logger.debug("Filtering by {} genres: {}", genres.size(), genres.toString());
            tempStream = stream.filter(movie -> !Collections.disjoint(movie.getGenres(), genres));
        }

        final WatchedCriteria watchedCriteria = settingService.getSetting(WatchedCriteria.class);
        final OwnershipCriteria ownershipCriteria = settingService.getSetting(OwnershipCriteria.class);
        final Predicate<Movie> ownershipCriteriaPredicate = ownershipCriteria.getPredicate(sessionService.getProfile().getId());

        return tempStream.filter(watchedCriteria.getPredicate()).filter(ownershipCriteriaPredicate);
    }

    //TODO might want to make sql call for distinct on genres table (it's set up only as @CollectionTable in Movie entity)
    public Set<String> getDistinctGenres() {
        final List<Movie> all = movieRepository.findAll();
        return all.stream()
                  .map(Movie::getGenres)
                  .reduce(new TreeSet<String>() {
                  }, (genres1, genres2) -> {
                      genres1.addAll(genres2);
                      return genres1;
                  });
    }
}
