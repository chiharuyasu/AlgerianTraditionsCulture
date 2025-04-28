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

import com.example.algeriantraditionsculture.adapter.SearchResultAdapter;
import com.example.algeriantraditionsculture.model.Category;
import com.example.algeriantraditionsculture.model.Tradition;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView categoriesRecyclerView;
    private SearchResultAdapter searchResultAdapter;
    private List<Category> categories;
    private List<Tradition> traditions;
    private SearchView searchView;
    private boolean isSearching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        searchView = findViewById(R.id.searchView);
        searchView.setOnClickListener(v -> searchView.setIconified(false));

        categories = new ArrayList<>();
        traditions = new ArrayList<>();

        searchResultAdapter = new SearchResultAdapter(new SearchResultAdapter.OnSearchResultClickListener() {
            @Override
            public void onCategoryClick(Category category) {
                Intent intent = new Intent(MainActivity.this, CategoryDetailActivity.class);
                intent.putExtra("category_id", category.getId());
                intent.putExtra("category_name", category.getName());
                startActivity(intent);
            }
            @Override
            public void onTraditionClick(Tradition tradition) {
                Intent intent = new Intent(MainActivity.this, TraditionDetailActivity.class);
                intent.putExtra("tradition_id", tradition.getId());
                // Find category for this tradition if possible
                int foundCategoryId = -1;
                String foundCategoryName = null;
                // Assign category by tradition title or by a new field if available
                if (tradition.getTitle().toLowerCase().contains("couscous")) {
                    foundCategoryId = 3; // Food
                    foundCategoryName = getString(R.string.category_food);
                } else if (tradition.getTitle().toLowerCase().contains("henna") || tradition.getTitle().toLowerCase().contains("khouara")) {
                    foundCategoryId = 4; // Marriage Customs
                    foundCategoryName = getString(R.string.category_marriage);
                } else if (tradition.getTitle().toLowerCase().contains("fantasia")) {
                    foundCategoryId = 2; // Festivals
                    foundCategoryName = getString(R.string.category_festivals);
                } else if (tradition.getTitle().toLowerCase().contains("casbah")) {
                    foundCategoryId = 5; // Historical Landmarks
                    foundCategoryName = getString(R.string.category_landmarks);
                } else if (tradition.getTitle().toLowerCase().contains("eid")) {
                    foundCategoryId = 2; // Festivals & Celebrations
                    foundCategoryName = getString(R.string.category_festivals);
                } else if (tradition.getTitle().toLowerCase().contains("yennayer")) {
                    foundCategoryId = 2; // Festivals & Celebrations
                    foundCategoryName = getString(R.string.category_festivals);
                }
                // Default fallback
                if (foundCategoryId == -1) {
                    foundCategoryId = 1;
                    foundCategoryName = getString(R.string.category_traditional_clothing);
                }
                intent.putExtra("category_id", foundCategoryId);
                intent.putExtra("category_name", foundCategoryName);
                intent.putExtra("tradition_title", tradition.getTitle());
                startActivity(intent);
            }
        });
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoriesRecyclerView.setAdapter(searchResultAdapter);

        loadCategories();
        loadTraditions();
        searchResultAdapter.setResults(new ArrayList<>(categories));
        setupSearchSystem();
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
    }

    private void loadTraditions() {
        // IDs must match those expected in TraditionDetailActivity
        // Karakou (ID 1) - Traditional Clothing
        traditions.add(new Tradition(1, "Karakou",
                "The Karakou is a traditional Algerian dress known for its intricate embroidery and rich cultural significance. It is typically worn during special occasions and weddings.",
                "The Karakou originated in Algiers and is a symbol of Algerian heritage, blending Ottoman and local influences. Its velvet fabric and gold embroidery are hallmarks of Algerian craftsmanship.",
                "https://upload.wikimedia.org/wikipedia/commons/8/8f/Karakou_Algerie.jpg", null, null, "Algiers", "Weddings, Celebrations",
                new String[]{"Velvet fabric", "Gold thread", "Embroidery", "Traditional jewelry"},
                new String[]{"Tailoring the velvet base", "Hand embroidery", "Assembly of pieces", "Fitting and finishing touches"},
                new String[]{"Chedda", "Blousa", "Gandoura"}
        ));
        // Burnous (ID 2) - Traditional Clothing
        traditions.add(new Tradition(2, "Burnous",
                "The Burnous is a long, hooded cloak made of wool, worn by men in Algeria for warmth and as a symbol of honor.",
                "Worn since antiquity, the Burnous is associated with dignity and respect. It is commonly white and worn during important ceremonies and by elders.",
                "https://upload.wikimedia.org/wikipedia/commons/3/32/Burnous_algerien.jpg", null, null, "Throughout Algeria", "Winter, Ceremonies",
                new String[]{"Wool", "Handmade buttons"},
                new String[]{"Wool collection and preparation", "Traditional weaving process", "Natural dyeing", "Assembly and finishing", "Decoration and embellishment"},
                new String[]{"Gandoura", "Djellaba", "Seroual"}
        ));
        // Eid al-Fitr (ID 3) - Festivals & Celebrations
        traditions.add(new Tradition(3, "Eid al-Fitr",
                "Eid al-Fitr is a major Islamic festival marking the end of Ramadan. In Algeria, it is celebrated with special prayers, family gatherings, and traditional foods.",
                "Eid al-Fitr has been celebrated in Algeria for centuries, with traditions passed down through generations. It represents the culmination of a month of fasting and spiritual reflection.",
                "https://upload.wikimedia.org/wikipedia/commons/6/6c/Eid_al-Fitr_Algeria.jpg", null, null, "Throughout Algeria", "End of Ramadan",
                new String[]{"Traditional sweets", "Dates", "Coffee", "Mint tea"},
                new String[]{"Early morning prayers", "Family visits", "Traditional meal preparation", "Gift exchange", "Community celebrations"},
                new String[]{"Ramadan", "Eid al-Adha", "Mawlid"}
        ));
        // Yennayer (ID 4) - Festivals & Celebrations
        traditions.add(new Tradition(4, "Yennayer",
                "Yennayer is the Amazigh (Berber) New Year celebration, marking the beginning of the agricultural year. It is celebrated with traditional foods and cultural events.",
                "Yennayer has been celebrated for over 2000 years in North Africa. It represents the Amazigh calendar and agricultural traditions.",
                "https://upload.wikimedia.org/wikipedia/commons/3/3f/Yennayer_Algeria.jpg", null, null, "Kabylie, Aurès, M'zab", "January 12th",
                new String[]{"Couscous", "Chicken", "Seven vegetables", "Traditional bread", "Dried fruits"},
                new String[]{"Preparation of traditional dishes", "Family gathering", "Cultural performances", "Traditional games", "Storytelling"},
                new String[]{"Imensi n Yennayer", "Tafsut", "Tiwizi"}
        ));
        // Couscous (ID 5) - Food & Cuisine
        traditions.add(new Tradition(5, "Couscous",
                "Couscous is the national dish of Algeria, made from steamed semolina grains served with vegetables and meat.",
                "Couscous has been a staple in Algerian cuisine for centuries, with its preparation methods passed down through generations. It represents the agricultural heritage of the region.",
                "https://upload.wikimedia.org/wikipedia/commons/8/8a/Couscous.jpg", null, null, "Throughout Algeria", "All year, especially Fridays",
                new String[]{"Semolina", "Lamb or chicken", "Carrots", "Zucchini", "Chickpeas", "Turnips", "Onions", "Tomatoes", "Spices"},
                new String[]{"Prepare the semolina", "Steam the couscous", "Cook the meat and vegetables", "Prepare the sauce", "Assemble and serve"},
                new String[]{"Tajine", "Chorba", "Merguez"}
        ));
        // El Khouara (Henna Night) (ID 6) - Marriage Customs
        traditions.add(new Tradition(6, "El Khouara (Henna Night)",
                "El Khouara is the traditional Algerian henna night, celebrated before the wedding ceremony. It is a festive gathering where the bride is adorned with henna and surrounded by family and friends.",
                "Henna night is a centuries-old custom in Algeria, symbolizing beauty, joy, and blessings for the bride. The ritual includes singing, dancing, and the application of intricate henna designs.",
                "https://upload.wikimedia.org/wikipedia/commons/7/79/Henna_painting.jpg", null, null, "Throughout Algeria", "Wedding Eve",
                new String[]{"Henna paste", "Traditional dresses", "Sweets and pastries", "Musical instruments"},
                new String[]{"Preparation of henna paste", "Gathering of women", "Application of henna designs", "Singing and dancing", "Blessings and gift-giving"},
                new String[]{"Wedding Ceremony", "Engagement Party", "Aroussa Preparation"}
        ));
        // Casbah of Algiers (ID 7) - Historical Landmarks
        traditions.add(new Tradition(7, "Casbah of Algiers",
                "The Casbah is the historic medina of Algiers, a UNESCO World Heritage Site known for its labyrinthine streets, whitewashed houses, and rich history.",
                "The Casbah dates back to the 10th century and has witnessed centuries of Algerian history, from Berber dynasties to Ottoman rule and the fight for independence.",
                "https://upload.wikimedia.org/wikipedia/commons/9/9c/Casbah_Algiers.jpg", null, null, "Algiers", "All year",
                new String[]{"Stone-paved alleys", "Traditional houses", "Historic mosques", "Artisan workshops"},
                new String[]{"Guided walking tour", "Visit to historic sites", "Exploring artisan shops", "Tasting local foods"},
                new String[]{"Ketchaoua Mosque", "Palace of the Dey", "Martyrs' Square"}
        ));
        // Algerian National Anthem (ID 8) - Popular Sayings and Proverbs
        traditions.add(new Tradition(8, "Algerian National Anthem",
                "The national anthem of Algeria, 'Kassaman' (We Pledge), is a symbol of the country's independence and pride. It is played at official events and national celebrations.",
                "Written by Moufdi Zakaria in 1956 while imprisoned by French colonial authorities, and composed by Mohamed Fawzi, the anthem became the official anthem upon Algeria's independence in 1962.",
                "https://upload.wikimedia.org/wikipedia/commons/5/5c/Algerian_flag.jpg", null, null, "Throughout Algeria", "All year",
                new String[]{"Lyrics by Moufdi Zakaria", "Music by Mohamed Fawzi", "Adopted in 1962 after independence", "Represents the struggle for freedom", "Officially titled 'Kassaman' (We Pledge)"},
                new String[]{"Recitation of the anthem at national events", "Singing in schools and sports events", "Learning the anthem as part of civic education"},
                new String[]{"Independence Day", "Algerian flag", "National celebrations", "Algerian Constitution"}
        ));
        // Add more traditions as needed, using correct IDs
    }

    private void setupSearchSystem() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterResults(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.trim().isEmpty()) {
                    searchResultAdapter.setResults(new ArrayList<>(categories));
                    isSearching = false;
                } else {
                    filterResults(newText);
                }
                return true;
            }
        });
    }

    private void filterResults(String query) {
        List<Object> filtered = new ArrayList<>();
        String q = query.toLowerCase();
        for (Category category : categories) {
            if (category.getName().toLowerCase().contains(q) ||
                category.getDescription().toLowerCase().contains(q)) {
                filtered.add(category);
            }
        }
        for (Tradition tradition : traditions) {
            if (tradition.getTitle().toLowerCase().contains(q) ||
                (tradition.getDescription() != null && tradition.getDescription().toLowerCase().contains(q))) {
                filtered.add(tradition);
            }
        }
        searchResultAdapter.setResults(filtered);
        isSearching = true;
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