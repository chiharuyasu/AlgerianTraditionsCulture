package com.example.algeriantraditionsculture;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.algeriantraditionsculture.adapter.TraditionAdapter;
import com.example.algeriantraditionsculture.model.Tradition;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetailActivity extends AppCompatActivity implements TraditionAdapter.OnTraditionClickListener {
    private RecyclerView traditionsRecyclerView;
    private TraditionAdapter traditionAdapter;
    private int categoryId;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        // Get category information from intent
        categoryId = getIntent().getIntExtra("category_id", -1);
        categoryName = getIntent().getStringExtra("category_name");

        // Setup toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(categoryName);
        }

        // Initialize views
        traditionsRecyclerView = findViewById(R.id.traditionsRecyclerView);

        // Setup RecyclerView
        traditionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        traditionAdapter = new TraditionAdapter(this);
        traditionsRecyclerView.setAdapter(traditionAdapter);

        // Load traditions
        loadTraditions();
    }

    private void loadTraditions() {
        List<Tradition> traditions = new ArrayList<>();
        // TODO: Load traditions from database or API based on categoryId
        // For now, we'll add some sample data
        switch (categoryId) {
            case 1: // Traditional Clothing
                traditions.add(new Tradition(1, 1, "Karakou", 
                    "Traditional Algerian dress with intricate embroidery", 
                    "The Karakou is a traditional dress that has been worn by Algerian women for centuries...",
                    R.drawable.karakou, null, null, "Algeria"));
                traditions.add(new Tradition(2, 1, "Burnous", 
                    "Traditional woolen cloak worn by men", 
                    "The Burnous is a traditional woolen cloak that has been worn by Algerian men...",
                    R.drawable.burnous, null, null, "Algeria"));
                break;
            case 2: // Festivals & Celebrations
                traditions.add(new Tradition(3, 2, "Eid al-Fitr", 
                    "Celebration marking the end of Ramadan", 
                    "Eid al-Fitr is one of the most important religious celebrations in Algeria...",
                    R.drawable.eid, null, null, "Algeria"));
                break;
            // Add more cases for other categories
        }
        traditionAdapter.setTraditions(traditions);
    }

    @Override
    public void onTraditionClick(Tradition tradition) {
        // TODO: Navigate to TraditionDetailActivity
        Intent intent = new Intent(this, TraditionDetailActivity.class);
        intent.putExtra("tradition_id", tradition.getId());
        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(Tradition tradition, boolean isFavorite) {
        // TODO: Save favorite status to database
        Toast.makeText(this, 
            isFavorite ? "Added to favorites" : "Removed from favorites", 
            Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareClick(Tradition tradition) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, tradition.getTitle());
        shareIntent.putExtra(Intent.EXTRA_TEXT, 
            tradition.getTitle() + "\n\n" + tradition.getDescription());
        startActivity(Intent.createChooser(shareIntent, "Share Tradition"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 