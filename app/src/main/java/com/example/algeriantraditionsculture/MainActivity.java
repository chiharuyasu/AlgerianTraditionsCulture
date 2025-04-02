package com.example.algeriantraditionsculture;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.algeriantraditionsculture.adapter.CategoryAdapter;
import com.example.algeriantraditionsculture.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener {
    private RecyclerView categoriesRecyclerView;
    private CategoryAdapter categoryAdapter;
    private FloatingActionButton fabSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        fabSearch = findViewById(R.id.fabSearch);

        // Setup RecyclerView
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter = new CategoryAdapter(this);
        categoriesRecyclerView.setAdapter(categoryAdapter);

        // Setup FAB
        fabSearch.setOnClickListener(v -> {
            // TODO: Implement search functionality
            Toast.makeText(this, "Search feature coming soon!", Toast.LENGTH_SHORT).show();
        });

        // Load categories
        loadCategories();
    }

    private void loadCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Traditional Clothing", 
            "Discover the rich variety of traditional Algerian clothing", R.drawable.traditional_clothing));
        categories.add(new Category(2, "Festivals & Celebrations", 
            "Explore the vibrant festivals and celebrations of Algeria", R.drawable.festivals));
        categories.add(new Category(3, "Food & Cuisine", 
            "Taste the authentic flavors of Algerian cuisine", R.drawable.food));
        categories.add(new Category(4, "Marriage Customs", 
            "Learn about traditional Algerian marriage customs", R.drawable.marriage));
        categories.add(new Category(5, "Historical Landmarks", 
            "Visit the historical landmarks of Algeria", R.drawable.landmarks));
        categories.add(new Category(6, "Popular Sayings & Proverbs", 
            "Discover the wisdom of Algerian proverbs", R.drawable.proverbs));

        categoryAdapter.setCategories(categories);
    }

    @Override
    public void onCategoryClick(Category category) {
        // TODO: Navigate to CategoryDetailActivity
        Intent intent = new Intent(this, CategoryDetailActivity.class);
        intent.putExtra("category_id", category.getId());
        intent.putExtra("category_name", category.getName());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorites) {
            // TODO: Navigate to FavoritesActivity
            Toast.makeText(this, "Favorites feature coming soon!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}