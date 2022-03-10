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

public class AddMedDailyFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedDailyFragment";

    Button btnYes, btnNo, btnOnlyAsNeeded;
    ProgressBar progressBar;
    TextView textTitle;
    String incomingMedicine;
    JSONObject outgoingMedicine;
    String isDaily;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_med_daily, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnYes = view.findViewById(R.id.selection_yes);
//        btnNo = view.findViewById(R.id.selection_no);
        btnOnlyAsNeeded = view.findViewById(R.id.selection_only_as_needed);
        progressBar = view.findViewById(R.id.progress_bar);
        textTitle = view.findViewById(R.id.title);

        progressBar.setProgress(50);

        btnYes.setOnClickListener(this);
//        btnNo.setOnClickListener(this);
        btnOnlyAsNeeded.setOnClickListener(this);

        outgoingMedicine = getArgs();

    }

    private JSONObject getArgs(){
        AddMedDailyFragmentArgs args = AddMedDailyFragmentArgs.fromBundle(getArguments());
        incomingMedicine = args.getReason();
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

    private void actionSelectionYes(View view){
        isDaily = getString(R.string.selection_yes);

        try {
            outgoingMedicine.put("isDaily", isDaily);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedDailyFragmentDirections.ActionAddMedDailyToOften
                action = AddMedDailyFragmentDirections.actionAddMedDailyToOften();
        action.setDaily(medicine);
        Navigation.findNavController(view).navigate(action);
    }

//    private void actionSelectionNo(View view){
//        isDaily = getString(R.string.selection_no);
//
//        try {
//            outgoingMedicine.put("isDaily", isDaily);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String medicine = outgoingMedicine.toString();
//
//        AddMedDailyFragmentDirections.ActionAddMedDailyToOften
//                action = AddMedDailyFragmentDirections.actionAddMedDailyToOften();
//        action.setDaily(medicine);
//        Navigation.findNavController(view).navigate(action);
//    }

    private void actionSelectionOnlyAsNeeded(View view){

        isDaily = getString(R.string.selection_only_as_needed);

        try {
            outgoingMedicine.put("isDaily", isDaily);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedDailyFragmentDirections.ActionAddMedDailyToAlmost
                action = AddMedDailyFragmentDirections.actionAddMedDailyToAlmost();
        action.setAlmost(medicine);
        Navigation.findNavController(view).navigate(action);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.selection_yes:
                actionSelectionYes(view);
                break;
//            case R.id.selection_no:
//                actionSelectionNo(view);
//                break;

            case R.id.selection_only_as_needed:
                actionSelectionOnlyAsNeeded(view);
                break;
        }
    }
}