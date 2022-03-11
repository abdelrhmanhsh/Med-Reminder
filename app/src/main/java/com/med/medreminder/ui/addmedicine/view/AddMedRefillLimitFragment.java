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

public class AddMedRefillLimitFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedRefillLimitFragment";

    TextView textTitle;
    ProgressBar progressBar;
    Button btnSet;
    EditText inputRefillLimit;
    String incomingMedicine;
    JSONObject outgoingMedicine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_med_refill_limit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textTitle = view.findViewById(R.id.title);
        progressBar = view.findViewById(R.id.progress_bar);
        btnSet = view.findViewById(R.id.btn_set_refill_limit);
        inputRefillLimit = view.findViewById(R.id.input_refill_limit);

        progressBar.setProgress(90);
        btnSet.setOnClickListener(this);

        outgoingMedicine = getArgs();

    }

    private void actionSet(View view){

        if(!inputRefillLimit.getText().toString().equals("")){

            String refillLimit = inputRefillLimit.getText().toString();

            try {
                outgoingMedicine.put("refill_limit", refillLimit);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String medicine = outgoingMedicine.toString();

            AddMedRefillLimitFragmentDirections.ActionAddMedRefillLimitToAlmost
                    action = AddMedRefillLimitFragmentDirections.actionAddMedRefillLimitToAlmost();
            action.setAlmost(medicine);
            Navigation.findNavController(view).navigate(action);

        } else {
            Toast.makeText(getContext(), getString(R.string.warning_fill_info), Toast.LENGTH_SHORT).show();
        }

    }

    private JSONObject getArgs(){

        AddMedRefillLimitFragmentArgs args =
                AddMedRefillLimitFragmentArgs.fromBundle(getArguments());
        incomingMedicine = args.getMedLeft();

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
        if(view.getId() == R.id.btn_set_refill_limit)
            actionSet(view);
    }
}