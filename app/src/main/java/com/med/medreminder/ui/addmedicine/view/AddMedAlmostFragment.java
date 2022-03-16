package com.med.medreminder.ui.addmedicine.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.med.medreminder.R;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.addmedicine.presenter.AddMedPresenter;
import com.med.medreminder.ui.addmedicine.presenter.AddMedPresenterInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddMedAlmostFragment extends Fragment implements View.OnClickListener, AddMedViewInterface {

    public static final String TAG = "AddMedAlmostFragment";

    Button btnSetTreatmentDuration, btnGetRefillReminder, btnAddInstructions, btnChangeMedIcon, btnSave;
    ProgressBar progressBar;
    TextView textTitle;

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
                ConcreteLocalSource.getInstance(getContext())));

        progressBar.setProgress(90);

        btnSetTreatmentDuration.setOnClickListener(this);
        btnGetRefillReminder.setOnClickListener(this);
//        btnAddInstructions.setOnClickListener(this);
        btnChangeMedIcon.setOnClickListener(this);
        btnSave.setOnClickListener(this);

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

            Date date = new Date();
            long timeMillis = date.getTime();

            filledMed.setStartDateMillis(timeMillis);

        }
        if(filledMed.getEndDate()==null){
            filledMed.setEndDate(getString(R.string.selection_ongoing_treatment));
        }
        if(filledMed.getImage()==0){
            filledMed.setImage(R.drawable.ic_medicine_other);
        }


        Medicine medicine = new Medicine(0, filledMed.getName(), filledMed.getForm(), filledMed.getStrength(),
                filledMed.getReason(), filledMed.getIsDaily(), filledMed.getOften(), filledMed.getTime(),
                filledMed.getStartDate(), filledMed.getEndDate(),filledMed.getStartDateMillis(),
                filledMed.getEndDateMillis(), filledMed.getMedLeft(), filledMed.getRefillLimit(),
                filledMed.getImage(),filledMed.getStatus());

        Log.i(TAG, "actionSave: medicine save: " + medicine.toString());
        addMed(medicine);

        // Set reminders HERE ------------------------------------------------------------


        filledMed.setOften(getString(R.string.selection_only_as_needed));
        filledMed.setTime("");
        String todayDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        filledMed.setStartDate(todayDate);

        Date date = new Date();
        long timeMillis = date.getTime();

        filledMed.setStartDateMillis(timeMillis);

        filledMed.setEndDate(getString(R.string.selection_ongoing_treatment));
        filledMed.setEndDateMillis(0);
        filledMed.setImage(R.drawable.ic_medicine_other);

        filledMed.setMedLeft(0);
        filledMed.setRefillLimit(0);

    }

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
        getActivity().finish();
    }
}