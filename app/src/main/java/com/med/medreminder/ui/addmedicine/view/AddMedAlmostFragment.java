package com.med.medreminder.ui.addmedicine.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.med.medreminder.R;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseHelper;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.addmedicine.presenter.AddMedPresenter;
import com.med.medreminder.ui.addmedicine.presenter.AddMedPresenterInterface;
import com.med.medreminder.ui.homepage.view.HomeActivity;
import com.med.medreminder.workmanager.MedReminderWorkManager;
import com.med.medreminder.workmanager.MyWorkManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AddMedAlmostFragment extends Fragment implements View.OnClickListener, AddMedViewInterface {

    public static final String TAG = "AddMedAlmostFragment";

    Button btnSetTreatmentDuration, btnGetRefillReminder, btnAddInstructions, btnChangeMedIcon, btnSave;
    ProgressBar progressBar;
    TextView textTitle;

    FirebaseFirestore db;
    AddMedPresenterInterface presenterInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_med_almost, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSetTreatmentDuration = view.findViewById(R.id.btn_set_treatment_duration);
        btnGetRefillReminder = view.findViewById(R.id.btn_get_refill_reminder);
//        btnAddInstructions = view.findViewById(R.id.btn_add_instructions);
        btnChangeMedIcon = view.findViewById(R.id.btn_change_med_icon);
        btnSave = view.findViewById(R.id.btn_almost_save);
        progressBar = view.findViewById(R.id.progress_bar);
        textTitle = view.findViewById(R.id.description);

        presenterInterface = new AddMedPresenter(Repository.getInstance(getContext(),
                ConcreteLocalSource.getInstance(getContext()), FirebaseWork.getInstance(getContext())));

        progressBar.setProgress(90);

        btnSetTreatmentDuration.setOnClickListener(this);
        btnGetRefillReminder.setOnClickListener(this);
//        btnAddInstructions.setOnClickListener(this);
        btnChangeMedIcon.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();

        setTitleText();

    }

    private void setTitleText(){
        Medicine medicine = Medicine.getInstance();
        textTitle.setText(medicine.getName());
    }

    private void actionSetTreatmentDuration(View view){
        NavDirections action = AddMedAlmostFragmentDirections.actionAddMedAlmostToTreatmentStartDate();
        Navigation.findNavController(view).navigate(action);
    }

    private void actionGetRefillReminder(View view){
        NavDirections action = AddMedAlmostFragmentDirections.actionAddMedAlmostToRefillLeft();
        Navigation.findNavController(view).navigate(action);
    }

    private void actionChangeMedIcon(View view){
        NavDirections action = AddMedAlmostFragmentDirections.actionAddMedAlmostToChangeIcon();
        Navigation.findNavController(view).navigate(action);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    void setAlarm(int hours, int minutes) {
        Log.d(TAG, "setAlarm: \n" + hours + "\n" + minutes);

        // Time to show notification at
        LocalDateTime timeAt = LocalDate.now().atTime(hours,minutes);
        LocalDateTime timeNow = LocalDateTime.now();

        Data data = new Data.Builder()
                .putInt(MedReminderWorkManager.HOUR,hours)
                .putInt(MedReminderWorkManager.MIN,minutes)
                .build();


        //set one time work request
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MedReminderWorkManager.class)
                .setInitialDelay(Duration.between(timeNow,timeAt))
                .setInputData(data)
                .build();
        WorkManager.getInstance(getContext()).enqueue(oneTimeWorkRequest);


//        PeriodicWorkRequest workBuilder = new PeriodicWorkRequest.Builder(MyWorkManager.class, 24, TimeUnit.HOURS)
//                .setInitialDelay(Duration.between(timeNow, timeAt))
//                .setInputData(data)
//                .build();
//
//        // This is just to complete the example
//        WorkManager.getInstance().enqueue(workBuilder);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void actionSave(){

        Medicine filledMed = Medicine.getInstance();

        //check for nullables
        if(filledMed.getOften()==null){
            filledMed.setOften(getString(R.string.selection_only_as_needed));
        }
        if(filledMed.getTime()==null){
            filledMed.setTime("");
        }
        if(filledMed.getStartDate()==null){
            String todayDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            filledMed.setStartDate(todayDate);

            try {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date date = sdf.parse(todayDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                filledMed.setStartDateMillis(calendar.getTimeInMillis());

            } catch(ParseException e){
                e.printStackTrace();
            }

        }

        if(filledMed.getEndDate()==null){
            filledMed.setEndDate(getString(R.string.selection_ongoing_treatment));
        }
        if(filledMed.getImage()==0){
            filledMed.setImage(R.drawable.ic_medicine_other);
        }
        if(filledMed.getRefillReminderTime()==null){
            filledMed.setRefillReminderTime("");
        }
        if(!filledMed.getRefillReminderTime().equals("")){
            filledMed.setRefillReminder(true);
        }

        long id = System.currentTimeMillis();
        String email = FirebaseHelper.getUserEmail(getContext());

        Medicine medicine = new Medicine(id, filledMed.getName(), filledMed.getForm(), filledMed.getStrength(),
                filledMed.getReason(), filledMed.getIsDaily(), filledMed.getOften(), filledMed.getTime(),
                filledMed.getStartDate(), filledMed.getEndDate(),filledMed.getStartDateMillis(),
                filledMed.getEndDateMillis(), filledMed.getMedLeft(), filledMed.getRefillLimit(),
                filledMed.getImage(), "", email, filledMed.isRefillReminder(), filledMed.getRefillReminderTime());

        Log.i(TAG, "actionSave: medicine save: " + medicine.toString());
        addMed(medicine);


        if(FirebaseHelper.isInternetAvailable(getContext()))
            if(FirebaseHelper.isUserLoggedIn(getContext())){
                addMedToFirestore(medicine, email, id);
            }

        // Set reminders HERE ------------------------------------------------------------
        // Set reminders HERE ------------------------------------------------------------
        //String[] time = medicine.getTime().split(":");
//        int hour = Integer.parseInt(time[0]);
//        int min = Integer.parseInt(time[1]);

//        Log.d("TAG", "actionSave: " + hour);
//        Log.d("TAG", "actionSave: " + min);

        //int often = 24;
       /* int hour, min;
        String[] time, dose2Time, dose3Time;
        String[] twiceDaily, threeDaily;
        int dose2hour, dose2min;
        int dose3hour, dose3min;
        switch (medicine.getOften()) {
            case "Once Daily":
                //often = 24;
                time = medicine.getTime().split(":");
                Log.d(TAG, "actionSave:"+time[0]);
                Log.d(TAG, "actionSave:"+time[1]);
                hour = Integer.parseInt(time[0].trim());
                min = Integer.parseInt(time[1].trim());
                setAlarm(hour, min);
                break;
            case "Twice Daily":
                // often = 12;
                twiceDaily = medicine.getTime().split(",");            //12:30,1:30
                time = twiceDaily[0].split(":");
                hour = Integer.parseInt(time[0].trim());
                min = Integer.parseInt(time[1].trim());
                setAlarm(hour, min);
                dose2Time = twiceDaily[1].split(":");
                Log.d(TAG, "actionSave:"+dose2Time[0]);
                Log.d(TAG, "actionSave:"+dose2Time[1]);
                dose2hour = Integer.parseInt(dose2Time[0].trim());
                dose2min = Integer.parseInt(dose2Time[1].trim());
                setAlarm(dose2hour,dose2min);
                break;
            case "3 times a day":
                // often = 8;
                threeDaily = medicine.getTime().split(",");            //12:30,1:30,3:30
                time = threeDaily[0].split(":");
                hour = Integer.parseInt(time[0].trim());
                min = Integer.parseInt(time[1].trim());
                setAlarm(hour, min);
                dose2Time = threeDaily[1].split(":");
                dose2hour = Integer.parseInt(dose2Time[0].trim());
                dose2min = Integer.parseInt(dose2Time[1].trim());
                setAlarm(dose2hour,dose2min);
                dose3Time = threeDaily[2].split(":");
                dose3hour = Integer.parseInt(dose3Time[0].trim());
                dose3min = Integer.parseInt(dose3Time[1].trim());
                setAlarm(dose3hour,dose3min);
                break;
        }*/

        //  setAlarm(hour, min);




        filledMed.setOften(getString(R.string.selection_only_as_needed));
        filledMed.setTime("");
        String todayDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        filledMed.setStartDate(todayDate);

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date = sdf.parse(todayDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            filledMed.setStartDateMillis(calendar.getTimeInMillis());

        } catch(ParseException e){
            e.printStackTrace();
        }

        filledMed.setEndDate(getString(R.string.selection_ongoing_treatment));
        filledMed.setEndDateMillis(0);
        filledMed.setImage(R.drawable.ic_medicine_other);

        filledMed.setMedLeft(0);
        filledMed.setRefillLimit(0);
        filledMed.setRefillReminder(false);
        filledMed.setRefillReminderTime("");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_set_treatment_duration:
                actionSetTreatmentDuration(view);
                break;
            case R.id.btn_get_refill_reminder:
                actionGetRefillReminder(view);
                break;

            case R.id.btn_change_med_icon:
                actionChangeMedIcon(view);
                break;

            case R.id.btn_almost_save:
                actionSave();
                break;

            default:
                Log.e(TAG, "onClick: error");

        }
    }

    @Override
    public void addMed(Medicine medicine) {
        presenterInterface.addMed(medicine);
        Toast.makeText(getContext(), "Medicine Added!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().finish();
    }


    @Override
    public void addMedToFirestore(Medicine medicine, String email, long id) {
        presenterInterface.addMedToFirestore(medicine, email, id);
    }

}