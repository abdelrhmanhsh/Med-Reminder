package com.med.medreminder.ui.homepage.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import java.time.LocalDate;
import java.util.ArrayList;
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


public class HomeFragment extends Fragment implements onMedClickListener, homeMedViewInterface, CalendarHomeAdapter.OnItemListener {

    private FragmentHomeBinding binding;
    LinearLayoutManager linearLayoutManager;
    MedHomeAdapter medHomeAdapter;
    RecyclerView allMed_rv;
    FloatingActionButton addMed_floatBtn;
    //ArrayList<Medicine> medicines;

    ImageView back_ic;
    ImageView next_ic;
    RecyclerView days_rv;
    TextView monthYearTV;

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

        CalendarUtils.selectedDate = LocalDate.now();
        monthYearTV = view.findViewById(R.id.monthYearTV);
        days_rv = view.findViewById(R.id.days_rv);
        back_ic = view.findViewById(R.id.back_ic);
        next_ic = view.findViewById(R.id.next_ic);

        back_ic.setOnClickListener(view1 -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
            setMonthView();
        });

        next_ic.setOnClickListener(view1 -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
            setMonthView();
        });

        setMonthView();

       // medicines = new ArrayList<>();

//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));
//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));
//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));
//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));
//        medicines.add(new Medicine(R.drawable.pill_ic,"Vitamic C","Pill(s)","250","10:00","AM","gm","1"));

        allMed_rv.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        allMed_rv.setLayoutManager(linearLayoutManager);
        medHomeAdapter = new MedHomeAdapter(getContext(), this);
        allMed_rv.setAdapter(medHomeAdapter);

        homeMedPresenterInterface = new HomeMedPresenter(this, Repository.getInstance(getContext(),
                ConcreteLocalSource.getInstance(getContext())));

        Log.d("TAG", "HomeFragment: " + getViewLifecycleOwner());

        homeMedPresenterInterface.showAllStoredMedicines(getViewLifecycleOwner());
        Log.d("TAG", "onViewCreated: " + 1);

        addMed_floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddMedActivity.class));
            }
        });
        Log.d("TAG", "onViewCreated: " + 2);

        oneTimeWork();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        monthYearTV.setText(CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = CalendarUtils.daysInMonthArray(CalendarUtils.selectedDate);
        CalendarHomeAdapter calendarAdapter = new CalendarHomeAdapter(daysInMonth, (CalendarHomeAdapter.OnItemListener) this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        days_rv.setLayoutManager(layoutManager);
        days_rv.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date) {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    @Override
    public void onCLick(Medicine medicine) {
        Log.d("TAG", "onCLick: " + medicine.getName());
        Toast.makeText(getContext(), "" + medicine.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getAllStoredMedicines(List<Medicine> medicines) {
        medHomeAdapter.setMedInfo(medicines);
    }

    public void oneTimeWork() {
//        WorkRequest locationUploadWorkRequest =
//                new OneTimeWorkRequest.Builder(WorkerMgr.class)
//                        .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresStorageNotLow(true).build())
//                        .build();
//        WorkManager.getInstance(MainActivity.this).enqueue(locationUploadWorkRequest);
        //--------------------------------------------------------------------------

        final PeriodicWorkRequest periodicWorkRequest1 = new PeriodicWorkRequest.Builder(WorkManMedRem.class,1, TimeUnit.MILLISECONDS)
                .setInitialDelay(6000,TimeUnit.MILLISECONDS)
                .build();
        WorkManager workManager =  WorkManager.getInstance(getContext());
        workManager.enqueue(periodicWorkRequest1);


        // to cancel worker class
        // WorkManager.getInstance(MainActivity.this).cancelWorkById(locationUploadWorkRequest.getId());
    }
}