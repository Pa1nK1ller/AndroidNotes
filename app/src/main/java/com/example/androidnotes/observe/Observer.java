package com.example.androidnotes.observe;

import com.example.androidnotes.data.NotesData;

public interface Observer {
    void updateCardData(NotesData notesData);
}
