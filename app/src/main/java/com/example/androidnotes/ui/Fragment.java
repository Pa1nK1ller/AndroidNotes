package com.example.androidnotes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidnotes.ActivityForNotes;
import com.example.androidnotes.R;
import com.example.androidnotes.TextNotesFragment;
import com.example.androidnotes.data.CardsSource;
import com.example.androidnotes.data.CardsSourceImpl;


public class Fragment extends androidx.fragment.app.Fragment {

    public static Fragment newInstance() {
        return new Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_new, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        CardsSource data = new CardsSourceImpl(getResources()).init();
        initRecyclerView(recyclerView, data);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, CardsSource data) {

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        final Adapter adapter = new Adapter(data);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        adapter.SetOnItemClickListener((view, position) -> {
            showPortTextNotes(position);
        });
    }

    private void showPortTextNotes(int position) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ActivityForNotes.class);
        intent.putExtra(TextNotesFragment.ARG_INDEX, position);
        startActivity(intent);
    }
}