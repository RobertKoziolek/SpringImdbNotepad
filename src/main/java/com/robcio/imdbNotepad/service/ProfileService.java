package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Profile;
import com.robcio.imdbNotepad.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(final ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile getProfile(final Long id) {
        return profileRepository.findById(id).get();
    }

    public Profile add(final String name){
        final Profile profile = Profile.builder().name(name).build();
        profileRepository.save(profile);
        return profile;
    }

    public List<Profile> getAll(){
        return profileRepository.findAll();
    }

    public void remove(Long id) {
        profileRepository.deleteById(id);
    }
}
