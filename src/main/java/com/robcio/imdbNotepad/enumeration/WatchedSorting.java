package com.robcio.imdbNotepad.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WatchedSorting {

    NO_MATTER("watched.none"),
    ONLY_WATCHED("watched.show"),
    HIDE("watched.hide"),
    FIRST("watched.first"),
    LAST("watched.last");

    private final String label;

    public static WatchedSorting getDefault(){
        return NO_MATTER;
    }
}
