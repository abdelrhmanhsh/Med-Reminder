package com.med.medreminder.ui.homepage.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.med.medreminder.R;
import com.med.medreminder.databinding.FragmentHomeBinding;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.addmedicine.view.AddMedActivity;
import com.med.medreminder.ui.homepage.presenter.HomeMedPresenter;
import com.med.medreminder.ui.homepage.presenter.homeMedPresenterInterface;
import com.med.medreminder.ui.medicationScreen.presenter.ActivePresenter;
import com.med.medreminder.ui.medicationScreen.presenter.ActivePresenterInterface;
import com.med.medreminder.ui.medicationScreen.view.ActiveMedViewInterface;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;


public class HomeFragment extends Fragment implements onMedClickListener, homeMedViewInterface     {

    private FragmentHomeBinding binding;
    LinearLayoutManager linearLayoutManager;
    MedHomeAdapter medHomeAdapter;
    RecyclerView allMed_rv;
    FloatingActionButton addMed_floatBtn;

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

        curDate = Calendar.getInstance().getTimeInMillis();

                Log.d("TAG", "onViewCreated: " + curDate);
        Log.d("TAG", "onViewCreated: " + Calendar.getInstance().getTimeInMillis());


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                Log.d("TAG", "onDateSelected: " + date.getTimeInMillis());
                String dateString = DateFormat.format("MM/dd/yyyy", new Date(String.valueOf(date.getTime()))).toString();
                Log.d("TAG", "onDateSelected: Dateeeeeeeeeeeeeeeeeeeeeeeeee" + dateString);
                //Log.d("TAG", "onDateSelected: " + date.get(position));
                homeMedPresenterInterface.showMedsOnDate(getViewLifecycleOwner(),date.getTimeInMillis());
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
                ConcreteLocalSource.getInstance(getContext())),curDate);


        homeMedPresenterInterface.showMedsOnDate(getViewLifecycleOwner(),curDate);


        addMed_floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddMedActivity.class));
            }
        });


       // oneTimeWork();

    }

    @Override
    public void onCLick(Medicine medicine) {
        Log.d("TAG", "onCLick: " + medicine.getName());
        Toast.makeText(getContext(), "" + medicine.getName(), Toast.LENGTH_SHORT).show();
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


    @Override
    public void getAllStoredMedicinesOnDate(List<Medicine> medicines) {
        if (medicines.size() == 0) {
             medHomeAdapter.removeMeds();
            Toast.makeText(getContext(), "we don't have any medicines for this day", Toast.LENGTH_SHORT).show();
        } else {
            medHomeAdapter.setMedInfo(medicines);
        }
    }
}