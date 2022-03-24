package com.med.medreminder.workmanager;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static com.med.medreminder.BaseApplication.REFILL_CHANNEL;
import static com.med.medreminder.BaseApplication.RESCHEDULE_CHANNEL;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.med.medreminder.R;
import com.med.medreminder.broadcasts.DialogReceiver;
import com.med.medreminder.broadcasts.RefillDialogReceiver;
import com.med.medreminder.broadcasts.RefillSkipReceiver;
import com.med.medreminder.broadcasts.RefillSnoozeReceiver;
import com.med.medreminder.broadcasts.SkipReceiver;
import com.med.medreminder.broadcasts.SnoozeReceiver;
import com.med.medreminder.broadcasts.TakeReceiver;
import com.med.medreminder.utils.Constants;

public class RefillReminder extends Worker{

    public static final String TAG = "WorkManager";
    public static final String IMAGE_RESOURCE = "IMAGE_RESOURCE";
    public static final String MED_NAME = "MED_NAME";

    private static NotificationManagerCompat notificationManagerCompat;
    Context context;
    public static RefillDialogReceiver refillReceiver;

    public RefillReminder(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        refillReceiver = new RefillDialogReceiver();
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {

        Data inputData = getInputData();
        int imageSource = inputData.getInt(IMAGE_RESOURCE, -1);
        String medName = inputData.getString(MED_NAME);
        long id = inputData.getLong(Constants.MED_ID, -1);
        int amountLeft = inputData.getInt(Constants.AMOUNT_LEFT, -1);
        String medTimes = inputData.getString(Constants.MED_TIMES);
        String medStrength = inputData.getString(Constants.MED_STRENGTH);

        sendOnRefill(context,imageSource,medName, id, amountLeft);
        sendRefillDialog(imageSource, medName, id, medTimes, medStrength, amountLeft);
        return ListenableWorker.Result.success();
    }

    private void sendRefillDialog(int imageSource, String medName, long id, String medTimes, String medStrength, int medLeft){
        IntentFilter intentFilter = new IntentFilter("notification_dialog");
        getApplicationContext().registerReceiver(refillReceiver, intentFilter);
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

    }

    public void sendOnRefill(Context context, int imageSource, String medName, long id, int amountLeft){

//        PendingIntent intent = WorkManager.getInstance(getApplicationContext())
//                .createCancelPendingIntent(getId());

        //snooze --> Snooze for extra 5 mins
        //refill --> refill

//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT|FLAG_IMMUTABLE);

        Intent skipIntent = new Intent(context, RefillSkipReceiver.class);
        Intent snoozeIntent = new Intent(context, RefillSnoozeReceiver.class);
        Intent refillIntent = new Intent(context, RefillDialogReceiver.class);

        skipIntent.putExtra(Constants.MED_ID, id);

        snoozeIntent.putExtra(Constants.MED_ID, id);
        snoozeIntent.putExtra(Constants.AMOUNT_LEFT, amountLeft);
        snoozeIntent.putExtra(Constants.MED_NAME, medName);
        snoozeIntent.putExtra(Constants.IMAGE_RESOURCE, imageSource);
//        snoozeIntent = WorkManager.getInstance(getApplicationContext())
//                .createCancelPendingIntent(getId());

        refillIntent.putExtra(Constants.MED_ID, id);
        refillIntent.putExtra(Constants.AMOUNT_LEFT, amountLeft);

//        PendingIntent intent = WorkManager.getInstance(getApplicationContext())
//                .createCancelPendingIntent(getId());

        PendingIntent skipActionIntent = PendingIntent.getBroadcast(context,
                0, skipIntent, PendingIntent.FLAG_UPDATE_CURRENT|FLAG_IMMUTABLE);

//        PendingIntent refillActionIntent = PendingIntent.getBroadcast(context,
//                0, refillIntent, PendingIntent.FLAG_UPDATE_CURRENT|FLAG_IMMUTABLE);

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
        Notification notification = new NotificationCompat.Builder(context, REFILL_CHANNEL)
                .setSmallIcon(setImgResource)
                .setContentTitle("Refill")
                .setContentText(medName)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .addAction(R.drawable.ic_delete,"Skip", skipActionIntent)
                .addAction(R.drawable.ic_snooze,"Snooze", snoozeActionIntent)
                .build();

        notificationManagerCompat.notify(2, notification);
    }


}
