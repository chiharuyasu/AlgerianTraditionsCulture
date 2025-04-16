package com.example.algeriantraditionsculture;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.algeriantraditionsculture.adapter.IngredientsAdapter;
import com.example.algeriantraditionsculture.adapter.StepsAdapter;
import com.example.algeriantraditionsculture.adapter.RelatedTraditionsAdapter;
import com.example.algeriantraditionsculture.adapter.CommentAdapter;
import com.example.algeriantraditionsculture.model.Tradition;
import com.example.algeriantraditionsculture.model.Comment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import android.content.res.AssetFileDescriptor;
import android.util.Log;
import java.util.Arrays;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class TraditionDetailActivity extends AppCompatActivity {
    private TextView traditionTitle;
    private TextView traditionDescription;
    private TextView historicalBackground;
    private ExtendedFloatingActionButton fabShare;
    private VideoView videoPlayer;
    private FloatingActionButton fabPlayVideo;
    private MaterialCardView historicalCard;
    private MaterialCardView audioCard;
    private LinearLayout audioPlayer;
    private ImageButton btnPlayAudio;
    private SeekBar audioSeekBar;
    private MediaPlayer mediaPlayer;
    private Tradition tradition;
    private int categoryId;
    private String categoryName;
    private boolean isVideoPlaying = false;
    private MaterialCardView regionCard;
    private MaterialCardView seasonCard;
    private MaterialCardView ingredientsCard;
    private MaterialCardView stepsCard;
    private MaterialCardView relatedTraditionsCard;
    private TextView regionText;
    private TextView seasonText;
    private RecyclerView ingredientsRecyclerView;
    private RecyclerView stepsRecyclerView;
    private RecyclerView relatedTraditionsRecyclerView;
    private IngredientsAdapter ingredientsAdapter;
    private StepsAdapter stepsAdapter;
    private RelatedTraditionsAdapter relatedTraditionsAdapter;
    private MaterialCardView commentsCard;
    private RecyclerView commentsRecyclerView;
    private TextInputEditText commentInput;
    private MaterialButton btnSubmitComment;
    private CommentAdapter commentAdapter;
    private List<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradition_detail);

        // List all available raw resources for debugging
        Field[] fields = R.raw.class.getFields();
        Log.d("VideoPlayer", "Available raw resources:");
        for (Field field : fields) {
            try {
                Log.d("VideoPlayer", "Resource: " + field.getName());
            } catch (Exception e) {
                Log.e("VideoPlayer", "Error listing resource: " + e.getMessage());
            }
        }

        // Initialize views
        initializeViews();
        initializeNewViews();

        // Setup RecyclerViews
        setupRecyclerViews();

        // Get tradition and category information from intent
        int traditionId = getIntent().getIntExtra("tradition_id", -1);
        categoryId = getIntent().getIntExtra("category_id", -1);
        categoryName = getIntent().getStringExtra("category_name");

        if (traditionId == -1 || categoryId == -1 || categoryName == null) {
            Toast.makeText(this, "Error: Tradition information not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

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

        // Setup comments
        setupComments();
    }

    private void initializeViews() {
        traditionTitle = findViewById(R.id.traditionTitle);
        traditionDescription = findViewById(R.id.traditionDescription);
        historicalBackground = findViewById(R.id.historicalBackground);
        fabShare = findViewById(R.id.fabShare);
        videoPlayer = findViewById(R.id.videoPlayer);
        fabPlayVideo = findViewById(R.id.fabPlayVideo);
        historicalCard = findViewById(R.id.historicalCard);
        audioCard = findViewById(R.id.audioCard);
        audioPlayer = findViewById(R.id.audioPlayer);
        btnPlayAudio = findViewById(R.id.btnPlayAudio);
        audioSeekBar = findViewById(R.id.audioSeekBar);
    }

    private void initializeNewViews() {
        regionCard = findViewById(R.id.regionCard);
        seasonCard = findViewById(R.id.seasonCard);
        ingredientsCard = findViewById(R.id.ingredientsCard);
        stepsCard = findViewById(R.id.stepsCard);
        relatedTraditionsCard = findViewById(R.id.relatedTraditionsCard);
        regionText = findViewById(R.id.regionText);
        seasonText = findViewById(R.id.seasonText);
        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView);
        stepsRecyclerView = findViewById(R.id.stepsRecyclerView);
        relatedTraditionsRecyclerView = findViewById(R.id.relatedTraditionsRecyclerView);
        commentsCard = findViewById(R.id.commentsCard);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        commentInput = findViewById(R.id.commentInput);
        btnSubmitComment = findViewById(R.id.btnSubmitComment);
    }

    private void setupRecyclerViews() {
        // Setup ingredients RecyclerView
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientsAdapter = new IngredientsAdapter();
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        // Setup steps RecyclerView
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stepsAdapter = new StepsAdapter();
        stepsRecyclerView.setAdapter(stepsAdapter);

        // Setup related traditions RecyclerView
        relatedTraditionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        relatedTraditionsAdapter = new RelatedTraditionsAdapter();
        relatedTraditionsRecyclerView.setAdapter(relatedTraditionsAdapter);
    }

    private void setupVideoPlayer(String videoUrl) {
        Log.d("VideoPlayer", "setupVideoPlayer called with URL: " + videoUrl);
        
        if (videoUrl != null && !videoUrl.isEmpty()) {
            try {
                // Make sure the video player and play button are visible
                videoPlayer.setVisibility(View.VISIBLE);
                fabPlayVideo.setVisibility(View.VISIBLE);
                
                // Get resource ID from raw folder
                int resourceId = getResources().getIdentifier(videoUrl, "raw", getPackageName());
                Log.d("VideoPlayer", "Resource ID for " + videoUrl + ": " + resourceId);
                
                if (resourceId != 0) {
                    // Load from raw resources
                    Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + resourceId);
                    Log.d("VideoPlayer", "Setting video URI: " + videoUri.toString());
                    
                    // Set up video player
                    videoPlayer.setVideoURI(videoUri);
                    videoPlayer.requestFocus();
                    
                    // Set up video player listeners
                    videoPlayer.setOnPreparedListener(mp -> {
                        Log.d("VideoPlayer", "Video prepared successfully");
                        mp.setLooping(true);
                        fabPlayVideo.setImageResource(android.R.drawable.ic_media_play);
                        
                        fabPlayVideo.setOnClickListener(v -> {
                            if (isVideoPlaying) {
                                videoPlayer.pause();
                                fabPlayVideo.setImageResource(android.R.drawable.ic_media_play);
                            } else {
                                videoPlayer.start();
                                fabPlayVideo.setImageResource(android.R.drawable.ic_media_pause);
                            }
                            isVideoPlaying = !isVideoPlaying;
                        });
                    });

                    videoPlayer.setOnErrorListener((mp, what, extra) -> {
                        Log.e("VideoPlayer", "Error playing video - what: " + what + ", extra: " + extra);
                        Toast.makeText(this, "Error: Could not play video (Error code: " + what + ")", Toast.LENGTH_SHORT).show();
                        videoPlayer.setVisibility(View.GONE);
                        fabPlayVideo.setVisibility(View.GONE);
                        return true;
                    });

                    videoPlayer.setOnCompletionListener(mp -> {
                        Log.d("VideoPlayer", "Video playback completed");
                        isVideoPlaying = false;
                        fabPlayVideo.setImageResource(android.R.drawable.ic_media_play);
                    });
                } else {
                    Log.e("VideoPlayer", "Resource not found for: " + videoUrl);
                    Toast.makeText(this, "Error: Video file not found (" + videoUrl + ")", Toast.LENGTH_SHORT).show();
                    videoPlayer.setVisibility(View.GONE);
                    fabPlayVideo.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("VideoPlayer", "Error setting up video player: " + e.getMessage());
                Toast.makeText(this, "Error: Could not initialize video player - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                videoPlayer.setVisibility(View.GONE);
                fabPlayVideo.setVisibility(View.GONE);
            }
        } else {
            Log.d("VideoPlayer", "Video URL is null or empty");
            videoPlayer.setVisibility(View.GONE);
            fabPlayVideo.setVisibility(View.GONE);
        }
    }

    private void loadTraditionData(int traditionId) {
        // Load tradition data based on category ID
        switch (categoryId) {
            case 1: // Traditional Clothing
                if (traditionId == 1) {
                    tradition = new Tradition(
                        1,
                        "Karakou",
                        "The Karakou is a traditional Algerian dress known for its intricate embroidery and rich cultural significance. It is typically worn during special occasions and weddings.",
                        "The Karakou has its origins in the Ottoman period and has evolved over centuries. It represents the fusion of Algerian and Ottoman cultures, with its distinctive embroidery patterns telling stories of heritage and craftsmanship.",
                        null,
                        "karakou",
                        null,
                        "Algiers, Constantine, Oran",
                        "Weddings and Special Occasions",
                        new String[]{
                            "Fine silk fabric",
                            "Gold and silver threads",
                            "Traditional embroidery patterns",
                            "Velvet accents",
                            "Pearl and bead decorations"
                        },
                        new String[]{
                            "Selection of high-quality silk fabric",
                            "Traditional pattern design",
                            "Hand embroidery with gold threads",
                            "Assembly of dress components",
                            "Final embellishments and adjustments"
                        },
                        new String[]{
                            "Haik",
                            "Burnous",
                            "Caftan"
                        }
                    );
                } else if (traditionId == 2) {
                    tradition = new Tradition(
                        2,
                        "Burnous",
                        "The Burnous is a traditional woolen cloak worn by Algerian men, especially in rural areas. It is known for its warmth and distinctive hood.",
                        "The Burnous has been worn in North Africa for centuries, with its design influenced by both Berber and Arab cultures. It was traditionally made from camel or sheep wool.",
                        null,
                        "burnous",
                        null,
                        "Kabylie, Aurès, Sahara",
                        "Winter and Cold Seasons",
                        new String[]{
                            "Natural wool",
                            "Traditional weaving tools",
                            "Natural dyes",
                            "Handmade buttons"
                        },
                        new String[]{
                            "Wool collection and preparation",
                            "Traditional weaving process",
                            "Natural dyeing",
                            "Assembly and finishing",
                            "Decoration and embellishment"
                        },
                        new String[]{
                            "Gandoura",
                            "Djellaba",
                            "Seroual"
                        }
                    );
                }
                break;
            case 2: // Festivals & Celebrations
                if (traditionId == 3) {
                    tradition = new Tradition(
                        3,
                        "Eid al-Fitr",
                        "Eid al-Fitr is a major Islamic festival marking the end of Ramadan. In Algeria, it is celebrated with special prayers, family gatherings, and traditional foods.",
                        "Eid al-Fitr has been celebrated in Algeria for centuries, with traditions passed down through generations. It represents the culmination of a month of fasting and spiritual reflection.",
                        null,
                        "eid",
                        null,
                        "Throughout Algeria",
                        "End of Ramadan",
                        new String[]{
                            "Traditional sweets",
                            "Dates",
                            "Coffee",
                            "Mint tea"
                        },
                        new String[]{
                            "Early morning prayers",
                            "Family visits",
                            "Traditional meal preparation",
                            "Gift exchange",
                            "Community celebrations"
                        },
                        new String[]{
                            "Ramadan",
                            "Eid al-Adha",
                            "Mawlid"
                        }
                    );
                } else if (traditionId == 4) {
                    tradition = new Tradition(
                        4,
                        "Yennayer",
                        "Yennayer is the Amazigh (Berber) New Year celebration, marking the beginning of the agricultural year. It is celebrated with traditional foods and cultural events.",
                        "Yennayer has been celebrated for over 2000 years in North Africa. It represents the Amazigh calendar and agricultural traditions.",
                        null,
                        "yennayer",
                        null,
                        "Kabylie, Aurès, M'zab",
                        "January 12th",
                        new String[]{
                            "Couscous",
                            "Chicken",
                            "Seven vegetables",
                            "Traditional bread",
                            "Dried fruits"
                        },
                        new String[]{
                            "Preparation of traditional dishes",
                            "Family gathering",
                            "Cultural performances",
                            "Traditional games",
                            "Storytelling"
                        },
                        new String[]{
                            "Imensi n Yennayer",
                            "Tafsut",
                            "Tiwizi"
                        }
                    );
                }
                break;
            case 3: // Food & Cuisine
                if (traditionId == 5) {
                    tradition = new Tradition(
                        5,
                        "Couscous",
                        "Couscous is the national dish of Algeria, made from steamed semolina grains served with vegetables and meat.",
                        "Couscous has been a staple in Algerian cuisine for centuries, with its preparation methods passed down through generations. It represents the agricultural heritage of the region.",
                        null,
                        "couscous",
                        null,
                        "Throughout Algeria",
                        "All year, especially Fridays",
                        new String[]{
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
                        new String[]{
                            "Prepare the semolina",
                            "Steam the couscous",
                            "Cook the meat and vegetables",
                            "Prepare the sauce",
                            "Assemble and serve"
                        },
                        new String[]{
                            "Tajine",
                            "Chorba",
                            "Merguez"
                        }
                    );
                }
                break;
        }

        if (tradition == null) {
            Toast.makeText(this, "Error: Tradition not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Update UI with tradition data
        traditionTitle.setText(tradition.getTitle());
        traditionDescription.setText(tradition.getDescription());

        // Setup region and season
        if (tradition.getRegion() != null && !tradition.getRegion().isEmpty()) {
            regionCard.setVisibility(View.VISIBLE);
            regionText.setText(tradition.getRegion());
        }

        if (tradition.getSeason() != null && !tradition.getSeason().isEmpty()) {
            seasonCard.setVisibility(View.VISIBLE);
            seasonText.setText(tradition.getSeason());
        }

        // Setup ingredients
        if (tradition.getIngredients() != null && tradition.getIngredients().length > 0) {
            ingredientsCard.setVisibility(View.VISIBLE);
            ingredientsAdapter.setIngredients(tradition.getIngredients());
        }

        // Setup steps
        if (tradition.getSteps() != null && tradition.getSteps().length > 0) {
            stepsCard.setVisibility(View.VISIBLE);
            stepsAdapter.setSteps(tradition.getSteps());
        }

        // Setup related traditions
        if (tradition.getRelatedTraditions() != null && tradition.getRelatedTraditions().length > 0) {
            relatedTraditionsCard.setVisibility(View.VISIBLE);
            relatedTraditionsAdapter.setRelatedTraditions(tradition.getRelatedTraditions());
        }

        // Setup video player
        setupVideoPlayer(tradition.getVideoUrl());

        // Setup historical background
        if (tradition.getHistoricalBackground() != null && !tradition.getHistoricalBackground().isEmpty()) {
            historicalCard.setVisibility(View.VISIBLE);
            historicalBackground.setText(tradition.getHistoricalBackground());
        }

        // Setup audio player if audio URL is available
        if (tradition.getAudioUrl() != null && !tradition.getAudioUrl().isEmpty()) {
            audioCard.setVisibility(View.VISIBLE);
            setupAudioPlayer(tradition.getAudioUrl());
        }
    }

    private void setupAudioPlayer(String audioUrl) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepareAsync();
            
            mediaPlayer.setOnPreparedListener(mp -> {
                audioSeekBar.setMax(mp.getDuration());
                btnPlayAudio.setOnClickListener(v -> {
                    if (mp.isPlaying()) {
                        mp.pause();
                        btnPlayAudio.setImageResource(android.R.drawable.ic_media_play);
                    } else {
                        mp.start();
                        btnPlayAudio.setImageResource(android.R.drawable.ic_media_pause);
                    }
                });
            });

            mediaPlayer.setOnCompletionListener(mp -> {
                btnPlayAudio.setImageResource(android.R.drawable.ic_media_play);
                audioSeekBar.setProgress(0);
            });

            // Update seekbar position
            new Thread(() -> {
                while (mediaPlayer != null) {
                    try {
                        if (mediaPlayer.isPlaying()) {
                            final int currentPosition = mediaPlayer.getCurrentPosition();
                            runOnUiThread(() -> audioSeekBar.setProgress(currentPosition));
                        }
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            audioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser && mediaPlayer != null) {
                        mediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error setting up audio player", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupClickListeners() {
        fabShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, tradition.getTitle());
            shareIntent.putExtra(Intent.EXTRA_TEXT, 
                tradition.getTitle() + "\n\n" + tradition.getDescription());
            startActivity(Intent.createChooser(shareIntent, getString(R.string.msg_share_tradition)));
        });
    }

    private void setupComments() {
        // Initialize comments list and adapter
        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(comments);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsRecyclerView.setAdapter(commentAdapter);

        // Show comments card
        commentsCard.setVisibility(View.VISIBLE);

        // Setup submit button click listener
        btnSubmitComment.setOnClickListener(v -> {
            String commentText = commentInput.getText().toString().trim();
            if (!commentText.isEmpty()) {
                // TODO: Replace with actual user ID and name from authentication
                String userId = "user123";
                String userName = "Anonymous User";
                
                Comment comment = new Comment(
                    UUID.randomUUID().toString(),
                    String.valueOf(tradition.getId()),
                    userId,
                    userName,
                    commentText
                );
                
                // TODO: Save comment to database
                comments.add(0, comment);
                commentAdapter.notifyItemInserted(0);
                commentInput.setText("");
                
                // Scroll to the new comment
                commentsRecyclerView.smoothScrollToPosition(0);
            }
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
        if (videoPlayer != null) {
            videoPlayer.pause();
            isVideoPlaying = false;
            fabPlayVideo.setImageResource(android.R.drawable.ic_media_play);
        }
    }
} 