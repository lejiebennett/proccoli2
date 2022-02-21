package com.example.proccoli2.NewModels;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PersonalNoteModel {
    private String note;
    private Date createdAt;
    private String noteId;

    public PersonalNoteModel(String noteId, String note, Date createdAt){
        this.note = note;
        this.createdAt = createdAt;
        this.noteId=noteId;
    }

    public ArrayList<PersonalNoteModel> parseData(DocumentSnapshot documentSnap){
        if(documentSnap!=null){
            DocumentSnap doc = documentSnap;
            HashMap<String,Object> data = doc.data();
        }
        else
            return null;
        ArrayList<PersonalNoteModel> finalNotes = new ArrayList<>();
        for(String key: data.keyset()){
            if(key.equals(CREATED_AT) == false){
                if(data.get(key) != null){
                    HashMap<String,Object> values = data.get(key);
                    finalNotes.add(new PersonalNoteModel((String)getValueOrDefault(values.get(NOTE_REF),"err"),(Date) getValueOrDefault(values.get(CREATED_AT),0)));
                }
                else
                    return null;
            }
        }
        return finalNotes;
    }

    public HashMap<String, Object> jsonConverter(PersonalNoteModel data){
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String,Object> hashMap1 = new HashMap<>();
        hashMap1.put(NOTE_REF,data.note);
        hashMap1.put(CREATED_AT,data.createdAt);
        hashMap.put(data.noteId,hashMap1);
        return hashMap;
    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

}
