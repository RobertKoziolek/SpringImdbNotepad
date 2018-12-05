package com.robcio.imdbNotepad.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Getter
@Service
public class FilterService {

    private Set<String> genres = Collections.emptySet();

    public void setGenres(final Set<String> genres) {
        this.genres = Objects.isNull(genres) ? Collections.emptySet() : genres;
    }
}
