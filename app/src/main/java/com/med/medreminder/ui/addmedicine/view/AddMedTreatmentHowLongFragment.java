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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.med.medreminder.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedTreatmentHowLongFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedTreatmentHowLongFragment";

    TextView textTitle;
    ProgressBar progressBar;
    Button btn30Days, btn1Week, btn10Days, btn5Days, btnSetEndDate, btnOngoingTreatment;
    String incomingMedicine;
    JSONObject outgoingMedicine;

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
        btn30Days = view.findViewById(R.id.selection_thirty_days);
        btn1Week = view.findViewById(R.id.selection_one_week);
        btn10Days = view.findViewById(R.id.selection_ten_days);
        btn5Days = view.findViewById(R.id.selection_five_days);
        btnSetEndDate = view.findViewById(R.id.selection_set_end_date);
        btnOngoingTreatment = view.findViewById(R.id.selection_ongoing_treatment);

        progressBar.setProgress(90);
        btn30Days.setOnClickListener(this);
        btn1Week.setOnClickListener(this);
        btn10Days.setOnClickListener(this);
        btn5Days.setOnClickListener(this);
        btnSetEndDate.setOnClickListener(this);
        btnOngoingTreatment.setOnClickListener(this);

        outgoingMedicine = getArgs();
    }


    private void actionSetEndDate(View view){

        String medicine = outgoingMedicine.toString();

        AddMedTreatmentHowLongFragmentDirections.ActionAddMedTreatmentHowLongToEndDate
                action = AddMedTreatmentHowLongFragmentDirections.actionAddMedTreatmentHowLongToEndDate();
        action.setTreatmentDuration(medicine);
        Navigation.findNavController(view).navigate(action);
    }

    private void actionSetTreatmentEndDate(View view, String endDate){
        try {
            outgoingMedicine.put("end_date", endDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedTreatmentHowLongFragmentDirections.ActionAddMedTreatmentHowLongToAlmost
                action = AddMedTreatmentHowLongFragmentDirections.actionAddMedTreatmentHowLongToAlmost();
        action.setAlmost(medicine);
        Navigation.findNavController(view).navigate(action);
    }

    private JSONObject getArgs(){

        AddMedTreatmentHowLongFragmentArgs args =
                AddMedTreatmentHowLongFragmentArgs.fromBundle(getArguments());
        incomingMedicine = args.getStartDate();

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
        switch (view.getId()){
            case R.id.selection_thirty_days:
                actionSetTreatmentEndDate(view, getString(R.string.selection_thirty_days));
                break;

            case R.id.selection_one_week:
                actionSetTreatmentEndDate(view, getString(R.string.selection_one_week));
                break;

            case R.id.selection_ten_days:
                actionSetTreatmentEndDate(view, getString(R.string.selection_ten_days));
                break;

            case R.id.selection_five_days:
                actionSetTreatmentEndDate(view, getString(R.string.selection_five_days));
                break;

            case R.id.selection_set_end_date:
                actionSetEndDate(view);
                break;

            case R.id.selection_ongoing_treatment:
                actionSetTreatmentEndDate(view, getString(R.string.selection_ongoing_treatment));
                break;
        }
    }
}