package com.example.unisammelapp;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.unisammelapp.SammelApp.CHANNEL_1_ID;
import static com.example.unisammelapp.SammelApp.CHANNEL_2_ID;
import static com.example.unisammelapp.SammelApp.CHANNEL_3_ID;

public class MainActivity extends AppCompatActivity {

    private EditText inputBait;
    private Button setBaitBtn;
    private Button ToPetListActivity;
    private TextView reservesText;
    private Integer inputBaitInt;
    public static Integer reservesInt = 5000;

    public String newPetBeingAdded = "fox";


    //test
    private NotificationManagerCompat notificationManager;
    private Button notifBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setupButtons();
        loadReserves();
        reservesText.setText(reservesInt.toString());


        //test
        notificationManager = NotificationManagerCompat.from(this);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        saveReserves();
        loadReserves();
        reservesText.setText(reservesInt.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveReserves();
        loadReserves();
        reservesText.setText(reservesInt.toString());
    }

    private void findViews(){
        inputBait = findViewById(R.id.baitInputId);
        setBaitBtn = findViewById(R.id.baitBtnId);
        reservesText = findViewById(R.id.reservesNumId);
        ToPetListActivity = findViewById(R.id.btnPetListActivity);

        //test
        notifBtn = findViewById(R.id.button);
    }

    private void setupButtons(){
        setBaitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reservesInt < 0){
                    reservesInt = 5000;
                    saveReserves();
                    loadReserves();
                    reservesText.setText(reservesInt.toString());

                }
                if (!inputBait.getText().toString().equals("")){
                    inputBaitInt = Integer.parseInt(inputBait.getText().toString());
                    if (v != null && inputBaitInt<reservesInt && inputBaitInt > 0 && reservesText != null){
                        setBait();
                        Toast.makeText(getApplicationContext(),"Köder (" + inputBaitInt + ") wurde gesetzt!", Toast.LENGTH_LONG).show();
                        inputBait.setText("");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Kann nicht gesetzt werden", Toast.LENGTH_LONG).show();
                        inputBait.setText("");
                    }
                }
            }
        });
        ToPetListActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PetListActivity.class);
                startActivity(i);
            }
        });
    }

    private void setBait(){
        reservesInt = reservesInt - inputBaitInt;
        reservesText.setText(reservesInt.toString());
        saveReserves();
        loadReserves();
    }

    //TEST
    //https://codinginflow.com/tutorials/android/notifications-notification-channels/part-1-notification-channels
    //https://stackoverflow.com/questions/15809399/android-notification-sound
    //https://stackoverflow.com/questions/15120821/remove-notification-after-clicking

    public void sendNotif1(View v){

        Intent channel1Intent = new Intent(this, NewPetActivity.class);
        channel1Intent.putExtra("INTENT_SPECIES", newPetBeingAdded);
        channel1Intent.setAction(newPetBeingAdded);

        PendingIntent channel1PendingIntent = PendingIntent.getActivity(this, 0, channel1Intent, 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification newPetFoundNotif = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Ein neues Tier ist aufgetaucht!")
                .setContentText("Gib ihm einen Namen")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(channel1PendingIntent)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(1, newPetFoundNotif);



        /*Intent channel2Intent = new Intent(this, PetListActivity.class);
        PendingIntent channel2PendingIntent = PendingIntent.getActivity(this, 0, channel2Intent, 0);

        Notification petGrowthNotif = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.test_tier)
                .setContentTitle("Ein neues Tier ist gewachsen!")
                .setContentText("XY")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(channel2PendingIntent)
                .build();
        notificationManager.notify(1, petGrowthNotif);



        Intent channel3Intent = new Intent(this, PetListActivity.class);
        PendingIntent channel3PendingIntent = PendingIntent.getActivity(this, 0, channel3Intent, 0);

        Notification petDeathNotif = new NotificationCompat.Builder(this, CHANNEL_3_ID)
                .setSmallIcon(R.drawable.test_tier)
                .setContentTitle("Ein Tier ist gerade verhungert")
                .setContentText("XY")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(channel3PendingIntent)
                .build();
        notificationManager.notify(1, petDeathNotif);*/

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
