package com.med.medreminder.workmanager;

import static com.med.medreminder.BaseApplication.RESCHEDULE_CHANNEL;

import android.app.Notification;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.med.medreminder.R;

public class MyWorkManager extends Worker {

    public static final String TAG = "WorkManager";
    public static final String IMAGE_SOURCE = "IMAGE_SOURCE";
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

        Data inputData = getInputData();
        int imageSource = inputData.getInt(IMAGE_SOURCE, -1);
        String medName = inputData.getString(MED_NAME);

        sendOnChannel1(context, imageSource, medName);
        return Result.success();
    }

    public static void sendOnChannel1(Context context, int imageSource, String medName){

        notificationManagerCompat = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, RESCHEDULE_CHANNEL)
                .setSmallIcon(imageSource)
                .setContentTitle("Time to take your mid")
                .setContentText("It's time to take your " + medName)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .setVibrate()
                .build();

        notificationManagerCompat.notify(1, notification);
    }

}
