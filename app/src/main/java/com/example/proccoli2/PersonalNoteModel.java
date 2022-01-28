package com.example.proccoli2;

import android.app.Person;

import java.io.Serializable;

public class PersonalNoteModel implements Serializable {
    private String note;
    private int createdAt;
    private String noteID;

    public PersonalNoteModel(String note, int createdAt, String noteID){
        this.note = note;
        this.createdAt = createdAt;
        this.noteID = noteID;
    }
    public PersonalNoteModel(){}

    public String getNote(){
        return note;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "PersonalNoteModel{" +
                "note='" + note + '\'' +
                '}';
    }
}
