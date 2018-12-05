package com.robcio.imdbNotepad.repository;

import com.robcio.imdbNotepad.entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    @Override
    List<Movie> findAll();

    Optional<Movie> findByHash(String hash);
}
