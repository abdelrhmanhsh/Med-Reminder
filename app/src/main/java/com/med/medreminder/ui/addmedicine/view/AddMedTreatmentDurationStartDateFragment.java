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

public class AddMedTreatmentDurationStartDateFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedTreatmentDurationStartDateFragment";

    TextView textTitle;
    ProgressBar progressBar;
    DatePicker datePicker;
    Button btnNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_med_treatment_start_date, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textTitle = view.findViewById(R.id.title);
        progressBar = view.findViewById(R.id.progress_bar);
        datePicker = view.findViewById(R.id.date_picker);
        btnNext = view.findViewById(R.id.btn_next_treatment_start);

        progressBar.setProgress(90);
        btnNext.setOnClickListener(this);

        setTitleText();

    }

    private void setTitleText(){
        Medicine medicine = Medicine.getInstance();
        textTitle.setText(medicine.getName());
    }

    private void actionNext(View view){

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String dateStr = day + "-" + month + "-" + year;

        Medicine medicine = Medicine.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");

        try{

            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            medicine.setStartDateMillis(calendar.getTimeInMillis());

        }catch(ParseException e){
            e.printStackTrace();
        }

        medicine.setStartDate(dateStr);

        NavDirections action = AddMedTreatmentDurationStartDateFragmentDirections.actionAddMedTreatmentStartDateToTreatmentHowLong();
        Navigation.findNavController(view).navigate(action);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_next_treatment_start)
            actionNext(view);
    }
}