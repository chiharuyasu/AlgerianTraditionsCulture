package com.example.algeriantraditionsculture;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.algeriantraditionsculture.adapter.CategoryAdapter;
import com.example.algeriantraditionsculture.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener {
    private RecyclerView categoriesRecyclerView;
    private CategoryAdapter categoryAdapter;
    private List<Category> categories;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        // Initialize search view
        searchView = findViewById(R.id.searchView);
        // Make SearchView expand when tapping anywhere
        searchView.setOnClickListener(v -> searchView.setIconified(false));

        // Initialize categories list
        categories = new ArrayList<>();

        // Setup RecyclerView
        setupRecyclerView();

        // Load categories
        loadCategories();

        setupSearchSystem();
    }

    private void setupRecyclerView() {
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter = new CategoryAdapter(categories, this);
        categoriesRecyclerView.setAdapter(categoryAdapter);
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

    private void setupSearchSystem() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCategories(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCategories(newText);
                return true;
            }
        });
    }

    private void filterCategories(String query) {
        List<Category> filteredList = new ArrayList<>();
        for (Category category : categories) {
            if (category.getName().toLowerCase().contains(query.toLowerCase()) ||
                category.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(category);
            }
        }
        categoryAdapter.setCategories(filteredList);
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
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        setupSearchSystem();
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