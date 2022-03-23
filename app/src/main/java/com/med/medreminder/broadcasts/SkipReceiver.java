package com.med.medreminder.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;
import androidx.work.WorkManager;

import com.med.medreminder.utils.Constants;

public class SkipReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        long id = intent.getLongExtra(Constants.MED_ID, -1);

        Toast.makeText(context, "Skipped!", Toast.LENGTH_SHORT).show();
        WorkManager.getInstance().cancelAllWorkByTag(String.valueOf(id));
//        NotificationManagerCompat.from(context).cancel("reschedule", 0);
        NotificationManagerCompat.from(context).cancel(1);

    }
}