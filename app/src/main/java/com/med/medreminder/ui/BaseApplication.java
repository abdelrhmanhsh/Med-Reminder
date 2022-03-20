package com.med.medreminder.ui;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

public class BaseApplication extends Application {

    public static final String RESCHEDULE_CHANNEL = "reschedul";
    public static final String MEDREMINDER_CHANNEL = "medReminder";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "onCreate: " + "Work Manager is working........");

        createNotificationsChannels();

    }

    private void createNotificationsChannels(){
        Log.d("TAG", "createNotificationsChannels: " + "inside notification------------>24");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Log.d("TAG", "createNotificationsChannels: " + "inside notification------------>27");

            NotificationChannel rescheduleChannel = new NotificationChannel(
                    RESCHEDULE_CHANNEL,
                    "reschedule",
                    NotificationManager.IMPORTANCE_HIGH
            );
            rescheduleChannel.setDescription("Notification for reschedule medication time");
            Log.d("TAG", "createNotificationsChannels: " + "inside notification------------>35");

            NotificationChannel notificationChannel2 = new NotificationChannel(
                    MEDREMINDER_CHANNEL,
                    "med reminder",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel2.setDescription("Medical reminder");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(rescheduleChannel);
            Log.d("TAG", "createNotificationsChannels: " + "inside notification------------>46");


        }
    }

}
