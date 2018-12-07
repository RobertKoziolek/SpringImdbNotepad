package com.robcio.imdbNotepad.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WatchedSorting {

    NO_MATTER("No action"),
    ONLY_WATCHED("Show only"),
    HIDE("Hide"),
    FIRST("Show first"),
    LAST("Show last");

    private final String label;

    public static WatchedSorting getDefault(){
        return NO_MATTER;
    }
}
