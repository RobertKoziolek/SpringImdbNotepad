package com.robcio.imdbNotepad.enumeration;

import com.robcio.imdbNotepad.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Comparator;

@AllArgsConstructor
@Getter
public enum MovieSorting {

    NAME("By name", Comparator.comparing(Movie::getName)),
    TYPE("By type (movie/series)", Comparator.comparing(Movie::getType)),
    DATE("By release date", Comparator.comparing(Movie::getDatePublished)),
    DURATION("By duration", Comparator.comparing(movie -> {
        final String duration = movie.getDuration();
        return StringUtils.isEmpty(duration) ? "0" : duration;
    })),
    RATING("By rating", Comparator.comparing(Movie::getRating).reversed());

    private final String label;
    private final Comparator<Movie> comparator;

    public static MovieSorting getDefault(){
        return NAME;
    }
}
