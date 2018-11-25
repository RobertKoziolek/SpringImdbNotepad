package com.robcio.imdbNotepad.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieInformation {

    private String name;

    private String description;

    private String image;

    private List<String> genre;

    private String duration;

    private String dateCreated;

    private String rating;

    @JsonProperty("@type")
    private String type;

    @JsonProperty("review")
    private void unpackDateCreated(final Map<String,Object> review) {
        dateCreated = (String)review.get("dateCreated");
    }

    @JsonProperty("aggregateRating")
    private void unpackRating(final Map<String,Object> aggregateRating) {
        rating = (String)aggregateRating.get("ratingValue");
    }
}
