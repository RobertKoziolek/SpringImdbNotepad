package com.robcio.imdbNotepad.enumeration;

import com.robcio.imdbNotepad.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Comparator;

@AllArgsConstructor
@Getter
public enum SortingCriteria {

    NAME("sort.name", Comparator.comparing(Movie::getName)),
    TYPE("sort.type", Comparator.comparing(Movie::getType)),
    DATE("sort.date", Comparator.comparing(Movie::getDatePublished)),
    DURATION("sort.duration", Comparator.comparing(movie -> {
        final String duration = movie.getDuration();
        return StringUtils.isEmpty(duration) ? "0" : duration;
    })),
    RATING("sort.rating", Comparator.comparing(movie -> {
        final String rating = movie.getRating();
        return StringUtils.isEmpty(rating) ? "0" : rating;
    }));

    private final String label;
    private final Comparator<Movie> comparator;

    public static SortingCriteria getDefault() {
        return NAME;
    }
}
