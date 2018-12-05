package com.robcio.imdbNotepad.enumeration;

import com.robcio.imdbNotepad.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Comparator;

@AllArgsConstructor
@Getter
public enum MovieSorting {

    TYPE("By type (movie/series)", Comparator.comparing(Movie::getType)),
    NAME("By name", Comparator.comparing(Movie::getName)),
    DATE("By release date", Comparator.comparing(Movie::getDatePublished)),
    DURATION("By duration", Comparator.comparing(movie -> {
        final String duration = movie.getDuration();
        return StringUtils.isEmpty(duration) ? "0" : duration;
    })),
    RATING("By rating", Comparator.comparing(Movie::getRating));

    private final String label;
    private final Comparator<Movie> comparator;
}
