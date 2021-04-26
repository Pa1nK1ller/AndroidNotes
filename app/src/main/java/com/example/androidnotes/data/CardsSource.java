package com.example.androidnotes.data;

public interface CardsSource {
    CardData getCardData(int position);

    int size();
}