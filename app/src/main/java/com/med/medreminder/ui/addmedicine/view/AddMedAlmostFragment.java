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

import com.google.android.material.snackbar.Snackbar;
import com.med.medreminder.R;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.addmedicine.presenter.AddMedPresenter;
import com.med.medreminder.ui.addmedicine.presenter.AddMedPresenterInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedAlmostFragment extends Fragment implements View.OnClickListener, AddMedViewInterface {

    public static final String TAG = "AddMedAlmostFragment";

    Button btnSetTreatmentDuration, btnGetRefillReminder, btnAddInstructions, btnChangeMedIcon, btnSave;
    ProgressBar progressBar;
    TextView textTitle;

    AddMedPresenterInterface presenterInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_med_almost, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSetTreatmentDuration = view.findViewById(R.id.btn_set_treatment_duration);
        btnGetRefillReminder = view.findViewById(R.id.btn_get_refill_reminder);
//        btnAddInstructions = view.findViewById(R.id.btn_add_instructions);
        btnChangeMedIcon = view.findViewById(R.id.btn_change_med_icon);
        btnSave = view.findViewById(R.id.btn_almost_save);
        progressBar = view.findViewById(R.id.progress_bar);
        textTitle = view.findViewById(R.id.description);

        presenterInterface = new AddMedPresenter(this,
                Repository.getInstance(getContext(),  ConcreteLocalSource.getInstance(getContext())));

        progressBar.setProgress(90);

        btnSetTreatmentDuration.setOnClickListener(this);
        btnGetRefillReminder.setOnClickListener(this);
//        btnAddInstructions.setOnClickListener(this);
        btnChangeMedIcon.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        setTitleText();

    }

    private void setTitleText(){
        Medicine medicine = Medicine.getInstance();
        textTitle.setText(medicine.getName());
    }

    private void actionSetTreatmentDuration(View view){
        NavDirections action = AddMedAlmostFragmentDirections.actionAddMedAlmostToTreatmentStartDate();
        Navigation.findNavController(view).navigate(action);
    }

    private void actionGetRefillReminder(View view){
        NavDirections action = AddMedAlmostFragmentDirections.actionAddMedAlmostToRefillLeft();
        Navigation.findNavController(view).navigate(action);
    }

    private void actionChangeMedIcon(View view){
        NavDirections action = AddMedAlmostFragmentDirections.actionAddMedAlmostToChangeIcon();
        Navigation.findNavController(view).navigate(action);
    }

    private void actionSave(){

        Medicine filledMed = Medicine.getInstance();

        Medicine medicine = new Medicine(0, filledMed.getName(), filledMed.getForm(), filledMed.getStrength(),
                filledMed.getReason(), filledMed.getIsDaily(), filledMed.getOften(), filledMed.getTime(),
                filledMed.getStartDateMillis(), filledMed.getEndDateMillis(), filledMed.getMedLeft(),
                filledMed.getRefillLimit(), filledMed.getImage());

        Log.i(TAG, "actionSave: medicine save: " + medicine.toString());
        addMed(medicine);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_set_treatment_duration:
                actionSetTreatmentDuration(view);
                break;
            case R.id.btn_get_refill_reminder:
                actionGetRefillReminder(view);
                break;

            case R.id.btn_change_med_icon:
                actionChangeMedIcon(view);
                break;

            case R.id.btn_almost_save:
                actionSave();
                break;

            default:
                Log.e(TAG, "onClick: error");

        }
    }

    @Override
    public void addMed(Medicine medicine) {
        presenterInterface.addMed(medicine);
        Toast.makeText(getContext(), "Medicine Added!", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }
}