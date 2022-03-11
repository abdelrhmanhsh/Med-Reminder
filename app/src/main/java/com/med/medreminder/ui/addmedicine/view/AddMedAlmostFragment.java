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

import com.google.android.material.snackbar.Snackbar;
import com.med.medreminder.R;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.addmedicine.presenter.AddMedPresenter;
import com.med.medreminder.ui.addmedicine.presenter.AddMedPresenterInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedAlmostFragment extends Fragment implements View.OnClickListener, AddMedViewInterface {

    public static final String TAG = "AddMedAlmostFragment";

    Button btnSetTreatmentDuration, btnGetRefillReminder, btnAddInstructions, btnChangeMedIcon, btnSave;
    ProgressBar progressBar;
    TextView textTitle;
    String incomingMedicine;
    JSONObject outgoingMedicine;

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

        presenterInterface = new AddMedPresenter(this,
                Repository.getInstance(getContext(),  ConcreteLocalSource.getInstance(getContext())));

        progressBar.setProgress(90);

        btnSetTreatmentDuration.setOnClickListener(this);
        btnGetRefillReminder.setOnClickListener(this);
//        btnAddInstructions.setOnClickListener(this);
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

        String medicine = outgoingMedicine.toString();

        AddMedAlmostFragmentDirections.ActionAddMedAlmostToTreatmentStartDate
                action = AddMedAlmostFragmentDirections.actionAddMedAlmostToTreatmentStartDate();
        action.setMedicine(medicine);
        Navigation.findNavController(view).navigate(action);
    }

    private void actionGetRefillReminder(View view){
        String medicine = outgoingMedicine.toString();

        AddMedAlmostFragmentDirections.ActionAddMedAlmostToRefillLeft
                action = AddMedAlmostFragmentDirections.actionAddMedAlmostToRefillLeft();
        action.setMedicine(medicine);
        Navigation.findNavController(view).navigate(action);
    }

//    private void actionAddInstructions(View view){
////        NavDirections directions =  AddMedAlmostFragmentDirections.actionAddMedAlmostToInstructionsFood();
////        Navigation.findNavController(view).navigate(directions);
//        Toast.makeText(getContext(), "Add Instructions", Toast.LENGTH_SHORT).show();
//    }

    private void actionChangeMedIcon(View view){
        String medicine = outgoingMedicine.toString();

        AddMedAlmostFragmentDirections.ActionAddMedAlmostToChangeIcon
                action = AddMedAlmostFragmentDirections.actionAddMedAlmostToChangeIcon();
        action.setMedicine(medicine);
        Navigation.findNavController(view).navigate(action);
    }

    private void actionSave(){

        try {

            String name = outgoingMedicine.getString("name");
            String form = outgoingMedicine.getString("form");
            String strength = outgoingMedicine.getString("strength");
            String reason = outgoingMedicine.getString("reason");
            String isDaily = outgoingMedicine.getString("isDaily");
            String often = getString(R.string.selection_only_as_needed);
            String time = getString(R.string.selection_only_as_needed);
            String startDate = getString(R.string.selection_only_as_needed);
            String endDate = getString(R.string.selection_only_as_needed);
            String medLeft = getString(R.string.selection_only_as_needed);
            String refillLimit = getString(R.string.selection_only_as_needed);
            int medIcon = R.drawable.ic_medicine_other;

            // check for nullable columns
            if(outgoingMedicine.has("often") && !outgoingMedicine.isNull("often")){
                often = outgoingMedicine.getString("often");
            }
            if(outgoingMedicine.has("time") && !outgoingMedicine.isNull("time")){
                time = outgoingMedicine.getString("time");
            }
            if(outgoingMedicine.has("start_date") && !outgoingMedicine.isNull("start_date")){
                startDate = outgoingMedicine.getString("start_date");
            }
            if(outgoingMedicine.has("end_date") && !outgoingMedicine.isNull("end_date")){
                endDate = outgoingMedicine.getString("end_date");
            }
            if(outgoingMedicine.has("med_left") && !outgoingMedicine.isNull("med_left")){
                medLeft = outgoingMedicine.getString("med_left");
            }
            if(outgoingMedicine.has("refill_limit") && !outgoingMedicine.isNull("refill_limit")){
                refillLimit = outgoingMedicine.getString("refill_limit");
            }
            if(outgoingMedicine.has("image") && !outgoingMedicine.isNull("image")){
                medIcon = outgoingMedicine.getInt("image");
            }

            Medicine medicine = new Medicine(0, name, form, strength, reason, isDaily, often, time,
                    startDate, endDate, medLeft, refillLimit, medIcon);
            Log.i(TAG, "actionSave: medicine save: " + medicine.toString());
            addMed(medicine);

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

//            case R.id.btn_add_instructions:
//                actionAddInstructions(view);
//                break;

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
    }
}