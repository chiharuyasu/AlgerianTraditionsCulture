package com.example.algeriantraditionsculture;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.algeriantraditionsculture.adapter.SearchAdapter;
import com.example.algeriantraditionsculture.model.Category;
import com.example.algeriantraditionsculture.model.Tradition;
import com.example.algeriantraditionsculture.model.SearchResult;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.OnSearchResultClickListener {
    private SearchBar searchBar;
    private SearchView searchView;
    private RecyclerView searchResultsRecyclerView;
    private SearchAdapter searchAdapter;
    private List<SearchResult> allResults = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = findViewById(R.id.searchBar);
        searchView = findViewById(R.id.searchView);
        searchResultsRecyclerView = findViewById(R.id.searchResultsRecyclerView);

        // Set up RecyclerView
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(this);
        searchResultsRecyclerView.setAdapter(searchAdapter);

        // Aggregate all searchable data
        aggregateAllSearchableData();
        searchAdapter.setSearchResults(allResults);

        // Setup search
        searchView.setupWithSearchBar(searchBar);
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
    }

    private void aggregateAllSearchableData() {
        // TODO: Aggregate all categories, traditions, proverbs, anthem, etc.
        // For demo, add sample data. Replace with real data source.
        List<Category> categories = SampleData.getCategories();
        List<Tradition> traditions = SampleData.getAllTraditions();

        for (Category category : categories) {
            allResults.add(new SearchResult(SearchResult.TYPE_CATEGORY, category, category.getName(), category.getDescription(), category.getImageResourceId()));
        }
        for (Tradition tradition : traditions) {
            allResults.add(new SearchResult(SearchResult.TYPE_TRADITION, tradition, tradition.getTitle(), tradition.getDescription(), Integer.parseInt(tradition.getImageUrl())));
        }
        // Add more types if needed
    }

    private void performSearch(String query) {
        String searchQuery = query.toLowerCase().trim();
        List<SearchResult> filtered = new ArrayList<>();
        if (searchQuery.isEmpty()) {
            filtered.addAll(allResults);
        } else {
            for (SearchResult result : allResults) {
                if (result.getTitle().toLowerCase().contains(searchQuery) ||
                    result.getDescription().toLowerCase().contains(searchQuery)) {
                    filtered.add(result);
                }
            }
        }
        searchAdapter.setSearchResults(filtered);
        if (filtered.isEmpty() && !searchQuery.isEmpty()) {
            Toast.makeText(this, "No results found for: " + query, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSearchResultClick(SearchResult result) {
        // TODO: Handle click, open detail screen based on result type
        Toast.makeText(this, "Clicked: " + result.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
