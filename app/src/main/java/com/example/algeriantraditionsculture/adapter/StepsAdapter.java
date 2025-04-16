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

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {
    private List<String> steps = new ArrayList<>();

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(position + 1, steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void setSteps(String[] steps) {
        this.steps.clear();
        for (String step : steps) {
            this.steps.add(step);
        }
        notifyDataSetChanged();
    }

    static class StepViewHolder extends RecyclerView.ViewHolder {
        private final TextView stepNumber;
        private final TextView stepText;

        StepViewHolder(@NonNull View itemView) {
            super(itemView);
            stepNumber = itemView.findViewById(R.id.stepNumber);
            stepText = itemView.findViewById(R.id.stepText);
        }

        void bind(int number, String step) {
            stepNumber.setText(String.format("%d.", number));
            stepText.setText(step);
        }
    }
} 