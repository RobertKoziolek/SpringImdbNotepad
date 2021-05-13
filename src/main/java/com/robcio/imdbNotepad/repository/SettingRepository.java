package com.robcio.imdbNotepad.repository;

import com.robcio.imdbNotepad.entity.Setting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends CrudRepository<Setting, String> {

    Optional<Setting> findByNameAndProfileId(String name, Long profileId);
}
