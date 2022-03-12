package com.med.medreminder.ui.meddisplayedit.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.med.medreminder.R;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.addmedicine.view.AddMedStrengthFragmentDirections;
import com.med.medreminder.ui.meddisplayedit.presenter.DisplayEditPresenter;
import com.med.medreminder.ui.meddisplayedit.presenter.DisplayEditPresenterInterface;

public class MedicationDrugScreenDisplayFragment extends Fragment implements View.OnClickListener, DisplayEditViewInterface {

    public static final String TAG = "MedicationDrugScreenDisplayFragment";

    ImageView imgEdit, imgDelete;
    Button btnSuspend, btnRefill;
    TextView remindersDesc, conditionsDesc, prescriptionDesc;

    DisplayEditPresenterInterface presenterInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medication_drug_screen_display, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgEdit = view.findViewById(R.id.icon_edit);
        imgDelete = view.findViewById(R.id.icon_delete);
        btnSuspend = view.findViewById(R.id.btn_suspend);
        btnRefill = view.findViewById(R.id.btn_refill);
        remindersDesc = view.findViewById(R.id.text_reminders_description);
        conditionsDesc = view.findViewById(R.id.text_conditions_description);
        prescriptionDesc = view.findViewById(R.id.text_prescription_description);

        presenterInterface = new DisplayEditPresenter(this,
                Repository.getInstance(getContext(),  ConcreteLocalSource.getInstance(getContext())));

        imgEdit.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
        btnSuspend.setOnClickListener(this);
        btnRefill.setOnClickListener(this);

        Medicine medicine = new Medicine(3, "name", "Pill", "500 g", "Reason", "Yes",
                "Once Daily", "9:0", "15-3-2022", "30-3-2022", 30, 2,
                R.drawable.ic_pill);

        actionRefill(medicine);

    }

    private void actionSuspend(){

    }

    private void actionRefill(Medicine medicine){
        getMedDetails(medicine);
    }

    private void actionEdit(View view){
        NavDirections action = MedicationDrugScreenDisplayFragmentDirections.actionMedicationDrugScreenDisplayToEdit();
        Navigation.findNavController(view).navigate(action);
    }

    private void actionDelete(View view){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_suspend:
                actionSuspend();
                break;
            case R.id.btn_refill:
//                actionRefill();
                break;
            case R.id.icon_edit:
                actionEdit(view);
                break;
            case R.id.icon_delete:
                actionDelete(view);
                break;
        }
    }

    @Override
    public void getMedDetails(Medicine medicine) {

        remindersDesc.setText(medicine.getOften()+"\n"+medicine.getTime());
        conditionsDesc.setText(medicine.getReason());
        prescriptionDesc.setText(medicine.getMedLeft()+" Pills left\nRefill reminder: when i have "+
                medicine.getRefillLimit()+" meds remaining.");

        presenterInterface.getMedDetails(medicine);
    }
}