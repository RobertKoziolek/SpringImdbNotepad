package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Setting;
import com.robcio.imdbNotepad.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;
    @Autowired
    private SessionService sessionService;

    public <T extends Enum<T>> T getSetting(final Class<T> clazz) {
        final Long profileId = sessionService.getProfile().getId();
        final Optional<Setting> settingOptional = settingRepository.findByNameAndProfileId(clazz.getSimpleName(), profileId);
        if (settingOptional.isPresent()) {
            final String value = settingOptional.get()
                                                .getValue();
            return T.valueOf(clazz, value);
        } else {
            final T[] enumConstants = clazz.getEnumConstants();
            final T aDefault = enumConstants[0];
            setSetting(aDefault);
            return aDefault;
        }
    }

    public Set<String> getSettingSet(final String settingName) {
        final Long profileId = sessionService.getProfile().getId();
        final Optional<Setting> settingOptional = settingRepository.findByNameAndProfileId(settingName, profileId);
        if (settingOptional.isPresent()){
            final String value = settingOptional.get().getValue();
            if (value.isEmpty()) return returnEmptySettingSet(settingName);
            final Set<String> set = Arrays.stream(value.split("~")).collect(Collectors.toSet());
            return set;
        }
        return returnEmptySettingSet(settingName);
    }

    private Set<String> returnEmptySettingSet(final String settingName) {
        final Set<String> emptySet = Collections.emptySet();
        setSettingSet(settingName, emptySet);
        return emptySet;
    }

    public <T extends Enum> void setSetting(final T settingEnum) {
        final Long profileId = sessionService.getProfile().getId();
        final Setting setting = new Setting(settingEnum.getClass()
                                                       .getSimpleName(), profileId, settingEnum.name());
        settingRepository.save(setting);
    }

    public <T> void setSettingSet(final String settingName, final Set<String> stringSet) {
        final Long profileId = sessionService.getProfile().getId();
        String setValue = "";
        if (stringSet!= null)
            setValue = stringSet.stream().map(String::valueOf).collect(Collectors.joining("~"));
        final Setting setting = new Setting(settingName, profileId, setValue);
        settingRepository.save(setting);
    }
}
