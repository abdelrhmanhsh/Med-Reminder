package com.med.medreminder.ui.homepage.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.med.medreminder.R;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.ForegroundInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class WorkManMedRem extends Worker {
    public static final String DATA = "DATA";
    private static final String CHANNEL_ID = "231";

    private NotificationManager notificationManager;



    public WorkManMedRem(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.d("TAG", "doWork: ");
       // Mark the Worker as important
        String progress = "Starting Location Upload...";
        setForegroundAsync(createForegroundInfo(progress));

        for(int i=0; i<=30; i++){
            try {
                Thread.sleep(1000);
                setForegroundAsync(createForegroundInfo(i+""));
                Log.d("LocationUploadWorker", "Log testing...." + i);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return Result.success();
    }

    private ForegroundInfo createForegroundInfo(String progress) {
        // Build a notification using bytesRead and contentLength

        Context context = getApplicationContext();
        String id = "my_channel_id_01";
        String title = "Time For Medication...";
        String skip = "Skip";
        String take = "Take";

        // This PendingIntent can be used to cancel the worker
        PendingIntent intent = WorkManager.getInstance(context)
                .createCancelPendingIntent(getId());


        Intent takeIntent = new Intent(getApplicationContext(), HomeActivity.class);
        takeIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        takeIntent.putExtra("Take","take");
        PendingIntent takenIntent = PendingIntent.getActivity(getApplicationContext(),1,takeIntent,PendingIntent.FLAG_ONE_SHOT);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }

        Notification notification = new NotificationCompat.Builder(context, id)
                .setContentTitle(title + progress)
                .setTicker(title)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setOngoing(true)
                // Add the cancel action to the notification which can
                // be used to cancel the worker
                .addAction(android.R.drawable.ic_delete, skip, intent)
                .addAction(android.R.drawable.ic_delete, take, takenIntent)
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

    //private void sendNotification(String title, String message) {
//        NotificationManager mNotificationManager =
//                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("1",
//                    "android",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription("WorkManger");
//            mNotificationManager.createNotificationChannel(channel);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel serviceChannel = new NotificationChannel(
//                    CHANNEL_ID,
//                    "Foreground Service Channel",
//                    NotificationManager.IMPORTANCE_DEFAULT
//            );
//            serviceChannel.setDescription("WorkManager");
//            NotificationManager manager = getApplicationContext().getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(serviceChannel);
//        }
//        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                // .setContentIntent(pendingIntent)
//                .build();


//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "1")
//                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
//                .setContentTitle(title) // title for notification
//                .setContentText(message)// message for notification
//                .setAutoCancel(true); // clear notification after click
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(pi);
//        mNotificationManager.notify(0, mBuilder.build());
   // }


