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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.med.medreminder.R;
import com.med.medreminder.model.Medicine;

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
    String strengthType;

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
        //textSkip = view.findViewById(R.id.skip);

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

        setTitleText();

    }

    private void actionNext(View view){
        if(!strengthInput.getText().toString().equals("")){

            String strength = strengthInput.getText().toString() + " " + strengthType;
            Medicine medicine = Medicine.getInstance();
            medicine.setStrength(strength);

            NavDirections action = AddMedStrengthFragmentDirections.actionAddMedStrengthToReason();
            Navigation.findNavController(view).navigate(action);

        } else {
            Toast.makeText(getContext(), getString(R.string.warning_fill_info), Toast.LENGTH_SHORT).show();
        }
    }

    private void setTitleText(){
        Medicine medicine = Medicine.getInstance();
        textTitle.setText(medicine.getName());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_next_strength)
            actionNext(view);
    }
}