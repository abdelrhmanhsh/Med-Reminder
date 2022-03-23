package com.med.medreminder.workmanager;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static com.med.medreminder.ui.BaseApplication.MEDREMINDER_CHANNEL;
import static com.med.medreminder.ui.BaseApplication.RESCHEDULE_CHANNEL;

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
    public static DialogReceiver dialogReceiver;

    public MyWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        dialogReceiver = new DialogReceiver();
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

    private static void showDialog(Context context){

        Intent notificationIntent = new Intent(context, NotificationDialogActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 5);

        PendingIntent pendingintent = PendingIntent.getActivity(context, 0,
                notificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT);

//        notification.setLatestEventInfo(context, title, message, pendingintent);
//        notificationIntent.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationIntent.setAction("dummy_unique_action_identifyer" + 5);
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


