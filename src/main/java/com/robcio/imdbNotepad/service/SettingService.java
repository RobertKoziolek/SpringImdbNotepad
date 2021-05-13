package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Setting;
import com.robcio.imdbNotepad.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;
    @Autowired
    private SessionService sessionService;


    private String getSettingValue(final String settingName) throws NoSuchElementException {
        final Long profileId = sessionService.getProfile().getId();
        return settingRepository.findByNameAndProfileId(settingName, profileId).get().getValue();
    }

    public <T extends Enum<T>> T getSetting(final Class<T> clazz) {
        try {
            final String value = getSettingValue(clazz.getSimpleName());
            return T.valueOf(clazz, value);
        } catch (final NoSuchElementException e){
            final T[] enumConstants = clazz.getEnumConstants();
            final T aDefault = enumConstants[0];
            setSetting(aDefault);
            return aDefault;
        }
    }

    public Set<String> getSettingSet(final String settingName) {
        try {
            final String value = getSettingValue(settingName);
            if (value.isEmpty()) throw new NoSuchElementException();
            return Arrays.stream(value.split("~")).collect(Collectors.toSet());
        } catch (final NoSuchElementException e){
            return returnEmptySettingSet(settingName);
        }
    }

    private Set<String> returnEmptySettingSet(final String settingName) {
        final Set<String> emptySet = Collections.emptySet();
        setSettingSet(settingName, emptySet);
        return emptySet;
    }

    public <T extends Enum> void setSetting(final T settingEnum) {
        saveSetting(settingEnum.getClass()
                               .getSimpleName(), settingEnum.name());
    }

    public <T> void setSettingSet(final String settingName, final Set<String> stringSet) {
        String setValue = "";
        if (stringSet!= null)
            setValue = stringSet.stream().map(String::valueOf).collect(Collectors.joining("~"));
        saveSetting(settingName, setValue);
    }

    private void saveSetting(final String settingName, final String settingValue) {
        final Long profileId = sessionService.getProfile().getId();
        final Setting setting = new Setting(settingName, profileId, settingValue);
        settingRepository.save(setting);
    }
}
