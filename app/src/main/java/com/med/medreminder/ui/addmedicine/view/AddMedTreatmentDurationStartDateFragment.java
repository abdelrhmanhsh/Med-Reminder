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

public class AddMedTreatmentDurationStartDateFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedTreatmentDurationStartDateFragment";

    TextView textTitle;
    ProgressBar progressBar;
    DatePicker datePicker;
    Button btnNext;
    String incomingMedicine;
    JSONObject outgoingMedicine;

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

        outgoingMedicine = getArgs();

    }

    private void actionNext(View view){

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String date = day + "-" + month + "-" + year;

        try {
            outgoingMedicine.put("start_date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedTreatmentDurationStartDateFragmentDirections.ActionAddMedTreatmentStartDateToTreatmentHowLong
                action = AddMedTreatmentDurationStartDateFragmentDirections.actionAddMedTreatmentStartDateToTreatmentHowLong();
        action.setStartDate(medicine);
        Navigation.findNavController(view).navigate(action);
    }

    private JSONObject getArgs(){
        AddMedTreatmentDurationStartDateFragmentArgs args =
                AddMedTreatmentDurationStartDateFragmentArgs.fromBundle(getArguments());
        incomingMedicine = args.getMedicine();

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

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_next_treatment_start)
            actionNext(view);
    }
}