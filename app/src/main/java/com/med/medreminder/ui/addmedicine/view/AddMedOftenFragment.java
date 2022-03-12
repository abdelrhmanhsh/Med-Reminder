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
import com.med.medreminder.model.Medicine;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedOftenFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedOftenFragment";

    Button btnOnceDaily, btnTwiceDaily, btnThreeTimes, btnFourTimes,
            btnSixTimes, btnEverySixHours, btnOnlyAsNeeded;
    ProgressBar progressBar;
    TextView textTitle;

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

        setTitleText();

    }

    private void actionSetOften(View view, String often){

        Medicine medicine = Medicine.getInstance();
        medicine.setOften(often);

        NavDirections action = AddMedOftenFragmentDirections.actionAddMedOftenToTime();
        Navigation.findNavController(view).navigate(action);

    }

    private void actionSetOnlyAsNeeded(View view, String often){

        Medicine medicine = Medicine.getInstance();
        medicine.setOften(often);

        NavDirections action = AddMedOftenFragmentDirections.actionAddMedOftenToAlmost();
        Navigation.findNavController(view).navigate(action);

    }

    private void setTitleText(){
        Medicine medicine = Medicine.getInstance();
        textTitle.setText(medicine.getName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.selection_once_daily:
                actionSetOften(view, getString(R.string.selection_once_daily));
                break;
            case R.id.selection_twice_daily:
                actionSetOften(view, getString(R.string.selection_twice_daily));
                break;

            case R.id.selection_three_times:
                actionSetOften(view, getString(R.string.selection_three_times));
                break;

            case R.id.selection_only_as_needed:
                actionSetOnlyAsNeeded(view, getString(R.string.selection_only_as_needed));
                break;

        }
    }
}