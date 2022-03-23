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

public class AddMedReasonFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedReasonFragment";

    Button btnNext;
    EditText reasonInput;
    ProgressBar progressBar;
    TextView textTitle, textSkip;

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
       // textSkip = view.findViewById(R.id.skip);

        btnNext.setOnClickListener(this);
        progressBar.setProgress(40);

        setTitleText();

    }

    private void actionNext(View view){
        if(!reasonInput.getText().toString().equals("")){

            String reason = reasonInput.getText().toString();

            Medicine medicine = Medicine.getInstance();
            medicine.setReason(reason);

            NavDirections action = AddMedReasonFragmentDirections.actionAddMedReasonToDaily();
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
        if(view.getId() == R.id.btn_next_reason)
            actionNext(view);
    }
}