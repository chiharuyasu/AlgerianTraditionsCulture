package com.example.algeriantraditionsculture.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.algeriantraditionsculture.R;
import com.example.algeriantraditionsculture.model.Category;
import com.example.algeriantraditionsculture.model.Tradition;

import java.util.ArrayList;
import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_CATEGORY = 0;
    private static final int TYPE_TRADITION = 1;
    private List<Object> results = new ArrayList<>();
    private OnSearchResultClickListener listener;

    public interface OnSearchResultClickListener {
        void onCategoryClick(Category category);
        void onTraditionClick(Tradition tradition);
    }

    public SearchResultAdapter(OnSearchResultClickListener listener) {
        this.listener = listener;
    }

    public void setResults(List<Object> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = results.get(position);
        if (item instanceof Category) return TYPE_CATEGORY;
        else return TYPE_TRADITION;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_CATEGORY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
            return new CategoryViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tradition, parent, false);
            return new TraditionViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = results.get(position);
        if (holder.getItemViewType() == TYPE_CATEGORY) {
            ((CategoryViewHolder) holder).bind((Category) item);
        } else {
            ((TraditionViewHolder) holder).bind((Tradition) item);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, description;
        CategoryViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.categoryImage);
            name = itemView.findViewById(R.id.categoryName);
            description = itemView.findViewById(R.id.categoryDescription);
        }
        void bind(final Category category) {
            name.setText(category.getName());
            description.setText(category.getDescription());
            Glide.with(image.getContext()).load(category.getImageResourceId()).centerCrop().into(image);
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onCategoryClick(category);
            });
        }
    }

    class TraditionViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, description;
        TraditionViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.traditionImage);
            title = itemView.findViewById(R.id.traditionTitle);
            description = itemView.findViewById(R.id.traditionDescription);
        }
        void bind(final Tradition tradition) {
            title.setText(tradition.getTitle());
            description.setText(tradition.getDescription());
            Glide.with(image.getContext()).load(tradition.getImageUrl()).centerCrop().into(image);
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onTraditionClick(tradition);
            });
        }
    }
}
