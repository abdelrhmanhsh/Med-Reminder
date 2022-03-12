package com.med.medreminder.ui.addmedicine.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.med.medreminder.R;
import com.med.medreminder.model.Medicine;

public class AddMedTreatmentHowLongFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedTreatmentHowLongFragment";

    TextView textTitle;
    ProgressBar progressBar;
    Button btn30Days, btn1Week, btn10Days, btn5Days, btnSetEndDate, btnOngoingTreatment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_med_treatment_how_long, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textTitle = view.findViewById(R.id.title);
        progressBar = view.findViewById(R.id.progress_bar);
//        btn30Days = view.findViewById(R.id.selection_thirty_days);
//        btn1Week = view.findViewById(R.id.selection_one_week);
//        btn10Days = view.findViewById(R.id.selection_ten_days);
//        btn5Days = view.findViewById(R.id.selection_five_days);
        btnSetEndDate = view.findViewById(R.id.selection_set_end_date);
        btnOngoingTreatment = view.findViewById(R.id.selection_ongoing_treatment);

        progressBar.setProgress(90);
//        btn30Days.setOnClickListener(this);
//        btn1Week.setOnClickListener(this);
//        btn10Days.setOnClickListener(this);
//        btn5Days.setOnClickListener(this);
        btnSetEndDate.setOnClickListener(this);
        btnOngoingTreatment.setOnClickListener(this);

        setTitleText();
    }


    private void actionSetEndDate(View view){
        NavDirections action = AddMedTreatmentHowLongFragmentDirections.actionAddMedTreatmentHowLongToEndDate();
        Navigation.findNavController(view).navigate(action);
    }

    private void actionSetOngoing(View view, String endDate){

        Medicine medicine = Medicine.getInstance();
        medicine.setEndDate(endDate);

        NavDirections action = AddMedTreatmentHowLongFragmentDirections.actionAddMedTreatmentHowLongToAlmost();
        Navigation.findNavController(view).navigate(action);

    }

    private void setTitleText(){
        Medicine medicine = Medicine.getInstance();
        textTitle.setText(medicine.getName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.selection_set_end_date:
                actionSetEndDate(view);
                break;

            case R.id.selection_ongoing_treatment:
                actionSetOngoing(view, getString(R.string.selection_ongoing_treatment));
                break;
        }
    }
}