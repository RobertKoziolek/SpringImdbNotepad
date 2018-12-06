package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.enumeration.MovieSorting;
import com.robcio.imdbNotepad.enumeration.WatchedSorting;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Stream;

@Setter
@Service
public class SortService {

    //TODO should remember those across app restarts
    private MovieSorting movieSorting = MovieSorting.NAME;
    private WatchedSorting watchedSorting = WatchedSorting.NO_MATTER;

    Stream<Movie> sort(final Stream<Movie> stream) {
        switch (watchedSorting) {
            case NO_MATTER:
                return stream;
            case FIRST:
                return stream.sorted(Comparator.comparing(Movie::getWatched)
                                               .reversed()
                                               .thenComparing(movieSorting.getComparator()));
            case LAST:
                return stream.sorted(Comparator.comparing(Movie::getWatched)
                                               .thenComparing(movieSorting.getComparator()));
            case ONLY_WATCHED:
                return stream.filter(Movie::getWatched);
            case HIDE:
                return stream.filter(movie -> !movie.getWatched());
        }
        throw new IllegalArgumentException("Watched sorting type not implemented.");
    }
}
