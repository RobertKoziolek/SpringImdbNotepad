package com.robcio.imdbNotepad.enumeration;

import com.robcio.imdbNotepad.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;
import java.util.function.Predicate;

@AllArgsConstructor
@Getter
public enum WatchedCriteria {

    NO_MATTER("watched.none", Comparator.comparing(movie->0), movie->true),
    ONLY_WATCHED("watched.show",Comparator.comparing(movie->0), Movie::getWatched),
    HIDE("watched.hide",Comparator.comparing(movie->0), movie -> !movie.getWatched()),
    FIRST("watched.first",Comparator.comparing(Movie::getWatched).reversed(), movie->true),
    LAST("watched.last",Comparator.comparing(Movie::getWatched), movie->true);

    private final String label;
    private final Comparator<Movie> comparator;
    private final Predicate<Movie> predicate;

    public static WatchedCriteria getDefault(){
        return NO_MATTER;
    }
}
