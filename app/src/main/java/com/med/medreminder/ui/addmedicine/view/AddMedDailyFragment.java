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

public class AddMedDailyFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedDailyFragment";

    Button btnYes, btnNo, btnOnlyAsNeeded;
    ProgressBar progressBar;
    TextView textTitle;

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

        setTitleText();

    }

    private void setTitleText(){
        Medicine medicine = Medicine.getInstance();
        textTitle.setText(medicine.getName());
    }

    private void actionSelectionYes(View view, String isDaily){

        Medicine medicine = Medicine.getInstance();
        medicine.setIsDaily(isDaily);

        NavDirections action = AddMedDailyFragmentDirections.actionAddMedDailyToOften();
        Navigation.findNavController(view).navigate(action);

    }

    private void actionSelectionOnlyAsNeeded(View view, String isDaily){

        Medicine medicine = Medicine.getInstance();
        medicine.setIsDaily(isDaily);

        NavDirections action = AddMedDailyFragmentDirections.actionAddMedDailyToAlmost();
        Navigation.findNavController(view).navigate(action);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.selection_yes:
                actionSelectionYes(view, getString(R.string.selection_yes));
                break;

            case R.id.selection_only_as_needed:
                actionSelectionOnlyAsNeeded(view, getString(R.string.selection_only_as_needed));
                break;
        }
    }
}