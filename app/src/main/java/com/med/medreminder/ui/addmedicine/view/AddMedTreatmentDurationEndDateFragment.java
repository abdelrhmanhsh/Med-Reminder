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
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.med.medreminder.R;
import com.med.medreminder.model.Medicine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddMedTreatmentDurationEndDateFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedTreatmentDurationEndDateFragment";

    TextView textTitle;
    ProgressBar progressBar;
    DatePicker datePicker;
    Button btnSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_med_treatment_duration_end_date, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textTitle = view.findViewById(R.id.title);
        progressBar = view.findViewById(R.id.progress_bar);
        btnSet = view.findViewById(R.id.btn_set_end_date);
        datePicker = view.findViewById(R.id.date_picker);

        progressBar.setProgress(90);
        btnSet.setOnClickListener(this);

        setTitleText();

    }

    private void actionSetEndDate(View view){

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth()+1;
        int year = datePicker.getYear();

        String dateStr = day + "-" + month + "-" + year;

        Medicine medicine = Medicine.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {

            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            medicine.setEndDateMillis(calendar.getTimeInMillis());

        } catch(ParseException e){
            e.printStackTrace();
        }

        medicine.setEndDate(dateStr);

        NavDirections action = AddMedTreatmentDurationEndDateFragmentDirections.actionAddMedTreatmentDurationEndDateToAlmost();
        Navigation.findNavController(view).navigate(action);

    }

    private void setTitleText(){
        Medicine medicine = Medicine.getInstance();
        textTitle.setText(medicine.getName());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_set_end_date)
            actionSetEndDate(view);
    }
}