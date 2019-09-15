package com.example.unisammelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PetAdapter extends ArrayAdapter<PetItem> {

    private ArrayList<PetItem> petItemArrayList;
    private Context context;

    public PetAdapter(Context context, ArrayList<PetItem> petItemArrayList) {
        super(context, R.layout.pet_list_layout, petItemArrayList);

        this.petItemArrayList = petItemArrayList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.pet_list_layout, null);
        }

        PetItem petItem = petItemArrayList.get(position);


        if(petItem != null){
            TextView tierNameText = v.findViewById(R.id.petName_textView);
            tierNameText.setText(petItem.getPetName());
            ImageView petImage = v.findViewById(R.id.list_imageView);
            if(petItem.getPetSpecies().equals("mouse") && petItem.getPetGrowthLevel() == 0) {
                petImage.setImageResource(R.drawable.android_s1_g1);
            }
            if(petItem.getPetSpecies().equals("mouse") && petItem.getPetGrowthLevel() == 1) {
                petImage.setImageResource(R.drawable.android_s1_g2);
            }
            if(petItem.getPetSpecies().equals("snake") && petItem.getPetGrowthLevel() == 0) {
                petImage.setImageResource(R.drawable.android_s2_g1);
            }
            if(petItem.getPetSpecies().equals("snake") && petItem.getPetGrowthLevel() == 1) {
                petImage.setImageResource(R.drawable.android_s2_g2);
            }
            if(petItem.getPetSpecies().equals("fox") && petItem.getPetGrowthLevel() == 0) {
                petImage.setImageResource(R.drawable.android_s3_g1);
            }
            if(petItem.getPetSpecies().equals("fox") && petItem.getPetGrowthLevel() == 1) {
                petImage.setImageResource(R.drawable.android_s3_g2);
            }

        }
        return v;
    }
}
