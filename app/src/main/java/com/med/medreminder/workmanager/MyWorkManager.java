package com.med.medreminder.workmanager;

import static com.med.medreminder.BaseApplication.REFILL_CHANNEL;
import static com.med.medreminder.BaseApplication.RESCHEDULE_CHANNEL;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.med.medreminder.R;

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

        Data inputData = getInputData();
        int imageSource = inputData.getInt(IMAGE_RESOURCE, -1);
        String medName = inputData.getString(MED_NAME);

        sendOnReschedule(context, imageSource, medName);
        sendOnRefill(context,imageSource,medName);
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

    public void sendOnRefill(Context context,int imageSource, String medName){

        String skip = "Skip";
        PendingIntent intent = WorkManager.getInstance(context)
                .createCancelPendingIntent(getId());

        //snooze --> Snooze for extra 5 mins
        //refill --> refill

        notificationManagerCompat = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, REFILL_CHANNEL)
                .setSmallIcon(imageSource)
                .setContentTitle("Refill")
                .setContentText(medName)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .addAction(R.drawable.ic_delete,skip, intent)
                .addAction(R.drawable.ic_snooze,"Snooze", intent)
                .addAction(R.drawable.ic_edit,"Refill", intent)
                .build();

        notificationManagerCompat.notify(2, notification);
    }

}
