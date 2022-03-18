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

public class AddMedRefillLimitFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedRefillLimitFragment";

    TextView textTitle;
    ProgressBar progressBar;
    Button btnNext;
    EditText inputRefillLimit;

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
        btnNext = view.findViewById(R.id.btn_next_refill_limit);
        inputRefillLimit = view.findViewById(R.id.input_refill_limit);

        progressBar.setProgress(90);
        btnNext.setOnClickListener(this);

        setTitleText();

    }

    private void actionNext(View view){

        if(!inputRefillLimit.getText().toString().equals("")){

            int refillLimit = Integer.parseInt(inputRefillLimit.getText().toString());

            Medicine medicine = Medicine.getInstance();
            medicine.setRefillLimit(refillLimit);

            NavDirections action = AddMedRefillLimitFragmentDirections.actionAddMedRefillLimitToRefillRemindTime();
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
        if(view.getId() == R.id.btn_next_refill_limit)
            actionNext(view);
    }
}