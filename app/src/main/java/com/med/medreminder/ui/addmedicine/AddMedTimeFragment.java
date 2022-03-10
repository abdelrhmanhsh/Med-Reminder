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
import android.widget.TimePicker;
import android.widget.Toast;

import com.med.medreminder.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedTimeFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedTimeFragment";

    Button btnNext;
    ProgressBar progressBar;
    TimePicker timePicker;
    TextView textTitle;
    String incomingMedicine;
    JSONObject outgoingMedicine;

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

        timePicker.setIs24HourView(true);
        timePicker.setHour(20);
        timePicker.setMinute(0);

        progressBar.setProgress(80);

        btnNext.setOnClickListener(this);

        outgoingMedicine = getArgs();

    }

    private void actionNext(View view){
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        String time = hour + ":" + minute;

        try {
            outgoingMedicine.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String medicine = outgoingMedicine.toString();

        AddMedTimeFragmentDirections.ActionAddMedTimeToAlmost
                action = AddMedTimeFragmentDirections.actionAddMedTimeToAlmost();
        action.setAlmost(medicine);
        Navigation.findNavController(view).navigate(action);

    }

    private JSONObject getArgs(){
        AddMedTimeFragmentArgs args = AddMedTimeFragmentArgs.fromBundle(getArguments());
        incomingMedicine = args.getOften();

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
        if (view.getId() == R.id.btn_next_time)
            actionNext(view);
    }
}