package com.robcio.imdbNotepad.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@IdClass(SettingId.class)
public class Setting {

    @Id
    private String name;

    @Id
    private Long profileId;
    //TODO will need some automic service to cleanup stuff (should delete settings for removed profile)

    private String value;

}
