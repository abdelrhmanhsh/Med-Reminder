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
import android.widget.TimePicker;

import com.med.medreminder.R;
import com.med.medreminder.model.Medicine;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedTimeFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedTimeFragment";

    Button btnNext;
    ProgressBar progressBar;
    TimePicker timePicker;
    TextView textTitle, textDoseNum;
    String doseNum;
    String doseTimes = "";
    int often = 1;
    int oftenDec;
    int doseCount = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_med_time, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnNext = view.findViewById(R.id.btn_next_time);
        progressBar = view.findViewById(R.id.progress_bar);
        timePicker = view.findViewById(R.id.time_picker);
        textTitle = view.findViewById(R.id.title);
        textDoseNum = view.findViewById(R.id.dose_num);

//        timePicker.setIs24HourView(true);
        timePicker.setHour(20);
        timePicker.setMinute(0);

        progressBar.setProgress(80);

        btnNext.setOnClickListener(this);

        setTitleText();

        if(often > 1){
            doseNum = getString(R.string.pick_time_for_dose) + " " + doseCount + ".";
            textDoseNum.setText(doseNum);
        }

    }

    private void actionNext(View view) {

        if (often == 1) {

            setTime(view);

        } else {

            if (oftenDec == 1) {
                doseNum = getString(R.string.pick_time_for_dose) + doseCount + ".";
                textDoseNum.setText(doseNum);

                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                String time = hour + ":" + minute;

                doseTimes += time;

                Medicine medicine = Medicine.getInstance();
                medicine.setTime(doseTimes);

                NavDirections action = AddMedTimeFragmentDirections.actionAddMedTimeToAlmost();
                Navigation.findNavController(view).navigate(action);

            } else {
                doseCount++;
                doseNum = getString(R.string.pick_time_for_dose) + " " + doseCount + ".";
                textDoseNum.setText(doseNum);

                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                String time = hour + ":" + minute;

                doseTimes += time + ",";
            }
        }
        oftenDec--;
    }

    private void setTime(View view){

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        String time = hour + ":" + minute;

        Medicine medicine = Medicine.getInstance();
        medicine.setTime(time);

        NavDirections action = AddMedTimeFragmentDirections.actionAddMedTimeToAlmost();
        Navigation.findNavController(view).navigate(action);

    }

    private void setTitleText(){

        Medicine medicine = Medicine.getInstance();
        String oftenStr = medicine.getOften();

        switch (oftenStr){
            case "Once Daily":
                often = 1;
                break;

            case "Twice Daily":
                often = 2;
                break;

            case "3 times a day":
                often = 3;
                break;

            default:
                Log.e(TAG, "Error handling often times");
        }

        oftenDec = often;
        textTitle.setText(medicine.getName());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_next_time)
            actionNext(view);
    }
}