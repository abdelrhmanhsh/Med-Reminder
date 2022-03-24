package com.med.medreminder.ui.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.med.medreminder.R;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseHelper;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Repository;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.workmanager.MedReminderWorkManager;
import com.med.medreminder.workmanager.MyWorkManager;

import java.util.concurrent.TimeUnit;

public class DailyNotificationDialogActivity  extends Activity {

    RepositoryInterface repo;

    public static final String TAG = "NotificationDialogActivity";

    Button btnSkip, btnTake, btnReschedule;
    ImageView imgEdit, medIcon;
    TextView medName, medSchedule, medStrength, medAmountLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_notification);

        Intent intent = getIntent();
        String medNameStr = intent.getStringExtra(Constants.MED_NAME);
        int medLeft = intent.getIntExtra(Constants.AMOUNT_LEFT, -1);
        long medId = intent.getLongExtra(Constants.MED_ID, -1);
        int imageResource = intent.getIntExtra(Constants.IMAGE_RESOURCE, -1);
        String medTimes = intent.getStringExtra(Constants.MED_TIMES);
        String medStrengthStr = intent.getStringExtra(Constants.MED_STRENGTH);

        btnSkip = findViewById(R.id.dialog_btn_skip);
        btnTake = findViewById(R.id.dialog_btn_take);
        btnReschedule = findViewById(R.id.dialog_btn_reschedule);
        imgEdit = findViewById(R.id.dialog_img_edit);
        medIcon = findViewById(R.id.med_icon);
        medName = findViewById(R.id.med_name);
        medSchedule = findViewById(R.id.scheduled_desc);
        medStrength = findViewById(R.id.strength_desc);
        medAmountLeft = findViewById(R.id.med_left_desc);
        this.setFinishOnTouchOutside(true);

        repo = Repository.getInstance(this,
                ConcreteLocalSource.getInstance(this), FirebaseWork.getInstance(this));


        Log.i(TAG, "showNotificationDialog: med id " + medId);

        imgEdit.setVisibility(View.GONE);

        int setImgResource;
        switch (imageResource){
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

        medIcon.setImageResource(setImgResource);
        medName.setText(medNameStr);
        medSchedule.setText("Scheduled for " + medTimes);
        medStrength.setText(medStrengthStr);
        medAmountLeft.setText(medLeft + " Med left");

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DailyNotificationDialogActivity.this, "Skipped!", Toast.LENGTH_SHORT).show();
//                WorkManager.getInstance().cancelAllWorkByTag(String.valueOf(medId));
                finish();
            }
        });

        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(medLeft > 0) {
                    String userEmail = FirebaseHelper.getUserEmail(DailyNotificationDialogActivity.this);
                    int newAmount = medLeft - 1;
                    updateMedAmount(medId, newAmount);
                    updateMedAmountFirestore(userEmail, medId, newAmount);
                    Toast.makeText(DailyNotificationDialogActivity.this, "Medicine Taken!", Toast.LENGTH_SHORT).show();
                }

                WorkManager.getInstance().cancelAllWorkByTag(String.valueOf(medId));
                finish();

            }
        });

//        btnReschedule.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(DailyNotificationDialogActivity.this, "Snoozed 15 minutes!", Toast.LENGTH_SHORT).show();
//                sendSnoozeNotification(DailyNotificationDialogActivity.this, imageResource, medNameStr, medId, medLeft);
//                finish();
//            }
//        });
        btnReschedule.setVisibility(View.GONE);

        Window window = getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }
//
//    private void sendSnoozeNotification(Context context, int imageResource, String medName, long id, int amountLeft){
//        Data data = new Data.Builder()
//                .putInt(Constants.IMAGE_RESOURCE, imageResource)
//                .putString(Constants.MED_NAME, medName)
//                .putLong(Constants.MED_ID, id)
//                .putInt(Constants.AMOUNT_LEFT, amountLeft)
//                .build();
//
//        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MedReminderWorkManager.class)
//                .setInputData(data)
////                .setConstraints(constraints)
//                .setInitialDelay(15, TimeUnit.SECONDS)
//                .addTag(String.valueOf(id))
//                .build();
//
//        androidx.work.WorkManager.getInstance(context).enqueue(request);
//    }

    private void updateMedAmount(long id, int newAmount) {
        repo.updateMedAmount(id, newAmount);
    }

    private void updateMedAmountFirestore(String email, long id, int newAmount) {
        repo.updateMedAmountFirestore(email, id, newAmount);
    }

}