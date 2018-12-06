package com.robcio.imdbNotepad.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WatchedSorting {

    ONLY_WATCHED("Show only"),
    HIDE("Hide"),
    FIRST("Show first"),
    LAST("Show last"),
    NO_MATTER("No action");

    private final String label;
}
