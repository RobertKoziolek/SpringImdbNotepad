package com.robcio.imdbNotepad.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class UrlRefiner {

    static public String refine(final String url) throws URISyntaxException {
        URI uri = new URI(url);
        return new URI(uri.getScheme(),
                uri.getAuthority(),
                uri.getPath(),
                null,
                uri.getFragment()).toString();
    }

    static public List<String> split(final String urls) {
        //TODO spend more than 10 seconds on this and do it right..
        if (urls.contains(",")) {
            return Arrays.asList(urls.split(","));
        }
        final String[] split = urls.split("\r\n");
        return Arrays.asList(split);
    }
}
