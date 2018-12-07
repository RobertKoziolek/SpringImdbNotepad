package com.robcio.imdbNotepad.service;

import com.robcio.imdbNotepad.entity.Setting;
import com.robcio.imdbNotepad.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingService {

    private final SettingRepository settingRepository;

    @Autowired
    public SettingService(final SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public <T extends Enum<T>> T getSetting(final Class<T> clazz) {
        final Optional<Setting> settingOptional = settingRepository.findByName(clazz.getSimpleName());
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

    public <T extends Enum> void setSetting(final T settingEnum) {
        final Setting setting = new Setting(settingEnum.getClass()
                                                       .getSimpleName(), settingEnum.name());
        settingRepository.save(setting);
    }
}
