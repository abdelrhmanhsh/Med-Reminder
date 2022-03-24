package com.med.medreminder.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.med.medreminder.ui.dialogs.DailyNotificationDialogActivity;
import com.med.medreminder.ui.dialogs.NotificationDialogActivity;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.workmanager.MedReminderWorkManager;
import com.med.medreminder.workmanager.MyWorkManager;

public class DailyDialogReceiver extends BroadcastReceiver {

    public static final String TAG = "DailyDialogReceiver";
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        String medName = intent.getStringExtra(Constants.MED_NAME);
        int medAmount = intent.getIntExtra(Constants.AMOUNT_LEFT, -1);
        long id = intent.getLongExtra(Constants.MED_ID, -1);
        int imageResource = intent.getIntExtra(Constants.IMAGE_RESOURCE, -1);
        String medStrength = intent.getStringExtra(Constants.MED_STRENGTH);
        String medTimes = intent.getStringExtra(Constants.MED_TIMES);

        this.context = context;
        Log.i(TAG, "onReceive: RECEIVED DIALOG");
        Intent incomingIntent = new Intent(context, DailyNotificationDialogActivity.class);
        incomingIntent.putExtra(Constants.MED_NAME, medName);
        incomingIntent.putExtra(Constants.AMOUNT_LEFT, medAmount);
        incomingIntent.putExtra(Constants.MED_ID, id);
        incomingIntent.putExtra(Constants.IMAGE_RESOURCE, imageResource);
        incomingIntent.putExtra(Constants.MED_STRENGTH, medStrength);
        incomingIntent.putExtra(Constants.MED_TIMES, medTimes);
        incomingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(incomingIntent);
        context.unregisterReceiver(MedReminderWorkManager.dialogReceiver);
    }
}