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

    @SuppressWarnings("unchecked")
    @JsonProperty("review")
    private void unpackDateCreated(final Map<String,Object> review) {
        this.dateCreated = (String)review.get("dateCreated");
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("aggregateRating")
    private void unpackRating(final Map<String,Object> aggregateRating) {
        this.rating = (String)aggregateRating.get("ratingValue");
    }
}
