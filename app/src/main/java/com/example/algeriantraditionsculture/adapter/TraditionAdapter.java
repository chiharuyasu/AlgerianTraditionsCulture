package com.example.algeriantraditionsculture.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.algeriantraditionsculture.R;
import com.example.algeriantraditionsculture.model.Tradition;

import java.util.ArrayList;
import java.util.List;

public class TraditionAdapter extends RecyclerView.Adapter<TraditionAdapter.TraditionViewHolder> {
    private List<Tradition> traditions = new ArrayList<>();
    private OnTraditionClickListener listener;

    public interface OnTraditionClickListener {
        void onTraditionClick(Tradition tradition);
        void onFavoriteClick(Tradition tradition, boolean isFavorite);
        void onShareClick(Tradition tradition);
    }

    public TraditionAdapter(OnTraditionClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TraditionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tradition, parent, false);
        return new TraditionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TraditionViewHolder holder, int position) {
        Tradition currentTradition = traditions.get(position);
        holder.traditionTitle.setText(currentTradition.getTitle());
        holder.traditionDescription.setText(currentTradition.getDescription());
        
        // Set favorite button state
        holder.btnFavorite.setImageResource(
            currentTradition.isFavorite() ? 
            android.R.drawable.btn_star_big_on : 
            android.R.drawable.btn_star_big_off
        );

        Glide.with(holder.itemView.getContext())
                .load(currentTradition.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .centerCrop()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (e != null) {
                            Log.e("TraditionAdapter", "Image loading failed: " + e.getMessage());
                            e.logRootCauses("TraditionAdapter");
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.traditionImage);
    }

    @Override
    public int getItemCount() {
        return traditions.size();
    }

    public void setTraditions(List<Tradition> traditions) {
        this.traditions = traditions;
        notifyDataSetChanged();
    }

    class TraditionViewHolder extends RecyclerView.ViewHolder {
        private ImageView traditionImage;
        private TextView traditionTitle;
        private TextView traditionDescription;
        private ImageButton btnFavorite;
        private ImageButton btnShare;

        public TraditionViewHolder(@NonNull View itemView) {
            super(itemView);
            traditionImage = itemView.findViewById(R.id.traditionImage);
            traditionTitle = itemView.findViewById(R.id.traditionTitle);
            traditionDescription = itemView.findViewById(R.id.traditionDescription);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            btnShare = itemView.findViewById(R.id.btnShare);

            // Set click listeners
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onTraditionClick(traditions.get(position));
                }
            });

            btnFavorite.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    Tradition tradition = traditions.get(position);
                    tradition.setFavorite(!tradition.isFavorite());
                    listener.onFavoriteClick(tradition, tradition.isFavorite());
                    notifyItemChanged(position);
                }
            });

            btnShare.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onShareClick(traditions.get(position));
                }
            });
        }
    }
} 