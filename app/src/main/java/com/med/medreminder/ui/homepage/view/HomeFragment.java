package com.med.medreminder.ui.homepage.view;

import static com.med.medreminder.BaseApplication.RESCHEDULE_CHANNEL;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.med.medreminder.R;
import com.med.medreminder.databinding.FragmentHomeBinding;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseHelper;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.addmedicine.view.AddMedActivity;
import com.med.medreminder.ui.homepage.presenter.HomeMedPresenter;
import com.med.medreminder.ui.homepage.presenter.homeMedPresenterInterface;
import com.med.medreminder.ui.medicationScreen.presenter.ActivePresenter;
import com.med.medreminder.ui.medicationScreen.presenter.ActivePresenterInterface;
import com.med.medreminder.ui.medicationScreen.view.ActiveMedViewInterface;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;
import com.med.medreminder.workmanager.MyWorkManager;
import com.med.medreminder.workmanager.RefillReminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;


public class HomeFragment extends Fragment implements onMedClickListener, homeMedViewInterface     {

    public static final String TAG = "HomeFragment";

    private static NotificationManagerCompat notificationManagerCompat;

    private FragmentHomeBinding binding;
    LinearLayoutManager linearLayoutManager;
    MedHomeAdapter medHomeAdapter;
    RecyclerView allMed_rv;
    FloatingActionButton addMed_floatBtn;

    int schedYear, schedMonth, schedDay, schedHour, schedMinute;
    long curDate;

    HorizontalCalendar horizontalCalendar;

    ActivePresenterInterface activePresenterInterface;

    homeMedPresenterInterface homeMedPresenterInterface;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allMed_rv = view.findViewById(R.id.allMed_rv);
        addMed_floatBtn = view.findViewById(R.id.addMed_floatBtn);

