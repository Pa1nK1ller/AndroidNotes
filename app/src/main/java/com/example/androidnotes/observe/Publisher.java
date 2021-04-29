package com.example.androidnotes.observe;

import com.example.androidnotes.data.NotesData;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private final List<Observer> observers;

    public Publisher() {
        observers = new ArrayList<>();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifySingle(NotesData notesData) {
        for (Observer observer : observers) {
            observer.updateCardData(notesData);
            unsubscribe(observer);

        }
    }
}
