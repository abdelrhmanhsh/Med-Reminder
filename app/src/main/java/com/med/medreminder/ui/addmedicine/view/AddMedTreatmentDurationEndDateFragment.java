package com.med.medreminder.ui.addmedicine.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.med.medreminder.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedTreatmentDurationEndDateFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedTreatmentDurationEndDateFragment";

    TextView textTitle;
    ProgressBar progressBar;
    DatePicker datePicker;
    Button btnSet;
    String incomingMedicine;
    JSONObject outgoingMedicine;

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

        outgoingMedicine = getArgs();

    }

    private JSONObject getArgs(){

        AddMedTreatmentDurationEndDateFragmentArgs args =
                AddMedTreatmentDurationEndDateFragmentArgs.fromBundle(getArguments());
        incomingMedicine = args.getTreatmentDuration();

        Log.i(TAG, "getArgs: " + incomingMedicine);

        JSONObject incomingJson = null;

        try {
            incomingJson = new JSONObject(incomingMedicine);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String title = "Unknown";
        try {
            title = incomingJson.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textTitle.setText(title);
        return incomingJson;
    }


    private void actionSetEndDate(View view){

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String date = day + "-" + month + "-" + year;

        try {
            outgoingMedicine.put("end_date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedTreatmentDurationEndDateFragmentDirections.ActionAddMedTreatmentDurationEndDateToAlmost
                action = AddMedTreatmentDurationEndDateFragmentDirections.actionAddMedTreatmentDurationEndDateToAlmost();
        action.setAlmost(medicine);
        Navigation.findNavController(view).navigate(action);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_set_end_date)
            actionSetEndDate(view);
    }
}