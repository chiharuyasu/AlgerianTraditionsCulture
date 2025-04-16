package com.example.algeriantraditionsculture;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener {
    private RecyclerView categoriesRecyclerView;
    private CategoryAdapter categoryAdapter;
    private FloatingActionButton fabSearch;
    private List<Category> categories;
    private SearchBar searchBar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        fabSearch = findViewById(R.id.fabSearch);
        searchBar = findViewById(R.id.searchBar);
        searchView = findViewById(R.id.searchView);

        // Initialize categories list
        categories = new ArrayList<>();

        // Setup RecyclerView
        setupRecyclerView();

        // Setup FAB
        fabSearch.setOnClickListener(v -> {
            searchView.show();
        });

        // Setup search functionality
        setupSearch();

        // Load categories
        loadCategories();
    }

    private void setupRecyclerView() {
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter = new CategoryAdapter(categories, this);
        categoriesRecyclerView.setAdapter(categoryAdapter);
    }

    private void setupSearch() {
        // Setup search view
        searchView.setupWithSearchBar(searchBar);
        
        // Handle search text changes in real-time
        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Handle search bar click
        searchBar.setOnClickListener(v -> {
            searchView.show();
        });

        // Handle search view close
        searchView.addTransitionListener((searchView, previousState, newState) -> {
            if (newState == SearchView.TransitionState.HIDDEN) {
                // Reset the search results when search view is closed
                categoryAdapter.setCategories(categories);
            }
        });
    }

    private void performSearch(String query) {
        if (categories == null) return;

        String searchQuery = query.toLowerCase().trim();
        List<Category> searchResults = new ArrayList<>();

        if (searchQuery.isEmpty()) {
            // If search query is empty, show all categories
            searchResults.addAll(categories);
        } else {
            // Search in category name and description
            for (Category category : categories) {
                if (category.getName().toLowerCase().contains(searchQuery) ||
                    category.getDescription().toLowerCase().contains(searchQuery)) {
                    searchResults.add(category);
                }
            }
        }

        // Update the adapter with search results
        categoryAdapter.setCategories(searchResults);

        // Show "No results" message if needed
        if (searchResults.isEmpty() && !searchQuery.isEmpty()) {
            Toast.makeText(this, "No results found for: " + query, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadCategories() {
        categories.add(new Category(1, getString(R.string.category_traditional_clothing), 
            getString(R.string.category_clothing_desc), R.drawable.traditional_clothing));
        categories.add(new Category(2, getString(R.string.category_festivals), 
            getString(R.string.category_festivals_desc), R.drawable.festivals));
        categories.add(new Category(3, getString(R.string.category_food), 
            getString(R.string.category_food_desc), R.drawable.food));
        categories.add(new Category(4, getString(R.string.category_marriage), 
            getString(R.string.category_marriage_desc), R.drawable.marriage));
        categories.add(new Category(5, getString(R.string.category_landmarks), 
            getString(R.string.category_landmarks_desc), R.drawable.landmarks));
        categories.add(new Category(6, getString(R.string.category_proverbs), 
            getString(R.string.category_proverbs_desc), R.drawable.proverbs));

        categoryAdapter.setCategories(categories);
    }

    @Override
    public void onCategoryClick(Category category) {
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
        int id = item.getItemId();
        if (id == R.id.action_favorites) {
            Toast.makeText(this, R.string.msg_favorites_coming_soon, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}