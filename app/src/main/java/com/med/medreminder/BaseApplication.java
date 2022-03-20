package com.med.medreminder;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class BaseApplication extends Application {

    public static final String RESCHEDULE_CHANNEL = "reschedule";
    public static final String REFILL_CHANNEL = "refill";


    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationsChannels();

    }

    private void createNotificationsChannels(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel rescheduleChannel = new NotificationChannel(
                    RESCHEDULE_CHANNEL,
                    "reschedule",
                    NotificationManager.IMPORTANCE_HIGH
            );
            rescheduleChannel.setDescription("Notification for reschedule medication time");

            NotificationChannel refillChannel = new NotificationChannel(
                    REFILL_CHANNEL,
                    "refill",
                    NotificationManager.IMPORTANCE_HIGH
            );
            refillChannel.setDescription("Notification for reschedule medication time");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(rescheduleChannel);
            manager.createNotificationChannel(refillChannel);

        }
    }

}
