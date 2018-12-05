package com.robcio.imdbNotepad.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = 200)
    private String hash;

    private String name;

    private String url;

    private String imageUrl;

    private String description;

    @ElementCollection
    @CollectionTable(name = "Genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "genre")
    private Set<String> genres;

    private String duration;

    private String datePublished;

    private String rating;

    private String type;

    private Boolean watched;
}
