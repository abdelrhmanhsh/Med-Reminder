package com.med.medreminder.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.med.medreminder.R;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseHelper;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.ui.meddisplayedit.presenter.DisplayEditPresenter;
import com.med.medreminder.ui.meddisplayedit.presenter.DisplayPresenterInterface;
import com.med.medreminder.ui.meddisplayedit.view.DisplayEditViewInterface;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.workmanager.MyWorkManager;

import java.util.concurrent.TimeUnit;

public class SnoozeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        long id = intent.getLongExtra(Constants.MED_ID, -1);
        int amountLeft = intent.getIntExtra(Constants.AMOUNT_LEFT, -1);
        String medName = intent.getStringExtra(Constants.MED_NAME);
        int imageResource = intent.getIntExtra(Constants.IMAGE_RESOURCE, -1);

        Toast.makeText(context, "Snoozed 15 minutes!", Toast.LENGTH_SHORT).show();
        sendSnoozeNotification(context, imageResource, medName, id, amountLeft);
        NotificationManagerCompat.from(context).cancel(1);

    }

    private void sendSnoozeNotification(Context context, int imageResource, String medName, long id, int amountLeft){
        Data data = new Data.Builder()
                .putInt(Constants.IMAGE_RESOURCE, imageResource)
                .putString(Constants.MED_NAME, medName)
                .putLong(Constants.MED_ID, id)
                .putInt(Constants.AMOUNT_LEFT, amountLeft)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorkManager.class)
                .setInputData(data)
//                .setConstraints(constraints)
                .setInitialDelay(15, TimeUnit.SECONDS)
                .addTag(String.valueOf(id))
                .build();

        androidx.work.WorkManager.getInstance(context).enqueue(request);
    }

}