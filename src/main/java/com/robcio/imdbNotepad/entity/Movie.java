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
    public static final String WATCHED = "WATCHED";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String url;

    private String imageUrl;

    private String description;

    @ElementCollection
    @CollectionTable(name="Genres", joinColumns=@JoinColumn(name="movie_id"))
    @Column(name="genre")
    private Set<String> genres;

    private String duration;

    private String dateCreated;

    private String rating;

    private String type;
}
