package com.example.androidnotes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidnotes.R;
import com.example.androidnotes.data.CardData;
import com.example.androidnotes.data.CardsSource;


public class Adapter
        extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final CardsSource dataSource;
    private OnItemClickListener itemClickListener;  // Слушатель будет устанавливаться извне

    public Adapter(CardsSource dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder viewHolder, int i) {
        viewHolder.setData(dataSource.getCardData(i));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
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
            dateNote = itemView.findViewById(R.id.date_note);
            like = itemView.findViewById(R.id.like);


            title.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        public void setData(CardData cardData) {
            title.setText(cardData.getTitle());
            description.setText(cardData.getDescription());
            dateNote.setText(cardData.getDateNote());
            like.setChecked(cardData.isLike());

        }
    }
}