package com.example.androidnotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class TextNotesFragment extends Fragment {

    public static final String ARG_INDEX = "index";

    private int index;

    public static TextNotesFragment newInstance(int index) {
        TextNotesFragment fragment = new TextNotesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    public TextNotesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_notes, container, false);
        TextView textView = view.findViewById(R.id.textView);
        String[] notes = getResources().getStringArray(R.array.textNotes);
        textView.setText(notes[index]);
        return view;
    }
}