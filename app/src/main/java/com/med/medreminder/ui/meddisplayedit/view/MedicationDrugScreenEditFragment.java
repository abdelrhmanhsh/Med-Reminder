package com.med.medreminder.ui.meddisplayedit.view;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.med.medreminder.R;
import com.med.medreminder.model.Medicine;

import lib.kingja.switchbutton.SwitchMultiButton;

public class MedicationDrugScreenEditFragment extends Fragment implements DisplayEditViewInterface,
        AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final String TAG = "MedicationDrugScreenEditFragment";

    EditText inputEditName, inputEditCondition, inputEditStrength, inputEditMedAmount, inputEditRefillLimit;
    SwitchCompat medReminderSwitch, refillReminderSwitch;
    Spinner reminderTimesOftenSpinner;
    TextView firstReminder, secondReminder, thirdReminder, textStartDate;
    RadioButton radioOngoing, radioSetEndDate;
    ImageView imgCurrent, imgPill, imgInjection, imgDrops, imgOther;
    Button btnDone;
    SwitchMultiButton switchMultiButton;

    String strengthType;
    String[] often = { "Once Daily", "Twice Daily", "3 times a day" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medication_drug_screen_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputEditName = view.findViewById(R.id.input_med_name_edit);
        inputEditCondition = view.findViewById(R.id.input_med_condition_edit);
        inputEditStrength = view.findViewById(R.id.input_med_strength_edit);
        inputEditMedAmount = view.findViewById(R.id.input_med_amount_edit);
        inputEditRefillLimit = view.findViewById(R.id.input_med_limit_edit);
        medReminderSwitch = view.findViewById(R.id.reminder_switch);
        refillReminderSwitch = view.findViewById(R.id.refill_reminder_switch);
        reminderTimesOftenSpinner = view.findViewById(R.id.reminder_times_spinner);
        firstReminder = view.findViewById(R.id.first_reminder);
        secondReminder = view.findViewById(R.id.second_reminder);
        thirdReminder = view.findViewById(R.id.third_reminder);
        textStartDate = view.findViewById(R.id.text_start_date);
        radioOngoing = view.findViewById(R.id.radio_ongoing);
        radioSetEndDate = view.findViewById(R.id.radio_set_end_date);
        imgCurrent = view.findViewById(R.id.med_current_icon);
        imgPill = view.findViewById(R.id.med_pill_icon);
        imgInjection = view.findViewById(R.id.med_injection_icon);
        imgDrops = view.findViewById(R.id.med_drops_icon);
        imgOther = view.findViewById(R.id.med_other_icon);
        switchMultiButton = view.findViewById(R.id.switch_multi_button);
        btnDone = view.findViewById(R.id.btn_done_edit);

        firstReminder.setOnClickListener(this);
        secondReminder.setOnClickListener(this);
        thirdReminder.setOnClickListener(this);
        radioSetEndDate.setOnClickListener(this);
        imgPill.setOnClickListener(this);
        imgInjection.setOnClickListener(this);
        imgDrops.setOnClickListener(this);
        imgOther.setOnClickListener(this);
        btnDone.setOnClickListener(this);

        strengthType = "";

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

        medReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    Toast.makeText(getContext(), "Reminder ON", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "Reminder OFF", Toast.LENGTH_SHORT).show();
            }
        });

        refillReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    Toast.makeText(getContext(), "Refill Reminder ON", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "Refill Reminder OFF", Toast.LENGTH_SHORT).show();
            }
        });

        reminderTimesOftenSpinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, often);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reminderTimesOftenSpinner.setAdapter(adapter);

        Medicine medicine = new Medicine(3, "name", "Pill", "500 mEg", "Reason", "Yes",
                "3 times a day", "9:0,19:30,5:0", "15-3-2022", "30-3-2022", 30, 2,
                R.drawable.ic_pill);

        getMed(medicine);

    }

    private void getMed(Medicine medicine){
        getMedDetails(medicine);
    }

    @Override
    public void getMedDetails(Medicine medicine) {

        inputEditName.setText(medicine.getName());
        inputEditCondition.setText(medicine.getReason());
        inputEditStrength.setText(medicine.getStrength());
        inputEditMedAmount.setText(String.valueOf(medicine.getMedLeft()));
        inputEditRefillLimit.setText(String.valueOf(medicine.getRefillLimit()));


        //set spinner
        int selection = 0;

        switch (medicine.getOften()){
            case "Once Daily":
                selection = 0;
                break;

            case "Twice Daily":
                selection = 1;
                break;

            case "3 times a day":
                selection = 2;
                break;

        }
        reminderTimesOftenSpinner.setSelection(selection);

        // set time texts
        String time = medicine.getTime();
        String[] times;
        String firstTime = "";
        String secondTime, thirdTime;
        if(time.contains(",")){

            times = time.split(",");

            // two times
            if (times.length==2){
                firstTime = times[0];
                secondTime = times[1];
                secondReminder.setText(secondTime);

            } else {    // three times

                firstTime = times[0];
                secondTime = times[1];
                thirdTime = times[2];
                secondReminder.setText(secondTime);
                thirdReminder.setText(thirdTime);
            }
        } else {
            firstTime = time;
        }

        firstReminder.setText(firstTime);

        // radio button selection
        switch (medicine.getIsDaily()){
            case "Yes":
                radioSetEndDate.setChecked(true);
                break;

            case "Ongoing Treatment":
                radioOngoing.setChecked(true);
                break;

            default:
                Log.e(TAG, "getMedDetails: error check radio button");
        }

        // if not null
        textStartDate.setText("Start date: " + medicine.getStartDate());

        //switch tab
        String[] type = medicine.getStrength().split(" ");
        int switchSelection = 0;
        switch (type[1]){
            case "g":
                switchSelection = 0;
                break;

            case "IU":
                switchSelection = 1;
                break;

            case "mcg":
                switchSelection = 2;
                break;

            case "mEg":
                switchSelection = 3;
                break;

            case "mg":
                switchSelection = 4;
                break;

            default:
                Log.e(TAG, "getMedDetails: error check radio button");
        }
        switchMultiButton.setSelectedTab(switchSelection);

    }

    private void actionOnceDaily(){
        firstReminder.setVisibility(View.VISIBLE);
        secondReminder.setVisibility(View.GONE);
        thirdReminder.setVisibility(View.GONE);
    }

    private void actionTwiceDaily(){

        firstReminder.setVisibility(View.VISIBLE);
        secondReminder.setVisibility(View.VISIBLE);
        thirdReminder.setVisibility(View.GONE);
    }

    private void actionThreeTimesDaily(){
        firstReminder.setVisibility(View.VISIBLE);
        secondReminder.setVisibility(View.VISIBLE);
        thirdReminder.setVisibility(View.VISIBLE);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                actionOnceDaily();
                break;
            case 1:
                actionTwiceDaily();
                break;
            case 2:
                actionThreeTimesDaily();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void actionRadioSetEndDate(){

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    Toast.makeText(getContext(), "end date set done " + hourOfDay + ":" + minute,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, 20, 0, true);
        timePickerDialog.setTitle("Select End Date");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    private void actionReminderText(String title){

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    switch (title){
                        case "Pick time for dose 1":
                            firstReminder.setText(hourOfDay + ":" + minute);
                            break;
                        case "Pick time for dose 2":
                            secondReminder.setText(hourOfDay + ":" + minute);
                            break;
                        case "Pick time for dose 3":
                            thirdReminder.setText(hourOfDay + ":" + minute);
                            break;
                    }
                }
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, 20, 0, true);
        timePickerDialog.setTitle("Set Reminder");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    private void actionChangeMedImage(int imgResource){
        imgCurrent.setImageResource(imgResource);
    }

    private void actionDone(){
        Toast.makeText(getContext(), "Edit Done!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.first_reminder:
                actionReminderText("Pick time for dose 1");
                break;
            case R.id.second_reminder:
                actionReminderText("Pick time for dose 2");
                break;
            case R.id.third_reminder:
                actionReminderText("Pick time for dose 3");
                break;
            case R.id.radio_set_end_date:
                actionRadioSetEndDate();
                break;
            case R.id.med_pill_icon:
                actionChangeMedImage(R.drawable.ic_pill);
                break;
            case R.id.med_injection_icon:
                actionChangeMedImage(R.drawable.ic_injection);
                break;
            case R.id.med_drops_icon:
                actionChangeMedImage(R.drawable.ic_drops);
                break;
            case R.id.med_other_icon:
                actionChangeMedImage(R.drawable.ic_medicine_other);
                break;
            case R.id.btn_done_edit:
                actionDone();
                break;
        }
    }
}