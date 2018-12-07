package com.robcio.imdbNotepad.util;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlRefiner {


    static public String refine(final String url) throws URISyntaxException {
        URI uri = new URI(url);
        return new URI(uri.getScheme(),
                       uri.getAuthority(),
                       uri.getPath(),
                       null,
                       uri.getFragment()).toString();
    }
}
