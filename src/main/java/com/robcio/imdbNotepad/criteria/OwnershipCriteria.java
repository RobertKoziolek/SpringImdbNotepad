package com.robcio.imdbNotepad.criteria;

import com.robcio.imdbNotepad.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.function.LongFunction;
import java.util.function.Predicate;

@AllArgsConstructor
public enum OwnershipCriteria {

    MINE("ownership.mine", id -> (movie -> Objects.equals(movie.getProfileId(), id))),
    ELSE("ownership.else", id -> (movie -> !Objects.equals(movie.getProfileId(), id))),
    ALL("ownership.all", id -> (movie -> true));

    @Getter
    private final String label;
    private final LongFunction<Predicate<Movie>> predicateRecipe;

    public Predicate<Movie> getPredicate(final Long profileId) {
        return predicateRecipe.apply(profileId);
    }


    public static OwnershipCriteria getDefault() {
        return MINE;
    }

}
