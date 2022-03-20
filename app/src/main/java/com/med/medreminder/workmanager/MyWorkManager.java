package com.med.medreminder.workmanager;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static com.med.medreminder.BaseApplication.RESCHEDULE_CHANNEL;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.med.medreminder.R;
import com.med.medreminder.model.MedStatus;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.ui.homepage.view.HomeActivity;
import com.med.medreminder.ui.homepage.view.HomeFragment;
import com.med.medreminder.ui.homepage.view.NotificationDialogActivity;

public class MyWorkManager extends Worker {

    public static final String TAG = "WorkManager";
    public static final String IMAGE_RESOURCE = "IMAGE_RESOURCE";
    public static final String MED_NAME = "MED_NAME";
    public static final String MED_ID = "MED_ID";

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
        long id = inputData.getLong(MED_ID, -1);

//        sendOnReschedule(context, imageSource, medName);
//        showDialog();
//        showNotificationDialog(context);
        sendOnReschedule(context, imageSource, medName, id);
//        sendOnReschedule(context, medicine);
//        showDialog();
        Log.i(TAG, "doWork: DO WOooooooork");
        return Result.success();
    }

    public void sendOnReschedule(Context context, int imageSource, String medName, long id){

        Intent intent = new Intent(context, HomeActivity.class);
//        Medicine medicine = null;
//        MedStatus medStatus = new MedStatus(123456, "20-03-2022", "Taken", "abdelrahman@gmail.com");
        intent.putExtra("med", id);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT|FLAG_IMMUTABLE);

        notificationManagerCompat = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, RESCHEDULE_CHANNEL)
                .setSmallIcon(imageSource)
                .setContentTitle(context.getString(R.string.resched_notification_title))
                .setContentText(context.getString(R.string.resched_notification_desc) + " " + medName)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

//    public void sendOnReschedule(Context context, int imageSource, String medName){
//
//        Intent intent = new Intent(context, HomeActivity.class);
////        Medicine medicine = null;
//        MedStatus medStatus = new MedStatus(123456, "20-03-2022", "Taken", "abdelrahman@gmail.com");
//        intent.putExtra("med", medStatus);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT|FLAG_IMMUTABLE);
//
//        notificationManagerCompat = NotificationManagerCompat.from(context);
//        Notification notification = new NotificationCompat.Builder(context, RESCHEDULE_CHANNEL)
//                .setSmallIcon(imageSource)
//                .setContentTitle(context.getString(R.string.resched_notification_title))
//                .setContentText(context.getString(R.string.resched_notification_desc) + " " + medName)
//                .setContentIntent(pendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setCategory(NotificationCompat.CATEGORY_REMINDER)
//                .build();
//
//        notificationManagerCompat.notify(1, notification);
//    }

//    private void showNotificationDialog(Context context){
//
////        HomeFragment.
////        HomeFragment.showNotificationDialog(context);
//
////        ContextCompat.getMainExecutor(context).execute(()  -> {
////            // This is where your UI code goes.
////            Dialog dialog = new Dialog(context);
////            dialog.setContentView(R.layout.dialog_notification);
////
////            dialog.show();
//////        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
//////            Window window = dialog.getWindow();
//////            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////        });
////
////        new Thread(){
////
////            @Override
////            public void run() {
////
////                Dialog dialog = new Dialog(context);
////                dialog.setContentView(R.layout.dialog_notification);
////
////                dialog.show();
//////        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
////                Window window = dialog.getWindow();
////                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////
////            }
////        }.start();
//
//    }

//    private void showDialog(){
//        Intent intent = new Intent(context, NotificationDialogActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
//
//        notificationManagerCompat = NotificationManagerCompat.from(context);
//        Notification notification = new NotificationCompat.Builder(context, RESCHEDULE_CHANNEL)
//                .setSmallIcon(R.drawable.ic_drops)
//                .setContentTitle(context.getString(R.string.resched_notification_title))
//                .setContentText(context.getString(R.string.resched_notification_desc))
//                .setContentIntent(pendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
////                .setVibrate()
//                .build();
//
//        notificationManagerCompat.notify(1, notification);
//
//    }

//    public void showDialog() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//
//        alertDialog.setTitle("REMINDER!");
//        alertDialog.setMessage("Turn off alarm by pressing off");
//
//        alertDialog.setNegativeButton("Off", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_SHORT).show();
//            }
//        });
//
////        alertDialog.show();
//        alertDialog.show().getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
//        // line you have to add
////        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
//    }

}
