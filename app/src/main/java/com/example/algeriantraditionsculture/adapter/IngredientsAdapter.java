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

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {
    private List<String> ingredients = new ArrayList<>();

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients.clear();
        for (String ingredient : ingredients) {
            this.ingredients.add(ingredient);
        }
        notifyDataSetChanged();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private final TextView ingredientText;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientText = itemView.findViewById(R.id.ingredientText);
        }

        void bind(String ingredient) {
            ingredientText.setText(ingredient);
        }
    }
} 