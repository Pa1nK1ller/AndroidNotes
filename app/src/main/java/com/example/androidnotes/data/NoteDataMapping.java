package com.example.androidnotes.data;


import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {
    public static class Fields {
        public final static String DATE = "date";
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
        public final static String LIKE = "like";

    }

    public static NotesData toNoteData(String id, Map<String, Object> doc) {
        Timestamp timeStamp = (Timestamp) doc.get(Fields.DATE);
        NotesData answer = new NotesData((String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DESCRIPTION),
                (boolean) doc.get(Fields.LIKE),
                timeStamp.toDate());
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(NotesData notesData) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, notesData.getTitle());
        answer.put(Fields.DESCRIPTION, notesData.getDescription());
        answer.put(Fields.LIKE, notesData.isLike());
        answer.put(Fields.DATE, notesData.getDate());
        return answer;
    }
}
