package com.example.androidnotes.data;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotesSourceFirebaseImpl implements NotesSource {
    public static final String NOTES_COLLECTION = "notes";
    public static final String TAG = "[NotesSourceFirebaseImpl]";

    private final FirebaseFirestore store = FirebaseFirestore.getInstance();

    private final CollectionReference collection = store.collection(NOTES_COLLECTION);

    private List<NotesData> notesDataList = new ArrayList<>();

    @Override
    public NotesSource init(NotesSourceResponse notesSourceResponse) {
        collection.orderBy(NoteDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        notesDataList = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            Map<String, Object> doc = documentSnapshot.getData();
                            String id = documentSnapshot.getId();
                            NotesData noteData = NoteDataMapping.toNoteData(id, doc);
                            notesDataList.add(noteData);
                        }
                        Log.d(TAG, "success " + notesDataList.size() + " qnt");
                        notesSourceResponse.initialized(NotesSourceFirebaseImpl.this);
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }).addOnFailureListener(e -> {
            Log.d(TAG, "get failed with ", e);
        });
        return this;
    }

    @Override
    public int size() {
        if (notesDataList == null) {
            return 0;
        }
        return notesDataList.size();
    }

    @Override
    public void deleteCardData(int position) {
        collection.document(notesDataList.get(position).getId()).delete();
        notesDataList.remove(position);
    }

    @Override
    public void updateCardData(int position, NotesData notesData) {
        String id = notesData.getId();
        collection.document(id).set(NoteDataMapping.toDocument(notesData));
    }

    @Override
    public void addCardData(NotesData notesData) {
        collection.add(NoteDataMapping.toDocument(notesData))
                .addOnSuccessListener(documentReference -> notesData.setId(documentReference.getId()));
    }

    @Override
    public void clearCardData() {
        for (NotesData noteDate : notesDataList) {
            collection.document(noteDate.getId()).delete();
        }
        notesDataList = new ArrayList<>();
    }

    @Override
    public NotesData getCardData(int position) {
        return notesDataList.get(position);
    }
}
