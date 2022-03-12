package com.med.medreminder.workmanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.ForegroundInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.med.medreminder.R;
import com.med.medreminder.ui.MainActivity;

public class MyWorker extends Worker {

    private NotificationManager notificationManager;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
    }

    @NonNull
    @Override
    public Result doWork() {

        // Mark the Worker as important
        String progress = "Starting Location Upload...";
        setForegroundAsync(createForegroundInfo(progress));

        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(1000);
                setForegroundAsync(createForegroundInfo(i + ""));
                Log.d("LocationUploadWorker", "Log testing...." + i);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return Result.success();
    }

    @NonNull
    private ForegroundInfo createForegroundInfo(@NonNull String progress) {
        // Build a notification using bytesRead and contentLength

        Context context = getApplicationContext();
        String id = "my_channel_id_01";
        String title = "Refill Reminder";
        String message = "Panadol, you have only 2 pill(s) left";
        String skip = "Skip";
        String snooze = "Snooze";
        String refill = "Refill";
        // This PendingIntent can be used to cancel the worker
        PendingIntent intent = WorkManager.getInstance(context)
                .createCancelPendingIntent(getId());

        Intent refillIntent = new Intent(getApplicationContext(), MainActivity.class);
        refillIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        refillIntent.putExtra("Refill","Refill");
        PendingIntent refillPIntent = PendingIntent.getActivity(getApplicationContext(),1,refillIntent,PendingIntent.FLAG_ONE_SHOT);

        Intent snoozeIntent = new Intent(getApplicationContext(), MainActivity.class);
        snoozeIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        snoozeIntent.putExtra("Snooze","Snooze");
        PendingIntent snoozePIntent = PendingIntent.getActivity(getApplicationContext(),2,snoozeIntent,PendingIntent.FLAG_ONE_SHOT);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }

        Notification notification = new NotificationCompat.Builder(context, id)
                .setContentTitle(title)
                .setTicker(title)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setOngoing(true)
                .setContentText(message)
                // Add the cancel action to the notification which can
                // be used to cancel the worker
                .addAction(android.R.drawable.ic_delete, skip, intent)
                .addAction(android.R.drawable.ic_lock_idle_alarm,snooze,snoozePIntent)
                .addAction(android.R.drawable.ic_input_add,refill,refillPIntent)
                .build();

        return new ForegroundInfo(100, notification);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        // Create a Notification channel
        CharSequence name = "Worker channel";
        String description = "Description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("my_channel_id_01", name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        notificationManager.createNotificationChannel(channel);
    }
}