        YourPreference yourPrefrence = YourPreference.getInstance(getContext());
        String userEmail = FirebaseHelper.getUserEmail(getContext());

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 5);

        horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .defaultSelectedDate(Calendar.getInstance())
                .build();
        Log.d("TAG", "onViewCreated: The current selected date: " + horizontalCalendar.getSelectedDate());

        Log.d("TAG", "onViewCreated: " + startDate.getTime());

        try {
            String dateString = startDate.getTime().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateString);

            // curDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        curDate  = Calendar.getInstance().getTimeInMillis();

        Log.d("TAG", "onViewCreated: " + curDate);
        Log.d("TAG", "onViewCreated: " + Calendar.getInstance().getTimeInMillis());


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                Log.d("TAG", "onDateSelected: " + date.getTimeInMillis());
                String dateString = DateFormat.format("MM/dd/yyyy", new Date(String.valueOf(date.getTime()))).toString();
                Log.d("TAG", "onDateSelected: Dateeeeeeeeeeeeeeeeeeeeeeeeee" + dateString);
                //Log.d("TAG", "onDateSelected: " + date.get(position));
                //homeMedPresenterInterface.showMedsOnDate(getViewLifecycleOwner(),date.getTimeInMillis());


                // shared pref -> isMedFriedn medFriendEMA
                if (FirebaseHelper.isInternetAvailable(getContext())){

//                    Log.d(TAG, "onViewCreated: " + "INTERNET CONNECTED");
//                    Log.d(TAG, "onViewCreated: " + yourPrefrence.getData(Constants.EMAIL));


                    homeMedPresenterInterface.getMedicinesOnDateFromFirebase(yourPrefrence.getData(Constants.EMAIL),curDate);
                }
                else {
                    Log.d(TAG, "onViewCreated: " + "INTERNET DISCONNECTED");

                    homeMedPresenterInterface.showMedsOnDate(getViewLifecycleOwner(), curDate, userEmail);

                }
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {
            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                return true;
            }
        });


        allMed_rv.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        allMed_rv.setLayoutManager(linearLayoutManager);
        medHomeAdapter = new MedHomeAdapter(getContext(), this);
        allMed_rv.setAdapter(medHomeAdapter);


        homeMedPresenterInterface = new HomeMedPresenter(this, Repository.getInstance(getContext(),
                ConcreteLocalSource.getInstance(getContext()), FirebaseWork.getInstance()), curDate);



        if (FirebaseHelper.isInternetAvailable(getContext())){

            Log.d(TAG, "onViewCreated: " + "INTERNET CONNECTED");
            Log.d(TAG, "onViewCreated: " + yourPrefrence.getData(Constants.EMAIL));
            homeMedPresenterInterface.getMedicinesOnDateFromFirebase(yourPrefrence.getData(Constants.EMAIL),curDate);
        }
        else {
            Log.d(TAG, "onViewCreated: " + "INTERNET DISCONNECTED");
            homeMedPresenterInterface.showMedsOnDate(getViewLifecycleOwner(), curDate, userEmail);
        }


        addMed_floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddMedActivity.class));
            }
        });
    }

    @Override
    public void onCLick(Medicine medicine) {
        showNotificationDialog(medicine);
    }

    private void showNotificationDialog(Medicine medicine){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_notification);

        ImageView imgEdit, medIcon;
        TextView medName, medSchedule, medStrength, medAmountLeft;
        Button btnSkip, btnTake, btnReschedule;

        imgEdit = dialog.findViewById(R.id.dialog_img_edit);
        medIcon = dialog.findViewById(R.id.med_icon);
        medName = dialog.findViewById(R.id.med_name);
        medSchedule = dialog.findViewById(R.id.scheduled_desc);
        medStrength = dialog.findViewById(R.id.strength_desc);
        medAmountLeft = dialog.findViewById(R.id.med_left_desc);
        btnSkip = dialog.findViewById(R.id.dialog_btn_skip);
        btnTake = dialog.findViewById(R.id.dialog_btn_take);
        btnReschedule = dialog.findViewById(R.id.dialog_btn_reschedule);
        Log.i(TAG, "showNotificationDialog: med id " + medicine.getId());

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("med_id", medicine.getId());
                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.actionNavigationHomeToEditNav, bundle);
                dialog.dismiss();
            }
        });

        medIcon.setImageResource(medicine.getImage());
        medName.setText(medicine.getName());
        medSchedule.setText("Scheduled for " + medicine.getTime());
        medStrength.setText(medicine.getStrength());
        medAmountLeft.setText(medicine.getMedLeft() + " Pills/med left");

        String medStatus = medicine.getStatus(); // status are ("", skipped, taken, snoozed)
        switch (medStatus){
            case "Skipped":
                btnSkip.setText(getString(R.string.unskip));
                break;
            case "Taken":
                btnTake.setText(getString(R.string.untake));
                break;
            default:
                // do nothing
                break;
        }

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (medicine.getStatus().equals(getString(R.string.skipped))) // perform un-skip
                    medicine.setStatus("");
                else {
                    medicine.setStatus(getString(R.string.skipped));
                }
                updateMed(medicine);
                dialog.dismiss();
            }
        });

        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // For refill reminder (check if isRefillReminder==true)
                if(medicine.isRefillReminder()){
                    int currMedLeft = medicine.getMedLeft();
                    if (medicine.getStatus().equals(getString(R.string.taken))){ // perform un-take
                        medicine.setStatus("");
                        medicine.setMedLeft(currMedLeft+1);

                    } else {
                        if(currMedLeft <= 0){
                            if(medicine.isRefillReminder())
                                Toast.makeText(getContext(), "You have no med left!\nPlease consider refill!", Toast.LENGTH_SHORT).show();
                        } else{
                            //check for med amount here to set refill reminder!
                            if(currMedLeft <= medicine.getRefillLimit()){
                                Log.d("TAG","REFILL REMINDER"+currMedLeft);
                                //sendRefillNotification(10,medicine.getImage(), medicine.getName());
                                refillReminderTime(medicine.getRefillReminderTime(),medicine.getImage(),medicine.getName());
                            }
                            medicine.setMedLeft(currMedLeft-1);
                        }

                        medicine.setStatus(getString(R.string.taken));
                    }
                    updateMed(medicine);
                    dialog.dismiss();

//                WorkManager.getInstance().cancelAllWorkByTag("reschedule");
                }

            }
        });

        btnReschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker(medicine.getImage(), medicine.getName(), medicine);
                dialog.dismiss();
            }
        });

        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void datePicker(int imageResource, String medName, Medicine medicine){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Log.i(TAG, "onDateSet: " + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        schedYear = year;
                        schedMonth = monthOfYear+1;
                        schedDay = dayOfMonth;
                        timePicker(imageResource, medName, medicine);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void timePicker(int imageResource, String medName, Medicine medicine){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Log.i(TAG, "onTimeSet: " + hourOfDay + ":" + minute);
                        schedHour = hourOfDay;
                        schedMinute = minute;

                        String dateStr = schedDay + "-" + schedMonth + "-" + schedYear + "/" + schedHour + ":" + schedMinute;

                        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy/HH:mm");

                        try{

                            Date date = sdf.parse(dateStr);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            Log.i(TAG, "onTimeSet: millis: " + calendar.getTimeInMillis());
                            long schedMillis = calendar.getTimeInMillis();

                            Date currDate = new Date();
                            long currMillis = currDate.getTime();

                            long delayInMillis = schedMillis - currMillis;
                            if(delayInMillis <= 0)
                                Toast.makeText(getContext(), "You need to provide time in future", Toast.LENGTH_SHORT).show();
                            else{
                                sendRescheduleNotification(delayInMillis, imageResource, medName);
                                medicine.setStatus("Snoozed until " + schedHour + ":" + schedMinute + ", " + schedDay + "-" + schedMonth + "-" + schedYear);
                                updateMed(medicine);
                            }

                        } catch(ParseException e){
                            e.printStackTrace();
                        }

                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    private void sendRescheduleNotification(long delayInMillis, int imageResource, String medName){
        Data data = new Data.Builder()
                .putInt(MyWorkManager.IMAGE_RESOURCE, imageResource)
                .putString(MyWorkManager.MED_NAME, medName)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorkManager.class)
                .setInputData(data)
//                .setConstraints(constraints)
                .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
                .addTag("reschedule")
                .build();

        androidx.work.WorkManager.getInstance(getContext()).enqueue(request);
    }

    @Override
    public void updateMed(Medicine medicine) {
        homeMedPresenterInterface.updateMed(medicine);
    }

    @Override
    public void failedToFetchMeds(String msg) {
        Log.d(TAG, "failedToFetchMeds: " + "FAILED TO FETCH MEDICINES");
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successToFetchMeds(List<Medicine> medicines) {
        Log.d(TAG, "successToFetchMeds: "+ "INSIDE SUCCESS");
        if (medicines.size() == 0) {
            Log.d(TAG, "successToFetchMeds: " + "NO MEDICINES");
            medHomeAdapter.removeMeds();
            Toast.makeText(getContext(), "we don't have any medicines for this day", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "successToFetchMeds: "+ medicines.get(0).getName());
            Log.d(TAG, "successToFetchMeds: "+ medicines.size());
            medHomeAdapter.setMedInfo(medicines);
        }
    }

    public void oneTimeWork() {
//        WorkRequest locationUploadWorkRequest =
//                new OneTimeWorkRequest.Builder(WorkerMgr.class)
//                        .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresStorageNotLow(true).build())
//                        .build();
//        WorkManager.getInstance(MainActivity.this).enqueue(locationUploadWorkRequest);
        //--------------------------------------------------------------------------

        final PeriodicWorkRequest periodicWorkRequest1 = new PeriodicWorkRequest.Builder(WorkManMedRem.class, 1, TimeUnit.MILLISECONDS)
                .setInitialDelay(6000, TimeUnit.MILLISECONDS)
                .build();
        WorkManager workManager = WorkManager.getInstance(getContext());
        workManager.enqueue(periodicWorkRequest1);


        // to cancel worker class
        // WorkManager.getInstance(MainActivity.this).cancelWorkById(locationUploadWorkRequest.getId());
    }



    private void refillReminderTime(String refillReminderTime, int imageResource, String medName){

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String dateStop = year +"/" +month+"/" + day+" "+refillReminderTime+":00";
        Date currentTime = new Date();

        // Custom date format
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Date refillTime = null;
        try {
            refillTime = format.parse(dateStop);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Get msec from each, and subtract.
        long diff = refillTime.getTime() - currentTime.getTime();
        Log.d("TAG","Time: Delay"+diff);
        sendRefillNotification(diff,imageResource,medName);

    }

    private void sendRefillNotification(long delayInMillis, int imageResource, String medName){
        Data data = new Data.Builder()
                .putInt(RefillReminder.IMAGE_RESOURCE, imageResource)
                .putString(RefillReminder.MED_NAME, medName)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(RefillReminder.class)
                .setInputData(data)
//                .setConstraints(constraints)
                .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
                .addTag("refill")
                .build();

        androidx.work.WorkManager.getInstance(getContext()).enqueue(request);
    }

    @Override
    public void getAllStoredMedicinesOnDate(List<Medicine> medicines) {
        if (medicines.size() == 0) {
            medHomeAdapter.removeMeds();
            Toast.makeText(getContext(), "we don't have any medicines for this day", Toast.LENGTH_SHORT).show();
        } else {
            medHomeAdapter.setMedInfo(medicines);
        }
    }

    @Override
    public void getAllStoredMedicines(List<Medicine> medicines) {
        medHomeAdapter.setMedInfo(medicines);
    }


}