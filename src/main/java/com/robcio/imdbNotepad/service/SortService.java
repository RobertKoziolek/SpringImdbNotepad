package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.enumeration.MovieSorting;
import com.robcio.imdbNotepad.enumeration.WatchedSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Stream;

@Service
public class SortService {

    private final SettingService settingService;

    @Autowired
    public SortService(final SettingService settingService) {
        this.settingService = settingService;
    }

    Stream<Movie> sort(final Stream<Movie> stream) {
        final WatchedSorting watchedSorting = settingService.getSetting(WatchedSorting.class);
        final MovieSorting movieSorting = settingService.getSetting(MovieSorting.class);

        switch (watchedSorting) {
            case NO_MATTER:
                return stream.sorted(movieSorting.getComparator());
            case FIRST:
                return stream.sorted(Comparator.comparing(Movie::getWatched)
                                               .reversed()
                                               .thenComparing(movieSorting.getComparator()));
            case LAST:
                return stream.sorted(Comparator.comparing(Movie::getWatched)
                                               .thenComparing(movieSorting.getComparator()));
            case ONLY_WATCHED:
                return stream.filter(Movie::getWatched)
                             .sorted(movieSorting.getComparator());
            case HIDE:
                return stream.filter(movie -> !movie.getWatched())
                             .sorted(movieSorting.getComparator());
        }
        throw new IllegalArgumentException("Watched sorting type not implemented.");
    }
}
