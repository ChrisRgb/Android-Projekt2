package com.example.unisammelapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class SammelApp extends Application {

    public static final String CHANNEL_1_ID = "NewPetFound";
    public static final String CHANNEL_2_ID = "PetHasGrown";
    public static final String CHANNEL_3_ID = "PatHasDied";

    @Override
    public void onCreate() {
        super.onCreate();

        initNotifications();
    }

    private void initNotifications(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "PetCaptured",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel1.setDescription("Wird gesendet sobald ein Tier gefunden wird");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "GrowthUpgrade",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel2.setDescription("Wird gesendet sobald ein Tier wächst");

            NotificationChannel channel3 = new NotificationChannel(
                    CHANNEL_3_ID,
                    "PetDeathEvent",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel3.setDescription("Wird gesendet sobald ein Tier stirbt");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
            manager.createNotificationChannel(channel3);
        }
    }
}
