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

public class AddMedFormFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedFormFragment";

    Button btnPill, btnSolution, btnInjection, btnPowder, btnDrops, btnInhaler, btnOther;
    ProgressBar progressBar;
    TextView textTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_med_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnPill = view.findViewById(R.id.selection_pill);
//        btnSolution = view.findViewById(R.id.selection_solution);
        btnInjection = view.findViewById(R.id.selection_injection);
//        btnPowder = view.findViewById(R.id.selection_powder);
        btnDrops = view.findViewById(R.id.selection_drops);
//        btnInhaler = view.findViewById(R.id.selection_inhaler);
        btnOther = view.findViewById(R.id.selection_other);
        progressBar = view.findViewById(R.id.progress_bar);
        textTitle = view.findViewById(R.id.title);

        progressBar.setProgress(20);

        btnPill.setOnClickListener(this);
//        btnSolution.setOnClickListener(this);
        btnInjection.setOnClickListener(this);
//        btnPowder.setOnClickListener(this);
        btnDrops.setOnClickListener(this);
//        btnInhaler.setOnClickListener(this);
        btnOther.setOnClickListener(this);

        setTitleText();

    }

    private void actionSetForm(View view, String form){

        Medicine medicine = Medicine.getInstance();
        medicine.setForm(form);

        NavDirections action = AddMedFormFragmentDirections.actionAddMedFormToStrength();
        Navigation.findNavController(view).navigate(action);

    }

    private void setTitleText(){
        Medicine medicine = Medicine.getInstance();
        textTitle.setText(medicine.getName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.selection_pill:
                actionSetForm(view, getString(R.string.selection_pill));
                break;

            case R.id.selection_injection:
                actionSetForm(view, getString(R.string.selection_injection));
                break;

            case R.id.selection_drops:
                actionSetForm(view, getString(R.string.selection_drops));
                break;

            case R.id.selection_other:
                actionSetForm(view, getString(R.string.selection_other));
                break;

            default:
                Log.e(TAG, "onClick: error");

        }
    }
}