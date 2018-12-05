package com.robcio.imdbNotepad.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieInformation {

    private String name;

    private String description;

    private String image;

    private Set<String> genre;

    private String duration;

    private String datePublished;

    private String rating;

    @JsonProperty("@type")
    private String type;

    @JsonProperty("aggregateRating")
    private void unpackRating(final Map<String,Object> aggregateRating) {
        rating = (String)aggregateRating.get("ratingValue");
    }
}
