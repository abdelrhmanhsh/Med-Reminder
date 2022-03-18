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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.med.medreminder.R;
import com.med.medreminder.model.Medicine;

public class AddMedRefillRemindTimeFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedRefillRemindTimeFragment";

    TextView textTitle;
    ProgressBar progressBar;
    Button btnSet;
    TimePicker timePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_med_refill_remind_time, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textTitle = view.findViewById(R.id.title);
        progressBar = view.findViewById(R.id.progress_bar);
        btnSet = view.findViewById(R.id.btn_set_refill_reminder);
        timePicker = view.findViewById(R.id.time_picker);
        timePicker.setHour(20);
        timePicker.setMinute(0);

        progressBar.setProgress(90);
        btnSet.setOnClickListener(this);

        setTitleText();

    }

    private void setTitleText(){
        Medicine medicine = Medicine.getInstance();
        textTitle.setText(medicine.getName());
    }

    private void actionSet(View view){

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        String time = hour + ":" + minute;

        Medicine medicine = Medicine.getInstance();
        medicine.setRefillReminderTime(time);

        NavDirections action = AddMedRefillRemindTimeFragmentDirections.actionAddMedRefillRemindTimeToAlmost();
        Navigation.findNavController(view).navigate(action);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_set_refill_reminder)
            actionSet(view);
    }
}