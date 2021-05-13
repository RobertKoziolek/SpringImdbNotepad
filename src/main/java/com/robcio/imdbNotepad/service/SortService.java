package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.criteria.SortingCriteria;
import com.robcio.imdbNotepad.criteria.WatchedCriteria;
import com.robcio.imdbNotepad.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class SortService {

    private final SettingService settingService;

    @Autowired
    public SortService(final SettingService settingService) {
        this.settingService = settingService;
    }

    Stream<Movie> sort(final Stream<Movie> stream) {
        final WatchedCriteria watchedCriteria = settingService.getSetting(WatchedCriteria.class);
        final SortingCriteria sortingCriteria = settingService.getSetting(SortingCriteria.class);
        return stream.sorted(watchedCriteria.getComparator().thenComparing(sortingCriteria.getComparator()));
    }
}
