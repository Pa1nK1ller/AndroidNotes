package com.example.androidnotes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static java.lang.String.format;

public class NotesFragment extends Fragment {

    private boolean isLandscape;

    public NotesFragment() {
    }

    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (isLandscape) {
            showLandTextNotes(0);
        }
        initView(view);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    private void initView(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.notes);
        String[] notesDate = getResources().getStringArray(R.array.notes_date);
        String[] notesDescriptions = getResources().getStringArray(R.array.descriptionNotes);
        for (int i = 0; i < notes.length; i++) {
            String note = notes[i];
            String noteDate = notesDate[i];
            String noteDescription = notesDescriptions[i];
            TextView tv = new TextView(getContext());
            tv.setText(format("%S: %s\n%s: %s\n%s :%s\n", getString(R.string.NameNote), note, getString(R.string.DateNote), noteDate, getString(R.string.DescriptionNote), noteDescription));
            tv.setTextSize(20);
            linearLayout.addView(tv);

            final int index = i;
            tv.setOnClickListener(v -> showTextNotes(index));
        }
    }

    private void showTextNotes(int index) {
        if (isLandscape) {
            showLandTextNotes(index);
        } else {
            showPortTextNotes(index);
        }
    }

    private void showLandTextNotes(int index) {
        TextNotesFragment textNotesFragment = TextNotesFragment.newInstance(index);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.notesText, textNotesFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void showPortTextNotes(int index) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ActivityForNotes.class);
        intent.putExtra(TextNotesFragment.ARG_INDEX, index);
        startActivity(intent);
    }
}