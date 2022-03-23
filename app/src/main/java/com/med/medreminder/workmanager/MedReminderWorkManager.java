package com.med.medreminder.workmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.med.medreminder.R;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.ui.homepage.view.HomeActivity;
import com.med.medreminder.utils.Constants;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MedReminderWorkManager extends Worker {
    private static final String TAG = "MEDREMINDER";
    Context context;

    public static final String HOUR = "HOUR";
    public static final String MIN = "MIN";

    ArrayList<Medicine> medicines;
    int position;

    public MedReminderWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    private void parseJSON(String string) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Medicine>>() {
        }.getType();
        medicines = gson.fromJson(string, type);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {

        Data data = getInputData();
        Log.d(TAG, "doWork:Time Now From Data"+ data.getInt("time_nw",0));
        LocalDateTime now = LocalDateTime.now();
        Log.d(TAG, "doWork:Time Now "+now.getMinute()+now.getHour());

        if (data.getInt("time_nw", 0) == now.getMinute()+now.getHour())
            return Result.success();
        Log.d(TAG, "After if");


        //current time // -> 1010101
        // current time (data.getlong("current_tim"))///

        parseJSON(data.getString(Constants.MED_STRING_LIST));

        position = data.getInt(Constants.MED_POSITION, 0);
        Log.d(TAG, "doWork: 70" + medicines);
        Log.d(TAG, "doWork: 70" + medicines.size());
        Log.d(TAG, "doWork: 70" + medicines.get(position));
        Log.d(TAG, "doWork: 70" + medicines.get(position).getImage());
        Log.d(TAG, "doWork: 70" + medicines.get(position).getName());
        Log.d(TAG, "doWork: 60" + position);

        notification(context, medicines.get(position));

        setAlarm(medicines);


      /*  OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MedReminderWorkManager.class)
               .setInitialDelay(Duration.between(timeNow,timeAt))
                .build();*/
        // WorkManager.getInstance().enqueue(oneTimeWorkRequest);


//        WorkManager.getInstance(getContext()).enqueue(oneTimeWorkRequest);


//        PeriodicWorkRequest workBuilder = new PeriodicWorkRequest.Builder(MyWorkManager.class, 24, TimeUnit.HOURS)
//                .setInitialDelay(Duration.between(timeNow, timeAt))
//                .build();

        // This is just to complete the example


//        PeriodicWorkRequest workBuilder = new PeriodicWorkRequest.Builder(MyWorkManager.class, 24, TimeUnit.HOURS)
//                .setInitialDelay(Duration.between(timeNow, timeAt))
//                .build();
//
//        // This is just to complete the example
//        WorkManager.getInstance().enqueue(workBuilder);

        return Result.success();
    }

    public static void notification(Context context, Medicine medicine) {
        Log.d(TAG, "sendOnMedicalReminder: " + "98");

        int reqCode = 1;
        Intent intent = new Intent(context, HomeActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT);
        String CHANNEL_ID = "channel_name";// The id of the channel.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(medicine.getImage())
                .setContentTitle(medicine.getName())
                .setContentText("Time to take your medicine")
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
        Log.d(TAG, "sendOnMedicalReminder: " + "122");

        Log.d("showNotification", "showNotification: " + reqCode);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setAlarm(ArrayList<Medicine> medicines) {
        WorkManager.getInstance().cancelAllWorkByTag("alarm");

        if (medicines != null && !medicines.isEmpty()) {
            Gson gson = new Gson();
            String medListString = gson.toJson(medicines);

            LocalDateTime timeNow = LocalDateTime.now();
            LocalDateTime timeAt = LocalDate.now().atTime(Integer.valueOf(medicines.get(0).getTime().split(",")[0].split(":")[0].trim()), Integer.valueOf(medicines.get(0).getTime().split(",")[0].split(":")[1].trim()));

            Duration duration = Duration.between(timeNow, timeAt);
            int position = 0;
            int nearestDose = 0;
            for (int i = 0; i < medicines.size(); i++) {
                for (int j = 0; j < medicines.get(i).getTime().split(",").length; j++) {
                    timeAt = LocalDate.now().atTime(Integer.valueOf(medicines.get(i).getTime().split(",")[j].split(":")[0].trim()), Integer.valueOf(medicines.get(i).getTime().split(",")[j].split(":")[1].trim()));
                    if ((timeAt.isAfter(timeNow)) && (duration.abs().toMillis() > Duration.between(timeNow, timeAt).toMillis())) {
                        duration = Duration.between(timeNow, timeAt);
                        position = i;
                        nearestDose = j;
                        Log.d(TAG, "setAlarm: --> " + timeAt);
                        Log.d(TAG, "setAlarm: --> " + "position:" + i);
                        Log.d(TAG, "setAlarm: --> " + "dose:" + j);
                        Log.d(TAG, "setAlarm: --> " + "medicine:" + medicines.get(i));
                    }
                }

            }
            Data data = new Data.Builder()
                    .putString(Constants.MED_STRING_LIST, medListString)
                    .putInt(Constants.MED_POSITION, position)
                    .putInt("time_nw",timeNow.getMinute()+timeNow.getHour())
                    .build();


            //set one time work request
            OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MedReminderWorkManager.class)
                    .setInitialDelay(duration)
                    .setInputData(data)
                    .addTag("alarm")
                    .build();
            WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);
        }
    }
}
