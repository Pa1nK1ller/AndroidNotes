package com.example.androidnotes.data;

import android.content.res.Resources;

import com.example.androidnotes.R;

import java.util.ArrayList;
import java.util.List;


public class CardsSourceImpl implements CardsSource {
    private final List<CardData> dataSource;
    private final Resources resources;

    public CardsSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(7);
        this.resources = resources;
    }

    public CardsSourceImpl init() {
        String[] titles = resources.getStringArray(R.array.notes);
        String[] descriptions = resources.getStringArray(R.array.descriptionNotes);
        String[] dateNotes = resources.getStringArray(R.array.notes_date);
        for (int i = 0; i < descriptions.length; i++) {
            dataSource.add(new CardData(titles[i], descriptions[i], dateNotes[i], false));
        }
        return this;
    }

    public CardData getCardData(int position) {
        return dataSource.get(position);
    }

    public int size() {
        return dataSource.size();
    }
}