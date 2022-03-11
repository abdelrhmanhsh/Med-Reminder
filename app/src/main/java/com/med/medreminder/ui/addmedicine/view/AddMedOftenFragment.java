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

import com.med.medreminder.R;

import org.json.JSONException;
import org.json.JSONObject;

// if more than once repeat the next two fragments
public class AddMedOftenFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedOftenFragment";

    Button btnOnceDaily, btnTwiceDaily, btnThreeTimes, btnFourTimes,
            btnSixTimes, btnEverySixHours, btnOnlyAsNeeded;
    ProgressBar progressBar;
    TextView textTitle;
    String incomingMedicine, often;
    JSONObject outgoingMedicine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_med_often, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnOnceDaily = view.findViewById(R.id.selection_once_daily);
        btnTwiceDaily = view.findViewById(R.id.selection_twice_daily);
        btnThreeTimes = view.findViewById(R.id.selection_three_times);
//        btnFourTimes = view.findViewById(R.id.selection_four_times);
//        btnSixTimes = view.findViewById(R.id.selection_six_times);
//        btnEverySixHours = view.findViewById(R.id.selection_every_six_hours);
        btnOnlyAsNeeded = view.findViewById(R.id.selection_only_as_needed);
        progressBar = view.findViewById(R.id.progress_bar);
        textTitle = view.findViewById(R.id.title);

        progressBar.setProgress(60);

        btnOnceDaily.setOnClickListener(this);
        btnTwiceDaily.setOnClickListener(this);
        btnThreeTimes.setOnClickListener(this);
//        btnFourTimes.setOnClickListener(this);
//        btnSixTimes.setOnClickListener(this);
//        btnEverySixHours.setOnClickListener(this);
        btnOnlyAsNeeded.setOnClickListener(this);

        outgoingMedicine = getArgs();

    }

    private JSONObject getArgs(){
        AddMedOftenFragmentArgs args = AddMedOftenFragmentArgs.fromBundle(getArguments());
        incomingMedicine = args.getDaily();
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

    private void actionSelectionOnceDaily(View view){

        often = getString(R.string.selection_once_daily);

        try {
            outgoingMedicine.put("often", often);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedOftenFragmentDirections.ActionAddMedOftenToTime
                action = AddMedOftenFragmentDirections.actionAddMedOftenToTime();
        action.setOften(medicine);
        Navigation.findNavController(view).navigate(action);
    }

    private void actionSelectionTwiceDaily(View view){
        often = getString(R.string.selection_twice_daily);

        try {
            outgoingMedicine.put("often", often);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedOftenFragmentDirections.ActionAddMedOftenToTime
                action = AddMedOftenFragmentDirections.actionAddMedOftenToTime();
        action.setOften(medicine);
        Navigation.findNavController(view).navigate(action);
    }

    private void actionSelectionThreeTimes(View view){
        often = getString(R.string.selection_three_times);

        try {
            outgoingMedicine.put("often", often);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedOftenFragmentDirections.ActionAddMedOftenToTime
                action = AddMedOftenFragmentDirections.actionAddMedOftenToTime();
        action.setOften(medicine);
        Navigation.findNavController(view).navigate(action);
    }

//    private void actionSelectionFourTimes(View view){
//        often = getString(R.string.selection_four_times);
//
//        try {
//            outgoingMedicine.put("often", often);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String medicine = outgoingMedicine.toString();
//
//        AddMedOftenFragmentDirections.ActionAddMedOftenToWhen
//                action = AddMedOftenFragmentDirections.actionAddMedOftenToWhen();
//        action.setOften(medicine);
//        Navigation.findNavController(view).navigate(action);
//    }
//
//    private void actionSelectionSixTimes(View view){
//        often = getString(R.string.selection_six_times);
//
//        try {
//            outgoingMedicine.put("often", often);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String medicine = outgoingMedicine.toString();
//
//        AddMedOftenFragmentDirections.ActionAddMedOftenToWhen
//                action = AddMedOftenFragmentDirections.actionAddMedOftenToWhen();
//        action.setOften(medicine);
//        Navigation.findNavController(view).navigate(action);
//    }
//
//    private void actionSelectionEverySixHours(View view){
//        often = getString(R.string.selection_every_six_hours);
//
//        try {
//            outgoingMedicine.put("often", often);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String medicine = outgoingMedicine.toString();
//
//        AddMedOftenFragmentDirections.ActionAddMedOftenToWhen
//                action = AddMedOftenFragmentDirections.actionAddMedOftenToWhen();
//        action.setOften(medicine);
//        Navigation.findNavController(view).navigate(action);
//    }

    private void actionSelectionOnlyAsNeeded(View view){
        often = getString(R.string.selection_only_as_needed);

        try {
            outgoingMedicine.put("often", often);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedOftenFragmentDirections.ActionAddMedOftenToAlmost
                action = AddMedOftenFragmentDirections.actionAddMedOftenToAlmost();
        action.setAlmost(medicine);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.selection_once_daily:
                actionSelectionOnceDaily(view);
                break;
            case R.id.selection_twice_daily:
                actionSelectionTwiceDaily(view);
                break;

            case R.id.selection_three_times:
                actionSelectionThreeTimes(view);
                break;

//            case R.id.selection_four_times:
//                actionSelectionFourTimes(view);
//                break;
//
//            case R.id.selection_six_times:
//                actionSelectionSixTimes(view);
//                break;
//
//            case R.id.selection_every_six_hours:
//                actionSelectionEverySixHours(view);
//                break;

            case R.id.selection_only_as_needed:
                actionSelectionOnlyAsNeeded(view);
                break;

        }
    }
}