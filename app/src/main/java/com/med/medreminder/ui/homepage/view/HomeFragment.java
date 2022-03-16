package com.med.medreminder.ui.homepage.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.med.medreminder.R;
import com.med.medreminder.databinding.FragmentHomeBinding;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseWork;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;


public class HomeFragment extends Fragment implements onMedClickListener, homeMedViewInterface, CalendarHomeAdapter.OnItemListener {

    public static final String TAG = "HomeFragment";

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
                ConcreteLocalSource.getInstance(getContext()), FirebaseWork.getInstance()));

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


    // ------------------------------------

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
                bundle.putInt("med_id", medicine.getId());
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

                if (medicine.getStatus().equals(getString(R.string.taken))) // perform un-take
                    medicine.setStatus("");
                else {
                    int currMedLeft = medicine.getMedLeft();
                    if(currMedLeft <= 0)
                        Toast.makeText(getContext(), "You have no med left!\nPlease consider refill!", Toast.LENGTH_SHORT).show();
                    else
                        medicine.setMedLeft(currMedLeft-1);

                    medicine.setStatus(getString(R.string.taken));
                }
                updateMed(medicine);
                dialog.dismiss();
            }
        });
        
        btnReschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Reschedule", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


//        textMedRemaining.setText("You have " + med.getMedLeft() + " meds remaining");
        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    // ----------------------------------------------


    @Override
    public void getAllStoredMedicines(List<Medicine> medicines) {
        medHomeAdapter.setMedInfo(medicines);
    }

    @Override
    public void updateMed(Medicine medicine) {
        homeMedPresenterInterface.updateMed(medicine);
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