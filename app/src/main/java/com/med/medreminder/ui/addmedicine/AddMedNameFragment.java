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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.med.medreminder.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedNameFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedNameFragment";

    Button btnNext;
    ProgressBar progressBar;
    EditText inputMedName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_med_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnNext = view.findViewById(R.id.btn_next_name);
        progressBar = view.findViewById(R.id.progress_bar);
        inputMedName = view.findViewById(R.id.input_med_name);

        progressBar.setProgress(10);
        btnNext.setOnClickListener(this);

    }

    private void actionNext(View view){
        if(!inputMedName.getText().toString().equals("")){

            String medName = inputMedName.getText().toString();

            JSONObject medicine = new JSONObject();

            try {
                medicine.put("name", medName);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String outgoingMedicine = medicine.toString();

            AddMedNameFragmentDirections.ActionAddMedNameToForm
                    action = AddMedNameFragmentDirections.actionAddMedNameToForm();
            action.setMedicine(outgoingMedicine);

            Navigation.findNavController(view).navigate(action);

        } else {
            Toast.makeText(getContext(), getString(R.string.warning_fill_info), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_next_name)
            actionNext(view);

    }
}