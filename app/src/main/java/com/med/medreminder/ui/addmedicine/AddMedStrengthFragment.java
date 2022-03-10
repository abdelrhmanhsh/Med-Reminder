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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.med.medreminder.R;

import org.json.JSONException;
import org.json.JSONObject;

import lib.kingja.switchbutton.SwitchMultiButton;

public class AddMedStrengthFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedStrengthFragment";

    SwitchMultiButton switchMultiButton;
    Button btnNext;
    ProgressBar progressBar;
    EditText strengthInput;
    TextView textTitle, textSkip;
    String incomingMedicine, strengthType;
    JSONObject outgoingMedicine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_med_strength, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switchMultiButton = view.findViewById(R.id.switch_multi_button);
        btnNext = view.findViewById(R.id.btn_next_strength);
        progressBar = view.findViewById(R.id.progress_bar);
        strengthInput = view.findViewById(R.id.input_med_strength);
        textTitle = view.findViewById(R.id.title);
        textSkip = view.findViewById(R.id.skip);

        btnNext.setOnClickListener(this);

        progressBar.setProgress(30);

        strengthType = "g";
        switchMultiButton.setText(
                getString(R.string.selection_g),
                getString(R.string.selection_iu),
                getString(R.string.selection_mcg),
                getString(R.string.selection_meg),
                getString(R.string.selection_mg)
        ).setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                strengthType = tabText;
            }
        });

        outgoingMedicine = getArgs();

    }

    private void actionNext(View view){
        if(!strengthInput.getText().toString().equals("")){

            String type = strengthInput.getText().toString() + " " + strengthType;

            try {
                outgoingMedicine.put("strength", type);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String medicine = outgoingMedicine.toString();

            AddMedStrengthFragmentDirections.ActionAddMedStrengthToReason
                    action = AddMedStrengthFragmentDirections.actionAddMedStrengthToReason();
            action.setStrength(medicine);
            Navigation.findNavController(view).navigate(action);

        } else {
            Toast.makeText(getContext(), getString(R.string.warning_fill_info), Toast.LENGTH_SHORT).show();
        }
    }

    private JSONObject getArgs(){
        AddMedStrengthFragmentArgs args = AddMedStrengthFragmentArgs.fromBundle(getArguments());
        incomingMedicine = args.getForm();
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
        if(view.getId() == R.id.btn_next_strength)
            actionNext(view);
    }
}