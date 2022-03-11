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

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedReasonFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedReasonFragment";

    Button btnNext;
    EditText reasonInput;
    ProgressBar progressBar;
    TextView textTitle, textSkip;
    String incomingMedicine;
    JSONObject outgoingMedicine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_med_reason, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnNext = view.findViewById(R.id.btn_next_reason);
        reasonInput = view.findViewById(R.id.input_med_reason);
        progressBar = view.findViewById(R.id.progress_bar);
        textTitle = view.findViewById(R.id.title);
        textSkip = view.findViewById(R.id.skip);

        btnNext.setOnClickListener(this);

        progressBar.setProgress(40);

        outgoingMedicine = getArgs();

    }

    private void actionNext(View view){
        if(!reasonInput.getText().toString().equals("")){

            String type = reasonInput.getText().toString();

            try {
                outgoingMedicine.put("reason", type);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String medicine = outgoingMedicine.toString();

            AddMedReasonFragmentDirections.ActionAddMedReasonToDaily
                    action = AddMedReasonFragmentDirections.actionAddMedReasonToDaily();
            action.setReason(medicine);
            Navigation.findNavController(view).navigate(action);

        } else {
            Toast.makeText(getContext(), getString(R.string.warning_fill_info), Toast.LENGTH_SHORT).show();
        }
    }

    private JSONObject getArgs(){
        AddMedReasonFragmentArgs args = AddMedReasonFragmentArgs.fromBundle(getArguments());
        incomingMedicine = args.getStrength();
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
        if(view.getId() == R.id.btn_next_reason)
            actionNext(view);
    }
}