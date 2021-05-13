package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.enumeration.WatchedCriteria;
import com.robcio.imdbNotepad.repository.MovieRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.SetUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
public class FilterService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SettingService settingService;

    @Autowired
    private SessionService sessionService;

    @Getter
    private Set<String> genres = Collections.emptySet();

    public void setGenres(final Set<String> genres) {
        this.genres = Objects.isNull(genres) ? Collections.emptySet() : genres;
    }

    Stream<Movie> filter(final Stream<Movie> stream) {
        Stream<Movie> tempStream = stream;
        if (!SetUtils.isEmpty(genres)) {
            tempStream = stream.filter(movie -> !Collections.disjoint(movie.getGenres(), genres));
        }
        final WatchedCriteria watchedCriteria = settingService.getSetting(WatchedCriteria.class);
        final Predicate<Movie> ownershipCriteriaPredicate = sessionService.getOwnershipCriteriaForProfile();
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
