package com.example.unisammelapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PetListActivity extends AppCompatActivity {

    private PetAdapter adapter;
    private ListView listView;
    private ArrayList<PetItem>petItemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petlist);

        petItemArrayList = new ArrayList<>();

        loadPets();
        findView();
        initListView();
        deleteMultiples();
    }

    public void findView(){
        listView = findViewById(R.id.petListView);
    }


    public void savePets(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(petItemArrayList);
        editor.putString("pet_list", json);
        editor.apply();
        loadPets();
    }

    public void loadPets(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("pet_list", null);
        Type type = new TypeToken<ArrayList<PetItem>>() {}.getType();
        if (gson.fromJson(json, type) != null){
            petItemArrayList = gson.fromJson(json, type);

        }
    }

    private void initListView() {
        Intent intent = getIntent();
        String addPetNameFromIntent = intent.getStringExtra(NewPetActivity.PETNAME);
        String addPetSpeciesFromIntent = intent.getStringExtra(NewPetActivity.PETSPECIES);

        if (addPetNameFromIntent != null){

            PetItem addedPetNameFromIntent = new PetItem(addPetNameFromIntent, addPetSpeciesFromIntent, 500, 0);
            petItemArrayList.add(addedPetNameFromIntent);
            savePets();
        }

        Intent j = getIntent();
        String updatePetName = j.getStringExtra("NAME");
        String updatePetSpecies = j.getStringExtra("SPECIES");
        Integer updatePetHunger = j.getIntExtra("HUNGER", 0);
        Integer updatePetGrowth = j.getIntExtra("GROWTH", 0);
        Integer i;

        if (updatePetName != null){

            PetItem newPetUpdate = new PetItem(updatePetName, updatePetSpecies, updatePetHunger, updatePetGrowth);
            petItemArrayList.add(newPetUpdate);
            savePets();

            for(i=1; i<petItemArrayList.size(); i++) {
                loadPets();
                savePets();
                PetItem petItem = petItemArrayList.get(i);
                if (petItem.getPetName().equals(updatePetName)) {
                    petItemArrayList.remove(petItem);
                }
            }
            loadPets();
            savePets();
            finishAct();

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PetListActivity.this, PetStatsActivity.class);
                intent.putExtra("petItemToStatsActivity", adapter.getItem(i));
                startActivity(intent);
            }
        });


        //remove

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                petItemArrayList.clear();
                savePets();
                loadPets();
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        adapter = new PetAdapter(this, petItemArrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void finishAct(){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void deleteMultiples() {
        Integer i;
        Integer k;
        for (i = 0; i < petItemArrayList.size(); i++) {
            for (k = 0; k < petItemArrayList.size() - 1; k++) {
                PetItem petItem = petItemArrayList.get(i);
                PetItem petItemj = petItemArrayList.get(k);
                if (petItem.getPetName().equals(petItemj.getPetName())) {
                    petItemArrayList.remove(petItem);
                }
            }
        }
        savePets();
    }

}
