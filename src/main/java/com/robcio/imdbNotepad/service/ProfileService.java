package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Profile;
import com.robcio.imdbNotepad.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Map<Long, String> getAllAsMap(){
        final List<Profile> all = profileRepository.findAll();
        return all.stream().collect(Collectors.toMap(Profile::getId, Profile::getName));
    }

    public Profile findById(final Long id) {
        //TODO need to check if exists
        return profileRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("No profile found"));
    }

    public void remove(Long id) {
        profileRepository.deleteById(id);
    }
}
