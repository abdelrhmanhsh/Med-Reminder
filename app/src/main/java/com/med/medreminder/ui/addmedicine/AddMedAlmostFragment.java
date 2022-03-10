package com.med.medreminder.ui.addmedicine;

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
import com.med.medreminder.model.Medicine;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedAlmostFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedAlmostFragment";

    Button btnSetTreatmentDuration, btnGetRefillReminder, btnAddInstructions, btnChangeMedIcon, btnSave;
    ProgressBar progressBar;
    TextView textTitle;
    String incomingMedicine;
    JSONObject outgoingMedicine;

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
        btnAddInstructions = view.findViewById(R.id.btn_add_instructions);
        btnChangeMedIcon = view.findViewById(R.id.btn_change_med_icon);
        btnSave = view.findViewById(R.id.btn_almost_save);
        progressBar = view.findViewById(R.id.progress_bar);
        textTitle = view.findViewById(R.id.description);

        progressBar.setProgress(90);

        btnSetTreatmentDuration.setOnClickListener(this);
        btnGetRefillReminder.setOnClickListener(this);
        btnAddInstructions.setOnClickListener(this);
        btnChangeMedIcon.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        outgoingMedicine = getArgs();

    }

    private JSONObject getArgs(){
        AddMedAlmostFragmentArgs args = AddMedAlmostFragmentArgs.fromBundle(getArguments());
        incomingMedicine = args.getAlmost();

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

    private void actionSetTreatmentDuration(View view){
        Toast.makeText(getContext(), "Set Duration Treatment", Toast.LENGTH_SHORT).show();
    }

    private void actionGetRefillReminder(View view){
        Toast.makeText(getContext(), "Get Refill Reminder", Toast.LENGTH_SHORT).show();
    }

    private void actionAddInstructions(View view){
        Toast.makeText(getContext(), "Add Instructions", Toast.LENGTH_SHORT).show();
    }

    private void actionChangeMedIcon(View view){
        Toast.makeText(getContext(), "Change Med Icon", Toast.LENGTH_SHORT).show();
    }

    private void actionSave(View view){

        try {

            String name = outgoingMedicine.getString("name");
            String form = outgoingMedicine.getString("form");
            String strength = outgoingMedicine.getString("strength");
            String reason = outgoingMedicine.getString("reason");
            String isDaily = outgoingMedicine.getString("isDaily");
            String often = "Only As Needed";
            String time = "Only As Needed";
//            String often = outgoingMedicine.getString("often");
//            String when = outgoingMedicine.getString("when");
//            String time = outgoingMedicine.getString("time");

            if(outgoingMedicine.has("often") && !outgoingMedicine.isNull("often")){
                often = outgoingMedicine.getString("often");
            }
            if(outgoingMedicine.has("time") && !outgoingMedicine.isNull("time")){
                time = outgoingMedicine.getString("time");
            }

            Medicine medicine = new Medicine(name, form, strength, reason, isDaily, often, time);
            Log.i(TAG, "actionSave: medicine save: " + medicine.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
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

            case R.id.btn_add_instructions:
                actionAddInstructions(view);
                break;

            case R.id.btn_change_med_icon:
                actionChangeMedIcon(view);
                break;

            case R.id.btn_almost_save:
                actionSave(view);
                break;

            default:
                Log.e(TAG, "onClick: error");

        }
    }
}