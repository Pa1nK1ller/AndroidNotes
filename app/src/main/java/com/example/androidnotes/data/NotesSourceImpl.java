package com.example.androidnotes.data;

import android.content.res.Resources;

import com.example.androidnotes.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class NotesSourceImpl implements NotesSource {
    private final List<NotesData> dataSource;
    private final Resources resources;

    public NotesSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(7);
        this.resources = resources;
    }

    public NotesSourceImpl init() {
        String[] titles = resources.getStringArray(R.array.notes);
        String[] descriptions = resources.getStringArray(R.array.descriptionNotes);
        for (int i = 0; i < descriptions.length; i++) {
            dataSource.add(new NotesData(titles[i], descriptions[i], false, Calendar.getInstance().getTime()));
        }
        return this;
    }

    public NotesData getCardData(int position) {
        return dataSource.get(position);
    }

    public int size() {
        return dataSource.size();
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, NotesData notesData) {
        dataSource.set(position, notesData);
    }

    @Override
    public void addCardData(NotesData notesData) {
        dataSource.add(notesData);
    }

    @Override
    public void clearCardData() {
        dataSource.clear();
    }
}