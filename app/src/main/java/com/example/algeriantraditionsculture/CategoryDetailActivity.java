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
                traditions.add(new Tradition(
                    1, // id
                    "Karakou", // title
                    "The Karakou is a traditional Algerian dress known for its intricate embroidery and rich cultural significance. It is typically worn during special occasions and weddings.", // description
                    "The Karakou has its origins in the Ottoman period and has evolved over centuries. It represents the fusion of Algerian and Ottoman cultures, with its distinctive embroidery patterns telling stories of heritage and craftsmanship.", // historicalBackground
                    "android.resource://" + getPackageName() + "/" + R.drawable.karakou, // imageUrl
                    "karakou_video.mp4", // videoUrl
                    null, // audioUrl
                    "Algiers, Constantine, Oran", // region
                    "Weddings and Special Occasions", // season
                    new String[]{ // ingredients
                        "Fine silk fabric",
                        "Gold and silver threads",
                        "Traditional embroidery patterns",
                        "Velvet accents",
                        "Pearl and bead decorations"
                    },
                    new String[]{ // steps
                        "Selection of high-quality silk fabric",
                        "Traditional pattern design",
                        "Hand embroidery with gold threads",
                        "Assembly of dress components",
                        "Final embellishments and adjustments"
                    },
                    new String[]{ // relatedTraditions
                        "Haik",
                        "Burnous",
                        "Caftan"
                    }
                ));
                traditions.add(new Tradition(
                    2, // id
                    "Burnous", // title
                    "The Burnous is a traditional woolen cloak worn by Algerian men, especially in rural areas. It is known for its warmth and distinctive hood.", // description
                    "The Burnous has been worn in North Africa for centuries, with its design influenced by both Berber and Arab cultures. It was traditionally made from camel or sheep wool.", // historicalBackground
                    "android.resource://" + getPackageName() + "/" + R.drawable.burnous, // imageUrl
                    "burnous_video.mp4", // videoUrl
                    null, // audioUrl
                    "Kabylie, Aurès, Sahara", // region
                    "Winter and Cold Seasons", // season
                    new String[]{ // ingredients
                        "Natural wool",
                        "Traditional weaving tools",
                        "Natural dyes",
                        "Handmade buttons"
                    },
                    new String[]{ // steps
                        "Wool collection and preparation",
                        "Traditional weaving process",
                        "Natural dyeing",
                        "Assembly and finishing",
                        "Decoration and embellishment"
                    },
                    new String[]{ // relatedTraditions
                        "Gandoura",
                        "Djellaba",
                        "Seroual"
                    }
                ));
                break;
            case 2: // Festivals & Celebrations
                traditions.add(new Tradition(
                    3, // id
                    "Eid al-Fitr", // title
                    "Eid al-Fitr is a major Islamic festival marking the end of Ramadan. In Algeria, it is celebrated with special prayers, family gatherings, and traditional foods.", // description
                    "Eid al-Fitr has been celebrated in Algeria for centuries, with traditions passed down through generations. It represents the culmination of a month of fasting and spiritual reflection.", // historicalBackground
                    "android.resource://" + getPackageName() + "/" + R.drawable.eid, // imageUrl
                    "eid_video.mp4", // videoUrl
                    null, // audioUrl
                    "Throughout Algeria", // region
                    "End of Ramadan", // season
                    new String[]{ // ingredients
                        "Traditional sweets",
                        "Dates",
                        "Coffee",
                        "Mint tea"
                    },
                    new String[]{ // steps
                        "Early morning prayers",
                        "Family visits",
                        "Traditional meal preparation",
                        "Gift exchange",
                        "Community celebrations"
                    },
                    new String[]{ // relatedTraditions
                        "Ramadan",
                        "Eid al-Adha",
                        "Mawlid"
                    }
                ));
                traditions.add(new Tradition(
                    4, // id
                    "Yennayer", // title
                    "Yennayer is the Amazigh (Berber) New Year celebration, marking the beginning of the agricultural year. It is celebrated with traditional foods and cultural events.", // description
                    "Yennayer has been celebrated for over 2000 years in North Africa. It represents the Amazigh calendar and agricultural traditions.", // historicalBackground
                    "android.resource://" + getPackageName() + "/" + R.drawable.yennayer, // imageUrl
                    "yennayer_video.mp4", // videoUrl
                    null, // audioUrl
                    "Kabylie, Aurès, M'zab", // region
                    "January 12th", // season
                    new String[]{ // ingredients
                        "Couscous",
                        "Chicken",
                        "Seven vegetables",
                        "Traditional bread",
                        "Dried fruits"
                    },
                    new String[]{ // steps
                        "Preparation of traditional dishes",
                        "Family gathering",
                        "Cultural performances",
                        "Traditional games",
                        "Storytelling"
                    },
                    new String[]{ // relatedTraditions
                        "Imensi n Yennayer",
                        "Tafsut",
                        "Tiwizi"
                    }
                ));
                break;
            case 3: // Food & Cuisine
                traditions.add(new Tradition(
                    5, // id
                    "Couscous", // title
                    "Couscous is the national dish of Algeria, made from steamed semolina grains served with vegetables and meat.", // description
                    "Couscous has been a staple in Algerian cuisine for centuries, with its preparation methods passed down through generations. It represents the agricultural heritage of the region.", // historicalBackground
                    "android.resource://" + getPackageName() + "/" + R.drawable.couscous, // imageUrl
                    "couscous_video.mp4", // videoUrl
                    null, // audioUrl
                    "Throughout Algeria", // region
                    "All year, especially Fridays", // season
                    new String[]{ // ingredients
                        "Semolina",
                        "Lamb or chicken",
                        "Carrots",
                        "Zucchini",
                        "Chickpeas",
                        "Turnips",
                        "Onions",
                        "Tomatoes",
                        "Spices"
                    },
                    new String[]{ // steps
                        "Prepare the semolina",
                        "Steam the couscous",
                        "Cook the meat and vegetables",
                        "Prepare the sauce",
                        "Assemble and serve"
                    },
                    new String[]{ // relatedTraditions
                        "Tajine",
                        "Chorba",
                        "Merguez"
                    }
                ));
                break;
            // Add more cases for other categories
        }
        traditionAdapter.setTraditions(traditions);
    }

    @Override
    public void onTraditionClick(Tradition tradition) {
        Intent intent = new Intent(this, TraditionDetailActivity.class);
        intent.putExtra("tradition_id", tradition.getId());
        intent.putExtra("category_id", categoryId);
        intent.putExtra("category_name", categoryName);
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