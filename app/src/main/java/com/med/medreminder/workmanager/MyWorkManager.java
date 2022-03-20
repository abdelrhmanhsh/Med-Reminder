package com.med.medreminder.workmanager;

import static com.med.medreminder.ui.BaseApplication.MEDREMINDER_CHANNEL;
import static com.med.medreminder.ui.BaseApplication.RESCHEDULE_CHANNEL;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.med.medreminder.R;
import com.med.medreminder.ui.MainActivity;
import com.med.medreminder.ui.homepage.view.HomeActivity;

public class MyWorkManager extends Worker {

    public static final String TAG = "WorkManager";
    public static final String IMAGE_RESOURCE = "IMAGE_RESOURCE";
    public static final String MED_NAME = "MED_NAME";

    private static NotificationManagerCompat notificationManagerCompat;
    Context context;

    public MyWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {

          Log.d(TAG, "doWork: 41");
        Data inputData = getInputData();
        int imageSource = inputData.getInt(IMAGE_RESOURCE, -1);
        String medName = inputData.getString(MED_NAME);
        Log.d(TAG, "doWork: 45"+medName);
        Log.d(TAG, "doWork: 45"+imageSource);


        //sendOnReschedule(context, imageSource, medName);
        //sendOnMedicalReminder(context);
        notification(context);
        return Result.success();
    }

    public static void sendOnReschedule(Context context, int imageSource, String medName){

        notificationManagerCompat = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, RESCHEDULE_CHANNEL)
                .setSmallIcon(imageSource)
                .setContentTitle(context.getString(R.string.resched_notification_title))
                .setContentText(context.getString(R.string.resched_notification_desc) + " " + medName)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .setVibrate()
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    public static void sendOnMedicalReminder(Context context) {
        Log.d(TAG, "sendOnMedicalReminder: "+ "70");
        notificationManagerCompat = NotificationManagerCompat.from(context);
        Intent activityIntent = new Intent(context, HomeActivity.class);  //but we cannot pass an intent to notification, pending intent instead to execute our intent
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, activityIntent, 0);                             //request code related to cancel or update this pending intent
        //flags: if we create another pending intent it should update this value

        Notification notification = new NotificationCompat.Builder(context, MEDREMINDER_CHANNEL)
                .setSmallIcon(R.drawable.ic_pill)
                .setContentTitle("Medication Reminder")
                .setContentText("It's time to take your medicine")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                // .setColor(getResources().getColor(R.color.black))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)   //if we tap on notification it disappear
                .setOnlyAlertOnce(true)  //it makes sound and popup for the first time only but when updated will not
                .addAction(R.mipmap.ic_launcher, "Take", pendingIntent)  //we can add up to 3 action buttons
                .build();

        notificationManagerCompat.notify(1, notification);    //1 means this notification if i want to cancel or repeat it will use this id

        Log.d(TAG, "sendOnMedicalReminder: "+ "92");

    }

    public static void notification(Context context){
        Log.d(TAG, "sendOnMedicalReminder: "+ "103");

        int reqCode = 1;
        Intent intent = new Intent(context, HomeActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT);
        String CHANNEL_ID = "channel_name";// The id of the channel.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("title")
                .setContentText("message")
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .addAction(R.mipmap.ic_launcher, "Take", pendingIntent)  //we can add up to 3 action buttons
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(reqCode, notificationBuilder.build()); // 0 is the request code, it should be unique id
        Log.d(TAG, "sendOnMedicalReminder: "+ "124");

        Log.d("showNotification", "showNotification: " + reqCode);

    }
    }


