package com.example.algeriantraditionsculture;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.algeriantraditionsculture.model.Tradition;

public class TraditionDetailActivity extends AppCompatActivity {
    private ImageView traditionImage;
    private TextView traditionTitle;
    private TextView traditionDescription;
    private TextView historicalBackground;
    private ImageButton btnFavorite;
    private ImageButton btnShare;
    private VideoView videoPlayer;
    private LinearLayout audioPlayer;
    private ImageButton btnPlayAudio;
    private SeekBar audioSeekBar;
    private MediaPlayer mediaPlayer;
    private Tradition tradition;
    private int categoryId;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradition_detail);

        // Get tradition and category information from intent
        int traditionId = getIntent().getIntExtra("tradition_id", -1);
        categoryId = getIntent().getIntExtra("category_id", -1);
        categoryName = getIntent().getStringExtra("category_name");

        if (traditionId == -1 || categoryId == -1 || categoryName == null) {
            Toast.makeText(this, "Error: Tradition information not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize views
        initializeViews();

        // Setup toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(categoryName);
        }

        // Load tradition data
        loadTraditionData(traditionId);

        // Setup click listeners
        setupClickListeners();
    }

    private void initializeViews() {
        traditionImage = findViewById(R.id.traditionImage);
        traditionTitle = findViewById(R.id.traditionTitle);
        traditionDescription = findViewById(R.id.traditionDescription);
        historicalBackground = findViewById(R.id.historicalBackground);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnShare = findViewById(R.id.btnShare);
        videoPlayer = findViewById(R.id.videoPlayer);
        audioPlayer = findViewById(R.id.audioPlayer);
        btnPlayAudio = findViewById(R.id.btnPlayAudio);
        audioSeekBar = findViewById(R.id.audioSeekBar);
    }

    private void loadTraditionData(int traditionId) {
        // Load tradition data based on traditionId and categoryId
        switch (categoryId) {
            case 1: // Traditional Clothing
                if (traditionId == 1) {
                    tradition = new Tradition(1, 1, "Karakou", 
                        "Traditional Algerian dress with intricate embroidery", 
                        "The Karakou is a traditional dress that has been worn by Algerian women for centuries. " +
                        "It features elaborate embroidery and is often made from luxurious fabrics. " +
                        "The dress is typically worn during special occasions and celebrations.",
                        R.drawable.karakou, null, null, "Algeria");
                } else if (traditionId == 2) {
                    tradition = new Tradition(2, 1, "Burnous", 
                        "Traditional woolen cloak worn by men", 
                        "The Burnous is a traditional woolen cloak that has been worn by Algerian men for generations. " +
                        "It is made from wool and provides excellent protection against the cold and rain.",
                        R.drawable.burnous, null, null, "Algeria");
                }
                break;
            case 2: // Festivals & Celebrations
                if (traditionId == 3) {
                    tradition = new Tradition(3, 2, "Eid al-Fitr", 
                        "Celebration marking the end of Ramadan", 
                        "Eid al-Fitr is one of the most important religious celebrations in Algeria. " +
                        "Families gather to share traditional meals and exchange gifts.",
                        R.drawable.eid, null, null, "Algeria");
                } else if (traditionId == 4) {
                    tradition = new Tradition(4, 2, "Yennayer", 
                        "Algerian New Year Celebration", 
                        "Yennayer is the Amazigh New Year celebration, marking the beginning of the agricultural year. " +
                        "It is celebrated with traditional foods and cultural activities.",
                        R.drawable.yennayer, null, null, "Algeria");
                }
                break;
            case 3: // Food & Cuisine
                if (traditionId == 5) {
                    tradition = new Tradition(5, 3, "Couscous", 
                        "Traditional Algerian dish", 
                        "Couscous is a staple dish in Algerian cuisine, made from steamed semolina grains. " +
                        "It is typically served with vegetables and meat in a flavorful broth.",
                        R.drawable.couscous, null, null, "Algeria");
                }
                break;
            // Add more cases for other categories
        }

        if (tradition == null) {
            Toast.makeText(this, "Error: Tradition not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Update UI with tradition data
        traditionTitle.setText(tradition.getTitle());
        traditionDescription.setText(tradition.getDescription());
        historicalBackground.setText(tradition.getHistoricalBackground());

        Glide.with(this)
                .load(tradition.getImageResourceId())
                .centerCrop()
                .into(traditionImage);

        // Setup media content if available
        setupMediaContent();
    }

    private void setupMediaContent() {
        // Setup video if available
        if (tradition.getVideoUrl() != null) {
            videoPlayer.setVisibility(View.VISIBLE);
            videoPlayer.setVideoURI(Uri.parse(tradition.getVideoUrl()));
        }

        // Setup audio if available
        if (tradition.getAudioUrl() != null) {
            audioPlayer.setVisibility(View.VISIBLE);
            setupAudioPlayer();
        }
    }

    private void setupAudioPlayer() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(tradition.getAudioUrl());
            mediaPlayer.prepare();

            audioSeekBar.setMax(mediaPlayer.getDuration());
            audioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            btnPlayAudio.setOnClickListener(v -> {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlayAudio.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    mediaPlayer.start();
                    btnPlayAudio.setImageResource(android.R.drawable.ic_media_pause);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupClickListeners() {
        btnFavorite.setOnClickListener(v -> {
            tradition.setFavorite(!tradition.isFavorite());
            btnFavorite.setImageResource(
                tradition.isFavorite() ? 
                android.R.drawable.btn_star_big_on : 
                android.R.drawable.btn_star_big_off
            );
            Toast.makeText(this, 
                tradition.isFavorite() ? R.string.msg_added_to_favorites : R.string.msg_removed_from_favorites, 
                Toast.LENGTH_SHORT).show();
        });

        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, tradition.getTitle());
            shareIntent.putExtra(Intent.EXTRA_TEXT, 
                tradition.getTitle() + "\n\n" + tradition.getDescription());
            startActivity(Intent.createChooser(shareIntent, getString(R.string.msg_share_tradition)));
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
} 