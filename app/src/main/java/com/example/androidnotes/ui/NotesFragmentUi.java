package com.example.androidnotes.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidnotes.MainActivity;
import com.example.androidnotes.Navigation;
import com.example.androidnotes.R;
import com.example.androidnotes.data.NotesSource;
import com.example.androidnotes.data.NotesSourceFirebaseImpl;
import com.example.androidnotes.observe.Publisher;



public class NotesFragmentUi extends Fragment {


    private NotesSource data;
    private NotesAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;
    private boolean moveToFirstPosition;

    public static NotesFragmentUi newInstance() {
        return new NotesFragmentUi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_new, container, false);
        initView(view);
        setHasOptionsMenu(true);
        data = new NotesSourceFirebaseImpl().init(notesData -> adapter.notifyDataSetChanged());
        adapter.setDataSource(data);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notes_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        initRecyclerView();
    }

    private boolean onItemSelected(int menuItemId) {

        switch (menuItemId) {
            case R.id.action_add:
                navigation.addFragment(NotesFragment.newInstance(), true);
                publisher.subscribe(notesData -> {
                    data.addCardData(notesData);
                    adapter.notifyItemInserted(data.size() - 1);
                    moveToFirstPosition = true;
                });
                return true;
            case R.id.action_update:
                final int updatePosition = adapter.getMenuPosition();
                navigation.addFragment(NotesFragment.newInstance(data.getCardData(updatePosition)), true);
                publisher.subscribe(notesData -> {
                    data.updateCardData(updatePosition, notesData);
                    adapter.notifyItemChanged(updatePosition);
                });
                return true;
            case R.id.action_delete:
                deleteAlertDialog();
                return true;
            case R.id.action_clear:
                clearAlertDialog();
                return true;
        }
        return false;
    }

    private void deleteAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.delete_confirmation)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    int deletePosition = adapter.getMenuPosition();
                    data.deleteCardData(deletePosition);
                    adapter.notifyItemRemoved(deletePosition);
                    Toast.makeText(getActivity(), R.string.delete_note, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {
                    Toast.makeText(getActivity(),R.string.delete_note_cancel, Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void clearAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.clear_confirmation)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    data.clearCardData();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), R.string.delete_note, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {
                    Toast.makeText(getActivity(),R.string.delete_note_cancel, Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void initRecyclerView() {

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NotesAdapter(this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);


        if (moveToFirstPosition && data.size() > 0) {
            recyclerView.scrollToPosition(0);
            moveToFirstPosition = false;
        }

        adapter.setOnItemClickListener((view, position) -> Toast.makeText(getContext(), String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.note_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
    }
}