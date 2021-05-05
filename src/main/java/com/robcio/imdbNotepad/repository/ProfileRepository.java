package com.robcio.imdbNotepad.repository;

import com.robcio.imdbNotepad.entity.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {

    @Override
    List<Profile> findAll();
    Optional<Profile> findById(Long id);
}
