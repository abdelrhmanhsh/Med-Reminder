package com.med.medreminder.ui.meddisplayedit.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.med.medreminder.R;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.meddisplayedit.presenter.DisplayEditPresenter;
import com.med.medreminder.ui.meddisplayedit.presenter.DisplayPresenterInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MedicationDrugScreenDisplayFragment extends Fragment implements View.OnClickListener, DisplayEditViewInterface {

    public static final String TAG = "MedicationDrugScreenDisplayFragment";

    ImageView imgEdit, imgDelete;
    Button btnSuspend, btnRefill, btnResume;
    TextView remindersDesc, conditionsDesc, prescriptionDesc, resumeDesc;
    View resumeView;

    int id;
    boolean isSuspended;
    Medicine med;

    DisplayPresenterInterface presenterInterface;

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
        btnResume = view.findViewById(R.id.btn_resume);
        remindersDesc = view.findViewById(R.id.text_reminders_description);
        conditionsDesc = view.findViewById(R.id.text_conditions_description);
        prescriptionDesc = view.findViewById(R.id.text_prescription_description);
        resumeDesc = view.findViewById(R.id.text_resume_description);
        resumeView = view.findViewById(R.id.resume_med_view);

        presenterInterface = new DisplayEditPresenter(this,
                Repository.getInstance(getContext(),
                        ConcreteLocalSource.getInstance(getContext()), FirebaseWork.getInstance()));

        imgEdit.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
        btnSuspend.setOnClickListener(this);
        btnRefill.setOnClickListener(this);
        btnResume.setOnClickListener(this);

        id = getArguments().getInt("id");
        isSuspended = getArguments().getBoolean("suspended");
        getMedicine(id);

    }

    public void getMedicine(int id){
        LiveData<Medicine> medicine = getMedDetails(id);
        medicine.observe(this, new Observer<Medicine>() {
            @Override
            public void onChanged(Medicine medicine) {
                Log.i(TAG, "getMedicine: DISPLAY " + medicine.getName());
                updateUI(medicine);
            }
        });
    }

    private void updateUI(Medicine medicine){

        med = medicine;

        String reminderDesc = medicine.getOften() + "\n" + medicine.getTime();
        remindersDesc.setText(reminderDesc);
        conditionsDesc.setText(medicine.getReason());

        String presDesc = medicine.getMedLeft() + " Pills left\n"
            + "Refill reminder: When I have " + medicine.getRefillLimit() + " meds remaining";
        prescriptionDesc.setText(presDesc);

        if(isSuspended)
            updateUISuspended();
        else
            updateUINotSuspended();

    }

    private void updateUISuspended(){
        btnSuspend.setVisibility(View.GONE);
    }

    private void updateUINotSuspended(){
        resumeView.setVisibility(View.GONE);
        btnResume.setVisibility(View.GONE);
        resumeDesc.setVisibility(View.GONE);
    }

    private void actionSuspend(){
        new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.suspend_alert_title))
                .setMessage(getString(R.string.suspend_alert_message))
                .setPositiveButton(getString(R.string.suspend), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        med.setStartDate("Suspended");
                        med.setStartDateMillis(0);
                        med.setEndDate("Suspended");
                        med.setEndDateMillis(0);
                        updateMed(med);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void showRefillDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_refill_med);

        TextView textOk, textCancel ,textMedRemaining;

        textOk = dialog.findViewById(R.id.text_ok);
        textCancel = dialog.findViewById(R.id.text_cancel);
        textMedRemaining = dialog.findViewById(R.id.text_meds_remaining);
        EditText inputAddMeds = dialog.findViewById(R.id.input_add_meds);

        textOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentMed = med.getMedLeft();
                int medIncreased = Integer.parseInt(inputAddMeds.getText().toString());
                med.setMedLeft(currentMed+medIncreased);
                updateMed(med);
                dialog.dismiss();
            }
        });

        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        textMedRemaining.setText("You have " + med.getMedLeft() + " meds remaining");
        dialog.show();

    }

    private void actionRefill(){
        showRefillDialog();
    }

    private void actionResume(){

        new AlertDialog.Builder(getContext())
                .setTitle("Resume your medication")
                .setMessage("Are you sure you want to resume this medication?")
                .setPositiveButton(getString(R.string.resume), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Date currentDate = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());
                        String formattedDate = df.format(currentDate);

                        Date currDate = new Date();
                        long currMillis = currDate.getTime();

                        med.setEndDate(getString(R.string.selection_ongoing_treatment));
                        med.setStartDate(formattedDate); // update this
                        med.setStartDateMillis(currMillis); // update this
                        updateMed(med);

                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();

    }

    private void actionEdit(View view, int id){
        MedicationDrugScreenDisplayFragmentDirections.ActionMedicationDrugScreenDisplayToEdit
                action = MedicationDrugScreenDisplayFragmentDirections.actionMedicationDrugScreenDisplayToEdit();
        action.setMedId(id);
        Navigation.findNavController(view).navigate(action);
    }

    private void actionDelete(Medicine medicine){

        new AlertDialog.Builder(getContext())
                .setTitle("Delete " + medicine.getName())
                .setMessage(getString(R.string.delete_alert_message))
                .setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMed(medicine);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_suspend:
                actionSuspend();
                break;
            case R.id.btn_refill:
                actionRefill();
                break;
            case R.id.btn_resume:
                actionResume();
                break;
            case R.id.icon_edit:
                actionEdit(view, id);
                break;
            case R.id.icon_delete:
                actionDelete(med);
                break;
        }
    }

    @Override
    public LiveData<Medicine> getMedDetails(int id) {
        return presenterInterface.getMedDetails(id);
    }

    @Override
    public void updateMed(Medicine medicine) {
        presenterInterface.updateMed(medicine);
    }

    @Override
    public void deleteMed(Medicine medicine) {
        presenterInterface.deleteMed(medicine);
        Toast.makeText(getContext(), "Med Deleted!", Toast.LENGTH_SHORT).show();
    }

}