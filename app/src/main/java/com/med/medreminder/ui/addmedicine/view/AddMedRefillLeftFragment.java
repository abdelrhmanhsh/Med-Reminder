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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.med.medreminder.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedRefillLeftFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedRefillLeftFragment";

    TextView textTitle;
    ProgressBar progressBar;
    Button btnNext;
    EditText inputMedLeft;
    String incomingMedicine;
    JSONObject outgoingMedicine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_med_refill_left, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textTitle = view.findViewById(R.id.title);
        progressBar = view.findViewById(R.id.progress_bar);
        btnNext = view.findViewById(R.id.btn_next_med_left);
        inputMedLeft = view.findViewById(R.id.input_med_left);

        progressBar.setProgress(90);
        btnNext.setOnClickListener(this);

        outgoingMedicine = getArgs();

    }

    private void actionNext(View view){

        if(!inputMedLeft.getText().toString().equals("")){

            String medLeft = inputMedLeft.getText().toString();

            try {
                outgoingMedicine.put("med_left", medLeft);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String medicine = outgoingMedicine.toString();

            AddMedRefillLeftFragmentDirections.ActionAddMedRefillLeftToRefillLimit
                    action = AddMedRefillLeftFragmentDirections.actionAddMedRefillLeftToRefillLimit();
            action.setMedLeft(medicine);
            Navigation.findNavController(view).navigate(action);

        } else {
            Toast.makeText(getContext(), getString(R.string.warning_fill_info), Toast.LENGTH_SHORT).show();
        }

    }

    private JSONObject getArgs(){

        AddMedRefillLeftFragmentArgs args =
                AddMedRefillLeftFragmentArgs.fromBundle(getArguments());
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
        if(view.getId() == R.id.btn_next_med_left)
            actionNext(view);
    }

}