package com.med.medreminder.ui.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.med.medreminder.R;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseHelper;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Repository;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.utils.Constants;

public class RefillDialogActivity extends Activity {

    RepositoryInterface repo;

    public static final String TAG = "RefillDialogActivity";

    TextView textOk, textCancel ,textMedRemaining;
    EditText inputAddMeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_refill_med);

        Intent intent = getIntent();
        int medLeft = intent.getIntExtra(Constants.AMOUNT_LEFT, -1);
        long medId = intent.getLongExtra(Constants.MED_ID, -1);

        textOk = findViewById(R.id.text_ok);
        textCancel = findViewById(R.id.text_cancel);
        textMedRemaining = findViewById(R.id.text_meds_remaining);
        inputAddMeds = findViewById(R.id.input_add_meds);


        // medLeft
        // id

        textOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentMed = medLeft;
                int medIncreased = Integer.parseInt(inputAddMeds.getText().toString());
//                med.setMedLeft(currentMed+medIncreased);
//                updateMed(med);

                int newMedAmount = currentMed+medIncreased;
                updateMedAmount(medId, newMedAmount);

                if(FirebaseHelper.isInternetAvailable(RefillDialogActivity.this))
                    if(FirebaseHelper.isUserLoggedIn(RefillDialogActivity.this)){
                        String email = FirebaseHelper.getUserEmail(RefillDialogActivity.this);
//                        updateMedFirestore(med, email, id);
                        updateMedAmountFirestore(email, medId, newMedAmount);
                    }

                finish();
            }
        });

        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textMedRemaining.setText("You have " + medLeft + " meds remaining");

//        Intent intent = getIntent();
////        String medName = intent.getStringExtra(Constants.MED_NAME);
//        int medLeft = intent.getIntExtra(Constants.AMOUNT_LEFT, -1);
//        long medId = intent.getLongExtra(Constants.MED_ID, -1);
//        int imageResource = intent.getIntExtra(Constants.IMAGE_RESOURCE, -1);
//        String medTimes = intent.getStringExtra(Constants.MED_TIMES);
//        String medStrengthStr = intent.getStringExtra(Constants.MED_STRENGTH);

//        btnSkip = findViewById(R.id.dialog_btn_skip);
//        btnTake = findViewById(R.id.dialog_btn_take);
//        btnReschedule = findViewById(R.id.dialog_btn_reschedule);
//        imgEdit = findViewById(R.id.dialog_img_edit);
//        medIcon = findViewById(R.id.med_icon);
//        medName = findViewById(R.id.med_name);
//        medSchedule = findViewById(R.id.scheduled_desc);
//        medStrength = findViewById(R.id.strength_desc);
//        medAmountLeft = findViewById(R.id.med_left_desc);
        this.setFinishOnTouchOutside(true);

        repo = Repository.getInstance(this,
                ConcreteLocalSource.getInstance(this), FirebaseWork.getInstance(this));


//        Log.i(TAG, "showNotificationDialog: med id " + medId);
//
//        imgEdit.setVisibility(View.GONE);

//        imgEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Bundle bundle = new Bundle();
////                bundle.putLong("med_id", medId);
//                Intent intent = new Intent(NotificationDialogActivity.this, MedDisplayEditActivity.class);
//                intent.putExtra(Constants.MED_ID, medId);
//                startActivity(intent);
//
////                NavController navController = Navigation.findNavController(getView());
////                navController.navigate(R.id.actionNavigationHomeToEditNav, bundle);
////                dialog.dismiss();
//            }
//        });

//        int setImgResource;
//        switch (imageResource){
//            case 1:
//                setImgResource = R.drawable.ic_pill;
//                break;
//            case 2:
//                setImgResource = R.drawable.ic_injection;
//                break;
//            case 3:
//                setImgResource = R.drawable.ic_drops;
//                break;
//            case 4:
//                setImgResource = R.drawable.ic_medicine_other;
//                break;
//            default:
//                setImgResource = R.drawable.ic_medicine_other;
//                break;
//        }

//        medIcon.setImageResource(setImgResource);
//        medName.setText(medNameStr);
//        medSchedule.setText("Scheduled for " + medTimes);
//        medStrength.setText(medStrengthStr);
//        medAmountLeft.setText(medLeft + " Pills/med left");

//        btnSkip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(RefillDialogActivity.this, "Skipped!", Toast.LENGTH_SHORT).show();
//                WorkManager.getInstance().cancelAllWorkByTag(String.valueOf(medId));
//                finish();
//            }
//        });
//
//        btnTake.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(medLeft > 0) {
//                    String userEmail = FirebaseHelper.getUserEmail(RefillDialogActivity.this);
//                    int newAmount = medLeft - 1;
//                    updateMedAmount(medId, newAmount);
//                    updateMedAmountFirestore(userEmail, medId, newAmount);
//                    Toast.makeText(RefillDialogActivity.this, "Medicine Taken!", Toast.LENGTH_SHORT).show();
//                }
//
//                WorkManager.getInstance().cancelAllWorkByTag(String.valueOf(medId));
//                finish();
//
//            }
//        });
//
//        btnReschedule.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(RefillDialogActivity.this, "Snoozed 15 minutes!", Toast.LENGTH_SHORT).show();
//                sendSnoozeNotification(RefillDialogActivity.this, imageResource, medNameStr, medId, medLeft);
//                finish();
//            }
//        });

        Window window = getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    private void updateMedAmount(long id, int newAmount) {
        repo.updateMedAmount(id, newAmount);
    }

    private void updateMedAmountFirestore(String email, long id, int newAmount) {
        repo.updateMedAmountFirestore(email, id, newAmount);
    }

}