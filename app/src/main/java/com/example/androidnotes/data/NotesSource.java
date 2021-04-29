package com.example.androidnotes.data;

public interface NotesSource {
    NotesSource init(NotesSourceResponse notesSourceResponse);

    NotesData getCardData(int position);

    int size();

    void deleteCardData(int position);

    void updateCardData(int position, NotesData notesData);

    void addCardData(NotesData notesData);

    void clearCardData();
}