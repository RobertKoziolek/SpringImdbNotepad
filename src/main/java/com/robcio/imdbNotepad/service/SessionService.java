package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Movie;
import com.robcio.imdbNotepad.entity.Profile;
import com.robcio.imdbNotepad.enumeration.OwnershipCriteria;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.function.Predicate;

@Service
@SessionScope
public class SessionService {
    private static final String DEFAULT_VIEW = "tiles";
    //TODO do it somewhere else
    private static final Profile EMPTY_PROFILE = new Profile();

//    static {
//        EMPTY_PROFILE = new Profile();
//        EMPTY_PROFILE.setId(0L);
//        EMPTY_PROFILE.setName("?");
//    }

    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    private ProfileService profileService;

    @Getter
    private Profile profile;
    @Getter
    private String lastView = DEFAULT_VIEW;

    @Getter
    @Setter
    private OwnershipCriteria ownershipCriteria = OwnershipCriteria.getDefault();

    public void setProfile(final Long id){
        try {
            profile = profileService.findById(id);
        } catch (final IllegalArgumentException e){
            profile = EMPTY_PROFILE;
        }
    }

    public void setLastView(final String view) {
        String tempView = view.replace("_view", "");
        lastView = tempView;
    }

    //TODO something else here
    public boolean noProfileSelected() {return profile == EMPTY_PROFILE || profile == null;}

    public Predicate<Movie> getOwnershipCriteriaForProfile() {
        return ownershipCriteria.getPredicate(profile.getId());
    }
}
