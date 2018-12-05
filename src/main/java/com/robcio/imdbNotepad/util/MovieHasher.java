package com.robcio.imdbNotepad.util;

public class MovieHasher {

    public String getHash(final String name, final String date){
        return name + date;
    }
}
