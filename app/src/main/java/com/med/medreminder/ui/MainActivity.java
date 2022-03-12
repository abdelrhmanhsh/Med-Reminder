package com.med.medreminder.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.NotificationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.med.medreminder.R;
import com.med.medreminder.workmanager.MyWorker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oneTimeWork();

        if(getIntent().hasExtra("Refill")){
            Log.d("Refill","Refill");
        }else if(getIntent().hasExtra("Snooze")){
            Log.d("Snooze","Snooze");
        }

        /*Constraints constraints = new Constraints.Builder().build();
        final PeriodicWorkRequest periodicWorkRequest1 = new PeriodicWorkRequest.Builder(MyWorker.class,1, TimeUnit.MILLISECONDS)
                .setInitialDelay(6000,TimeUnit.MILLISECONDS)
                .build();
        WorkManager workManager =  WorkManager.getInstance(this);
        workManager.enqueue(periodicWorkRequest1);
        workManager.getWorkInfoByIdLiveData(periodicWorkRequest1.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        if (workInfo != null) {
                            Log.d("periodicWorkRequest", "Status changed to : " + workInfo.getState());
                        }
                    }
                });
*/
    }


    public void oneTimeWork() {
        WorkRequest locationUploadWorkRequest =
                new OneTimeWorkRequest.Builder(MyWorker.class)
                        .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresStorageNotLow(true).build())
                        .build();
        WorkManager.getInstance(MainActivity.this).enqueue(locationUploadWorkRequest);

        // to cancel worker class
        //WorkManager.getInstance(MainActivity.this).cancelWorkById(locationUploadWorkRequest.getId());
    }

}