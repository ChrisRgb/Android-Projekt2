package com.example.unisammelapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class NewPetActivity extends AppCompatActivity {

    private EditText giveNewName;
    private Button giveNameBtn;
    public static final String PETNAME = "com.example.unisammelapp.petname";
    public static final String PETSPECIES = "com.example.unisammelapp.petspecies";

    private ImageView newPetImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_pet_activity);

        findView();
        setupImage();
        setupButton();
        setupButton();

    }

    private void findView(){
        giveNewName = findViewById(R.id.newPetGiveNameId);
        giveNameBtn = findViewById(R.id.giveNameBtnId);
        newPetImage = findViewById(R.id.newPetImage);
    }

    private void setupImage(){

        Bundle extras = getIntent().getExtras();
        String petSpecies = extras.getString("INTENT_SPECIES");
        switch (petSpecies){
            case "fox": newPetImage.setImageResource(R.drawable.android_s3_g1);
                break;
            case "snake": newPetImage.setImageResource(R.drawable.android_s2_g1);
                break;
            case "mouse": newPetImage.setImageResource(R.drawable.android_s1_g1);
                break;
            default: newPetImage.setImageResource(R.drawable.test_tier);
        }
    }

    private void setupButton(){
        giveNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                String petSpecies = extras.getString("INTENT_SPECIES");

                String newName = giveNewName.getText().toString();
                String newSpecies = petSpecies;

                Intent i = new Intent(NewPetActivity.this, PetListActivity.class);
                i.putExtra(PETNAME, newName);
                i.putExtra(PETSPECIES, newSpecies);
                startActivity(i);
                finish();
            }
        });
    }
}
