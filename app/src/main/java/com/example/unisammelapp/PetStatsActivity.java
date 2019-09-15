package com.example.unisammelapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class PetStatsActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView speciesText;
    private TextView hungerText;
    private TextView growthText;
    private ImageView statsImage;
    private Button feedBtn;
    private EditText feedInput;
    private Integer feedInputInt;
    private Integer hungerInt;
    private Integer updateHunger;
    private Integer reservesInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petstats);

        findViews();
        setupButton();

        Intent intent = getIntent();
        PetItem petStatItem = intent.getParcelableExtra("petItemToStatsActivity");

        reservesInt = MainActivity.reservesInt;

        nameText.setText(petStatItem.getPetName());
        speciesText.setText(petStatItem.getPetSpecies());
        hungerText.setText(petStatItem.getPetHunger() + "");
        growthText.setText(petStatItem.getPetGrowthLevel() + "");

        loadImages();


    }

    private void loadImages(){
        Intent intent = getIntent();
        PetItem petStatItem = intent.getParcelableExtra("petItemToStatsActivity");
        if(petStatItem.getPetSpecies().equals("mouse") && petStatItem.getPetGrowthLevel() == 0) {
            statsImage.setImageResource(R.drawable.android_s1_g1);
            speciesText.setText(("Maus"));
        }
        if(petStatItem.getPetSpecies().equals("mouse") && petStatItem.getPetGrowthLevel() == 1) {
            statsImage.setImageResource(R.drawable.android_s1_g2);
            speciesText.setText(("Maus"));
        }
        if(petStatItem.getPetSpecies().equals("snake") && petStatItem.getPetGrowthLevel() == 0) {
            statsImage.setImageResource(R.drawable.android_s2_g1);
            speciesText.setText(("Schlange"));
        }
        if(petStatItem.getPetSpecies().equals("snake") && petStatItem.getPetGrowthLevel() == 1) {
            statsImage.setImageResource(R.drawable.android_s2_g2);
            speciesText.setText(("Schlange"));
        }
        if(petStatItem.getPetSpecies().equals("fox") && petStatItem.getPetGrowthLevel() == 0) {
            statsImage.setImageResource(R.drawable.android_s3_g1);
            speciesText.setText(("Fuchs"));
        }
        if(petStatItem.getPetSpecies().equals("fox") && petStatItem.getPetGrowthLevel() == 1) {
            statsImage.setImageResource(R.drawable.android_s3_g2);
            speciesText.setText(("Fuchs"));
        }
    }

    private void findViews(){
        nameText = findViewById(R.id.statsNameID);
        speciesText = findViewById(R.id.statsSpeciesID);
        hungerText = findViewById(R.id.statsHungerID);
        growthText = findViewById(R.id.statsGrowthID);
        statsImage = findViewById(R.id.petStatImageID);
        feedBtn = findViewById(R.id.feedPetBtn);
        feedInput = findViewById(R.id.feedPetInput);
    }

    private void setupButton(){
        feedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!feedInput.getText().toString().equals("")) {
                    feedInputInt = Integer.parseInt(feedInput.getText().toString());

                    if (v != null && feedInputInt > 0 /*&& feedInputInt < reservesInt*/) {
                        feedPet();
                        Toast.makeText(getApplicationContext(), "Das Tier wurde gefüttert! " + feedInputInt, Toast.LENGTH_LONG).show();
                        feedInput.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "Damit kann das Tier nicht gefüttert werden", Toast.LENGTH_LONG).show();
                        feedInput.setText("");
                    }
                }
                finish();
            }
        });
    }

    private void feedPet(){
        Intent intent = getIntent();
        PetItem petStatItem = intent.getParcelableExtra("petItemToStatsActivity");
        feedInputInt = Integer.parseInt(feedInput.getText().toString());
        hungerInt = Integer.parseInt(hungerText.getText().toString());

        updateHunger = feedInputInt + hungerInt;
        Integer growthInt = 0;
        if (updateHunger>=1000){
            petStatItem.setPetGrowthLevel(1);
            growthText.setText("1");
            growthInt = 1;
            loadImages();

        }
        hungerText.setText((updateHunger).toString());
        petStatItem.setPetHunger(updateHunger);

        MainActivity.reservesInt = reservesInt - feedInputInt;

        saveReserves();
        loadReserves();
        Intent i = new Intent(PetStatsActivity.this, PetListActivity.class);
        i.putExtra("NAME", petStatItem.getPetName());
        i.putExtra("SPECIES", petStatItem.getPetSpecies());
        i.putExtra("HUNGER", updateHunger);
        i.putExtra("GROWTH", growthInt);
        startActivity(i);

    }

    private void saveReserves() {

        SharedPreferences sp = getSharedPreferences("reserves", MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("foodReserves", reservesInt);
        editor.commit();
    }

    private void loadReserves(){
        SharedPreferences sp = getSharedPreferences("reserves", MainActivity.MODE_PRIVATE);
        reservesInt = sp.getInt("foodReserves", -1);
    }
}


