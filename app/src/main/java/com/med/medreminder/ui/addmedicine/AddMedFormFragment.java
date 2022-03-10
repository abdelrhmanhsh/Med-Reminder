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

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedFormFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedFormFragment";

    Button btnPill, btnSolution, btnInjection, btnPowder, btnDrops, btnInhaler, btnOther;
    ProgressBar progressBar;
    TextView textTitle;
    String incomingMedicine, type;
    JSONObject outgoingMedicine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_med_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnPill = view.findViewById(R.id.selection_pill);
//        btnSolution = view.findViewById(R.id.selection_solution);
        btnInjection = view.findViewById(R.id.selection_injection);
//        btnPowder = view.findViewById(R.id.selection_powder);
        btnDrops = view.findViewById(R.id.selection_drops);
//        btnInhaler = view.findViewById(R.id.selection_inhaler);
        btnOther = view.findViewById(R.id.selection_other);
        progressBar = view.findViewById(R.id.progress_bar);
        textTitle = view.findViewById(R.id.title);

        progressBar.setProgress(20);

        btnPill.setOnClickListener(this);
//        btnSolution.setOnClickListener(this);
        btnInjection.setOnClickListener(this);
//        btnPowder.setOnClickListener(this);
        btnDrops.setOnClickListener(this);
//        btnInhaler.setOnClickListener(this);
        btnOther.setOnClickListener(this);

        outgoingMedicine = getArgs();

    }

    private void actionSelectionPill(View view){

        type = getString(R.string.selection_pill);
        try {
            outgoingMedicine.put("form", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedFormFragmentDirections.ActionAddMedFormToStrength
                action = AddMedFormFragmentDirections.actionAddMedFormToStrength();
        action.setForm(medicine);
        Navigation.findNavController(view).navigate(action);

    }

//    private void actionSelectionSolution(View view){
//        type = getString(R.string.selection_solution);
//        try {
//            outgoingMedicine.put("form", type);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String medicine = outgoingMedicine.toString();
//
//        AddMedFormFragmentDirections.ActionAddMedFormToStrength
//                action = AddMedFormFragmentDirections.actionAddMedFormToStrength();
//        action.setForm(medicine);
//        Navigation.findNavController(view).navigate(action);
//    }

    private void actionSelectionInjection(View view){
        type = getString(R.string.selection_injection);
        try {
            outgoingMedicine.put("form", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedFormFragmentDirections.ActionAddMedFormToStrength
                action = AddMedFormFragmentDirections.actionAddMedFormToStrength();
        action.setForm(medicine);
        Navigation.findNavController(view).navigate(action);
    }

//    private void actionSelectionPowder(View view){
//        type = getString(R.string.selection_powder);
//        try {
//            outgoingMedicine.put("form", type);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String medicine = outgoingMedicine.toString();
//
//        AddMedFormFragmentDirections.ActionAddMedFormToStrength
//                action = AddMedFormFragmentDirections.actionAddMedFormToStrength();
//        action.setForm(medicine);
//        Navigation.findNavController(view).navigate(action);
//    }

    private void actionSelectionDrops(View view){
        type = getString(R.string.selection_drops);
        try {
            outgoingMedicine.put("form", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedFormFragmentDirections.ActionAddMedFormToStrength
                action = AddMedFormFragmentDirections.actionAddMedFormToStrength();
        action.setForm(medicine);
        Navigation.findNavController(view).navigate(action);
    }

//    private void actionSelectionInhaler(View view){
//        type = getString(R.string.selection_inhaler);
//        try {
//            outgoingMedicine.put("form", type);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String medicine = outgoingMedicine.toString();
//
//        AddMedFormFragmentDirections.ActionAddMedFormToStrength
//                action = AddMedFormFragmentDirections.actionAddMedFormToStrength();
//        action.setForm(medicine);
//        Navigation.findNavController(view).navigate(action);
//    }

    private void actionSelectionOther(View view){
        type = getString(R.string.selection_other);
        try {
            outgoingMedicine.put("form", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedFormFragmentDirections.ActionAddMedFormToStrength
                action = AddMedFormFragmentDirections.actionAddMedFormToStrength();
        action.setForm(medicine);
        Navigation.findNavController(view).navigate(action);
    }

    private JSONObject getArgs(){
        AddMedFormFragmentArgs args = AddMedFormFragmentArgs.fromBundle(getArguments());
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
        switch (view.getId()){
            case R.id.selection_pill:
                actionSelectionPill(view);
                break;

//            case R.id.selection_solution:
//                actionSelectionSolution(view);
//                break;

            case R.id.selection_injection:
                actionSelectionInjection(view);
                break;

//            case R.id.selection_powder:
//                actionSelectionPowder(view);
//                break;

            case R.id.selection_drops:
                actionSelectionDrops(view);
                break;

//            case R.id.selection_inhaler:
//                actionSelectionInhaler(view);
//                break;

            case R.id.selection_other:
                actionSelectionOther(view);
                break;

            default:
                Log.e(TAG, "onClick: error");

        }
    }
}