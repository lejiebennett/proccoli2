package com.lejiebennett.proccoli2.NewModels;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class PersonalNoteModel {
    SingletonStrings ss = new SingletonStrings();
    private String note;
    private long createdAt;
    private String noteId;

    public PersonalNoteModel(String noteId, String note, long createdAt){
        this.note = note;
        this.createdAt = createdAt;
        this.noteId=noteId;
    }

    public static ArrayList<PersonalNoteModel> parseData(DocumentSnapshot documentSnap){
        SingletonStrings ss = new SingletonStrings();

        if(documentSnap!=null){
            DocumentSnapshot doc = documentSnap;
            HashMap<String,Object> data = (HashMap<String, Object>) doc.getData();
            ArrayList<PersonalNoteModel> finalNotes = new ArrayList<>();
            for(String key: data.keySet()){
                if(key.equals(ss.CREATED_AT) == false){
                    if(data.get(key) != null){
                        HashMap<String,Object> values = (HashMap<String, Object>) data.get(key);
                        finalNotes.add(new PersonalNoteModel(key,(String)getValueOrDefault(values.get(ss.NOTE_REF),"err"),(long) getValueOrDefault(values.get(ss.CREATED_AT),0)));
                    }
                    else
                        return null;
                }
            }
            return finalNotes;
        }
        else
            return null;


    }

    public static HashMap<String, Object> jsonConverter(PersonalNoteModel data){
        SingletonStrings ss = new SingletonStrings();

        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String,Object> hashMap1 = new HashMap<>();
        hashMap1.put(ss.NOTE_REF,data.note);
        hashMap1.put(ss.CREATED_AT,data.createdAt);
        hashMap.put(data.noteId,hashMap1);
        return hashMap;
    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public String getNote() {
        return note;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "PersonalNoteModel{" +
                "ss=" + ss +
                ", note='" + note + '\'' +
                ", createdAt=" + createdAt +
                ", noteId='" + noteId + '\'' +
                '}';
    }
}
