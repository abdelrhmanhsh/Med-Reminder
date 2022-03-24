package com.med.medreminder.workmanager;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

import static com.med.medreminder.BaseApplication.MEDREMINDER_CHANNEL;
import static com.med.medreminder.BaseApplication.RESCHEDULE_CHANNEL;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.med.medreminder.R;
import com.med.medreminder.broadcasts.DialogReceiver;
import com.med.medreminder.broadcasts.RefillDialogReceiver;
import com.med.medreminder.broadcasts.SkipReceiver;
import com.med.medreminder.broadcasts.SnoozeReceiver;
import com.med.medreminder.broadcasts.TakeReceiver;
import com.med.medreminder.ui.homepage.view.HomeActivity;
import com.med.medreminder.ui.dialogs.NotificationDialogActivity;
import com.med.medreminder.utils.Constants;

public class MyWorkManager extends Worker {

    public static final String TAG = "WorkManager";

    private static NotificationManagerCompat notificationManagerCompat;
    Context context;
    public static RefillDialogReceiver dialogReceiver;

    public MyWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        dialogReceiver = new RefillDialogReceiver();
    }

    @NonNull
    @Override
    public Result doWork() {

//          Log.d(TAG, "doWork: 41");
//        Data inputData = getInputData();
//        int imageSource = inputData.getInt(IMAGE_RESOURCE, -1);
//        String medName = inputData.getString(MED_NAME);
//        Log.d(TAG, "doWork: 45"+medName);
//        Log.d(TAG, "doWork: 45"+imageSource);
//
//
//        //sendOnReschedule(context, imageSource, medName);
//        //sendOnMedicalReminder(context);
//        notification(context);
//        return Result.success();

        Data inputData = getInputData();
        int imageSource = inputData.getInt(Constants.IMAGE_RESOURCE, -1);
        String medName = inputData.getString(Constants.MED_NAME);
        long id = inputData.getLong(Constants.MED_ID, -1);
        int amountLeft = inputData.getInt(Constants.AMOUNT_LEFT, -1);
        String medTimes = inputData.getString(Constants.MED_TIMES);
        String medStrength = inputData.getString(Constants.MED_STRENGTH);

        Log.i(TAG, "doWork: " + imageSource);
        Log.i(TAG, "doWork: " + medName);
        Log.i(TAG, "doWork: " + id);
        Log.i(TAG, "doWork: " + amountLeft);
        Log.i(TAG, "doWork: " + medTimes);
        Log.i(TAG, "doWork: " + medStrength);

//        sendOnReschedule(context, imageSource, medName);
//        showDialog();
//        showNotificationDialog(context);



        sendOnReschedule(context, imageSource, medName, id, amountLeft);
        sendNotificationDialog(imageSource, medName, id, medTimes, medStrength, amountLeft);
//        sendNotification2Dialog();




//        dialogReceiver = new DialogReceiver();
//        context.startActivity(new Intent(context, NotificationDialogActivity.class));
//        Intent intent = new Intent(context, NotificationDialogActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//        Intent intent = new Intent(context, NotificationDialogActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//        sendOnReschedule(context, medicine);
//        showDialog();
        Log.i(TAG, "doWork: DO WOooooooork");
        return Result.success();

    }

    private void sendNotificationDialog(int imageSource, String medName, long id, String medTimes, String medStrength, int medLeft){
        IntentFilter intentFilter = new IntentFilter("notification_dialog");
        getApplicationContext().registerReceiver(dialogReceiver, intentFilter);
        Intent intent = new Intent();
        intent.putExtra(Constants.MED_NAME, medName);
        intent.putExtra(Constants.IMAGE_RESOURCE, imageSource);
        intent.putExtra(Constants.MED_ID, id);
        intent.putExtra(Constants.MED_TIMES, medTimes);
        intent.putExtra(Constants.MED_STRENGTH, medStrength);
        intent.putExtra(Constants.AMOUNT_LEFT, medLeft);
        intent.setAction("notification_dialog");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getApplicationContext().sendBroadcast(intent);

        Log.i(TAG, "sendNotificationDialog: " + imageSource);
        Log.i(TAG, "sendNotificationDialog: " + medName);
        Log.i(TAG, "sendNotificationDialog: " + id);
        Log.i(TAG, "sendNotificationDialog: TIEMS" + medTimes);
        Log.i(TAG, "sendNotificationDialog:STRED " + medStrength);
        Log.i(TAG, "sendNotificationDialog: MEDLEFT " + medLeft);

    }

    public void sendOnReschedule(Context context, int imageSource, String medName, long id, int amountLeft){

        Intent intent = new Intent(context, HomeActivity.class);
//        Medicine medicine = null;
//        MedStatus medStatus = new MedStatus(123456, "20-03-2022", "Taken", "abdelrahman@gmail.com");
//        intent.putExtra("med", id);
//        intent.putExtra("flag", "notification");
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT|FLAG_IMMUTABLE);

        Intent skipIntent = new Intent(context, SkipReceiver.class);
        Intent snoozeIntent = new Intent(context, SnoozeReceiver.class);
        Intent takeIntent = new Intent(context, TakeReceiver.class);

        skipIntent.putExtra(Constants.MED_ID, id);

        snoozeIntent.putExtra(Constants.MED_ID, id);
        snoozeIntent.putExtra(Constants.AMOUNT_LEFT, amountLeft);
        snoozeIntent.putExtra(Constants.MED_NAME, medName);
        snoozeIntent.putExtra(Constants.IMAGE_RESOURCE, imageSource);
//        snoozeIntent = WorkManager.getInstance(getApplicationContext())
//                .createCancelPendingIntent(getId());

        takeIntent.putExtra(Constants.MED_ID, id);
        takeIntent.putExtra(Constants.AMOUNT_LEFT, amountLeft);

//        PendingIntent intent = WorkManager.getInstance(getApplicationContext())
//                .createCancelPendingIntent(getId());

        PendingIntent skipActionIntent = PendingIntent.getBroadcast(context,
                0, skipIntent, PendingIntent.FLAG_UPDATE_CURRENT|FLAG_IMMUTABLE);

        PendingIntent takeActionIntent = PendingIntent.getBroadcast(context,
                0, takeIntent, PendingIntent.FLAG_UPDATE_CURRENT|FLAG_IMMUTABLE);

        PendingIntent snoozeActionIntent = PendingIntent.getBroadcast(context,
                0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT|FLAG_IMMUTABLE);

        int setImgResource;
        switch (imageSource){
            case 1:
                setImgResource = R.drawable.ic_pill;
                break;
            case 2:
                setImgResource = R.drawable.ic_injection;
                break;
            case 3:
                setImgResource = R.drawable.ic_drops;
                break;
            case 4:
                setImgResource = R.drawable.ic_medicine_other;
                break;
            default:
                setImgResource = R.drawable.ic_medicine_other;
                break;
        }

        notificationManagerCompat = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, RESCHEDULE_CHANNEL)
                .setSmallIcon(setImgResource)
                .setContentTitle(context.getString(R.string.resched_notification_title))
                .setContentText(context.getString(R.string.resched_notification_desc) + " " + medName)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .addAction(R.mipmap.ic_launcher, "Skip", skipActionIntent)
                .addAction(R.mipmap.ic_launcher, "Snooze", snoozeActionIntent)
                .addAction(R.mipmap.ic_launcher, "Take", takeActionIntent)
                .build();

        notificationManagerCompat.notify(1, notification);
//        int notificationId = 123;
//        NotificationManagerCompat.from(inContext).cancel(notificationId, 0));
    }



    }


