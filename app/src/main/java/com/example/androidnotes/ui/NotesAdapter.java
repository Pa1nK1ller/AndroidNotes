package com.example.androidnotes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidnotes.R;
import com.example.androidnotes.data.NotesData;
import com.example.androidnotes.data.NotesSource;

import java.text.SimpleDateFormat;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private NotesSource dataSource;
    private final NotesFragmentUi fragment;
    private OnItemClickListener itemClickListener;
    private int menuPosition;

    public NotesAdapter(NotesFragmentUi fragment) {
        this.fragment = fragment;
    }

    public void setDataSource(NotesSource dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder viewHolder, int i) {
        viewHolder.setData(dataSource.getCardData(i));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView description;
        private final TextView dateNote;
        private final CheckBox like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            dateNote = itemView.findViewById(R.id.date);
            like = itemView.findViewById(R.id.like);

            registerContextMenu(itemView);
            title.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
            title.setOnLongClickListener(v -> {
                menuPosition = getLayoutPosition();
                itemView.showContextMenu(10, 10);
                return true;
            });
        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null) {
                itemView.setOnLongClickListener(v -> {
                    menuPosition = getLayoutPosition();
                    return false;
                });
                fragment.registerForContextMenu(itemView);
            }
        }


        public void setData(NotesData notesData) {
            title.setText(notesData.getTitle());
            description.setText(notesData.getDescription());
            dateNote.setText(new SimpleDateFormat("dd-MM-yy").format(notesData.getDate()));
            like.setChecked(notesData.isLike());

        }
    }
}