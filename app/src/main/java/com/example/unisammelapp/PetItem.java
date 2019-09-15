package com.example.unisammelapp;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class PetItem implements Parcelable {

    private String petName;
    private String petSpecies;
    private Integer petHunger;
    private Integer petGrowthLevel;


    public PetItem(String petName, String petSpecies, Integer petHunger, Integer petGrowthLevel) {
        this.petName = petName;
        this.petSpecies = petSpecies;
        this.petHunger = petHunger;
        this.petGrowthLevel = petGrowthLevel;

    }

    protected PetItem(Parcel in) {
        petName = in.readString();
        petSpecies = in.readString();
        if (in.readByte() == 0) {
            petHunger = null;
        } else {
            petHunger = in.readInt();
        }
        if (in.readByte() == 0) {
            petGrowthLevel = null;
        } else {
            petGrowthLevel = in.readInt();
        }
    }

    public static final Creator<PetItem> CREATOR = new Creator<PetItem>() {
        @Override
        public PetItem createFromParcel(Parcel in) {
            return new PetItem(in);
        }

        @Override
        public PetItem[] newArray(int size) {
            return new PetItem[size];
        }
    };

    public String getPetName() {
        return petName;
    }

    public void setPetName(@NonNull String petName) {
        this.petName = petName;
    }

    public String getPetSpecies() {
        return petSpecies;
    }

    public void setPetSpecies(String petSpecies) {
        this.petSpecies = petSpecies;
    }

    public Integer getPetHunger() {
        return petHunger;
    }

    public void setPetHunger(Integer petHunger) {
        this.petHunger = petHunger;
    }

    public Integer getPetGrowthLevel() {
        return petGrowthLevel;
    }

    public void setPetGrowthLevel(Integer petGrowthLevel) {
        this.petGrowthLevel = petGrowthLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(petName);
        parcel.writeString(petSpecies);
        if (petHunger == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(petHunger);
        }
        if (petGrowthLevel == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(petGrowthLevel);
        }
    }
}