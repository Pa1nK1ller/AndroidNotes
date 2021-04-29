package com.example.androidnotes.data;

public class CardData {
    private final String title;
    private final String description;
    private final String dateNote;
    private final boolean like;

    public CardData(String title, String description, String dateNote, boolean like) {
        this.title = title;
        this.description = description;
        this.dateNote = dateNote;
        this.like = like;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateNote() {
        return dateNote;
    }

    public boolean isLike() {
        return like;
    }
}