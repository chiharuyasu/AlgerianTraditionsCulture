package com.example.algeriantraditionsculture.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.algeriantraditionsculture.R;

import java.util.ArrayList;
import java.util.List;

public class RelatedTraditionsAdapter extends RecyclerView.Adapter<RelatedTraditionsAdapter.RelatedTraditionViewHolder> {
    private List<String> relatedTraditions = new ArrayList<>();
    private OnRelatedTraditionClickListener listener;

    public interface OnRelatedTraditionClickListener {
        void onRelatedTraditionClick(String tradition);
    }

    public void setOnRelatedTraditionClickListener(OnRelatedTraditionClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RelatedTraditionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_related_tradition, parent, false);
        return new RelatedTraditionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedTraditionViewHolder holder, int position) {
        holder.bind(relatedTraditions.get(position));
    }

    @Override
    public int getItemCount() {
        return relatedTraditions.size();
    }

    public void setRelatedTraditions(String[] relatedTraditions) {
        this.relatedTraditions.clear();
        for (String tradition : relatedTraditions) {
            this.relatedTraditions.add(tradition);
        }
        notifyDataSetChanged();
    }

    class RelatedTraditionViewHolder extends RecyclerView.ViewHolder {
        private final TextView traditionText;

        RelatedTraditionViewHolder(@NonNull View itemView) {
            super(itemView);
            traditionText = itemView.findViewById(R.id.traditionText);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onRelatedTraditionClick(relatedTraditions.get(position));
                }
            });
        }

        void bind(String tradition) {
            traditionText.setText(tradition);
        }
    }
} 