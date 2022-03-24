package com.med.medreminder.ui.meddisplayedit.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.med.medreminder.R;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseHelper;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.meddisplayedit.presenter.DisplayEditPresenter;
import com.med.medreminder.ui.meddisplayedit.presenter.DisplayPresenterInterface;
import com.med.medreminder.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lib.kingja.switchbutton.SwitchMultiButton;

public class MedicationDrugScreenEditFragment extends Fragment implements DisplayEditViewInterface,
        AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final String TAG = "MedicationDrugScreenEditFragment";

    EditText inputEditName, inputEditCondition, inputEditStrength, inputEditMedAmount, inputEditRefillLimit;
    SwitchCompat medReminderSwitch, refillReminderSwitch;
    Spinner reminderTimesOftenSpinner;
    TextView firstReminder, secondReminder, thirdReminder, textStartDate, textRefillReminderTime, refillReminderQuestion;
    RadioButton radioOngoing, radioSetEndDate;
    ImageView imgCurrent, imgPill, imgInjection, imgDrops, imgOther;
    Button btnDone;
    SwitchMultiButton switchMultiButton;
    DisplayPresenterInterface presenterInterface;

    String strengthType;
    String[] often = { "Once Daily", "Twice Daily", "3 times a day" };
    String endDate;
    int imgRes;
    long id;

    Medicine updateMed;

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
        textRefillReminderTime = view.findViewById(R.id.refill_reminder_time);
        refillReminderQuestion = view.findViewById(R.id.refill_reminder_question);
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
        textRefillReminderTime.setOnClickListener(this);
        radioSetEndDate.setOnClickListener(this);
        imgPill.setOnClickListener(this);
        imgInjection.setOnClickListener(this);
        imgDrops.setOnClickListener(this);
        imgOther.setOnClickListener(this);
        btnDone.setOnClickListener(this);

        presenterInterface = new DisplayEditPresenter(this,
                Repository.getInstance(getContext(),
                        ConcreteLocalSource.getInstance(getContext()), FirebaseWork.getInstance(getContext())));

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
                if (isChecked) {
                    reminderTimesOftenSpinner.setVisibility(View.VISIBLE);
                    int itemNum = reminderTimesOftenSpinner.getSelectedItemPosition();
                    actionOftenReminder(itemNum);
                } else{
                    actionHideAllReminders();
                }
            }
        });

        refillReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    textRefillReminderTime.setVisibility(View.VISIBLE);
                    refillReminderQuestion.setVisibility(View.VISIBLE);
                } else{
                    textRefillReminderTime.setVisibility(View.GONE);
                    refillReminderQuestion.setVisibility(View.GONE);
                }
//                updateMed.setRefillReminder(isChecked);
            }
        });

        reminderTimesOftenSpinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, often);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reminderTimesOftenSpinner.setAdapter(adapter);

        MedicationDrugScreenEditFragmentArgs args = MedicationDrugScreenEditFragmentArgs.fromBundle(getArguments());
        id = args.getMedId();

        getMedicine(id);

    }

    private void getMedicine(long id){
        LiveData<Medicine> medicine = getMedDetails(id);
        medicine.observe(this, new Observer<Medicine>() {
            @Override
            public void onChanged(Medicine medicine) {
                updateUI(medicine);
            }
        });
    }

    private void updateUI(Medicine medicine){

        updateMed = medicine;
        endDate = medicine.getEndDate();
        imgRes = medicine.getImage();

        inputEditName.setText(medicine.getName());
        inputEditCondition.setText(medicine.getReason());
        inputEditMedAmount.setText(String.valueOf(medicine.getMedLeft()));
        inputEditRefillLimit.setText(String.valueOf(medicine.getRefillLimit()));

        int setImgResource;
        switch (medicine.getImage()){
            case 1:
                setImgResource = R.drawable.ic_pill;
                break;
            case 2:
                setImgResource = R.drawable.ic_injection;
                break;
            case 3:
                setImgResource = R.drawable.ic_drops;
                break;
            case 4:
                setImgResource = R.drawable.ic_medicine_other;
                break;
            default:
                setImgResource = R.drawable.ic_medicine_other;
                break;
        }

        imgCurrent.setImageResource(setImgResource);

        //set spinner
        int selection = 0;

        switch (medicine.getOften()){
            case "Once Daily":
                medReminderSwitch.setChecked(true);
                selection = 0;
                break;

            case "Twice Daily":
                medReminderSwitch.setChecked(true);
                selection = 1;
                break;

            case "3 times a day":
                medReminderSwitch.setChecked(true);
                selection = 2;
                break;

            case "Only As Needed":
                medReminderSwitch.setChecked(false);
                reminderTimesOftenSpinner.setVisibility(View.GONE);
                break;

        }
        reminderTimesOftenSpinner.setSelection(selection);

        // set time texts
        String time = medicine.getTime();
        String[] times;
        String firstTime;
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
        if(medicine.getEndDate().equals("Ongoing treatment"))
            radioOngoing.setChecked(true);
        else
            radioSetEndDate.setChecked(true);


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
        inputEditStrength.setText(type[0]);

        //refill reminder
        boolean isRefillReminder = medicine.isRefillReminder();
        refillReminderSwitch.setChecked(isRefillReminder);
        textRefillReminderTime.setText("20:0");
        if(isRefillReminder){
            textRefillReminderTime.setVisibility(View.VISIBLE);
            textRefillReminderTime.setText(medicine.getRefillReminderTime());
        }

    }

    @Override
    public LiveData<Medicine> getMedDetails(long id) {
        return presenterInterface.getMedDetails(id);
    }

    private void actionOftenReminder(int itemSelected){
        switch (itemSelected){
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

    private void actionHideAllReminders(){
        reminderTimesOftenSpinner.setVisibility(View.GONE);
        firstReminder.setVisibility(View.GONE);
        secondReminder.setVisibility(View.GONE);
        thirdReminder.setVisibility(View.GONE);
    }


    private void actionOnceDaily(){
        firstReminder.setVisibility(View.VISIBLE);

        if(firstReminder.getText().toString().equals("")){
            firstReminder.setText(getString(R.string.click_to_add_reminder));
        }

        secondReminder.setVisibility(View.GONE);
        thirdReminder.setVisibility(View.GONE);
    }

    private void actionTwiceDaily(){
        firstReminder.setVisibility(View.VISIBLE);
        secondReminder.setVisibility(View.VISIBLE);

        if(firstReminder.getText().toString().equals("")){
            firstReminder.setText(getString(R.string.click_to_add_reminder));
        }
        if(secondReminder.getText().toString().equals("")){
            secondReminder.setText(getString(R.string.click_to_add_reminder));
        }

        thirdReminder.setVisibility(View.GONE);
        textRemindersRestrictions();
    }

    private void actionThreeTimesDaily(){
        firstReminder.setVisibility(View.VISIBLE);
        secondReminder.setVisibility(View.VISIBLE);
        thirdReminder.setVisibility(View.VISIBLE);

        if(firstReminder.getText().toString().equals("")){
            firstReminder.setText(getString(R.string.click_to_add_reminder));
        }
        if(secondReminder.getText().toString().equals("")){
            secondReminder.setText(getString(R.string.click_to_add_reminder));
        }
        if(thirdReminder.getText().toString().equals("")){
            thirdReminder.setText(getString(R.string.click_to_add_reminder));
        }
        textRemindersRestrictions();

    }

    private void textRemindersRestrictions(){
        if(firstReminder.getText().equals(getString(R.string.click_to_add_reminder))){
            secondReminder.setEnabled(false);
            thirdReminder.setEnabled(false);
        } else if(secondReminder.getText().equals(getString(R.string.click_to_add_reminder))){
            secondReminder.setEnabled(true);
            thirdReminder.setEnabled(false);
        } else {
            firstReminder.setEnabled(true);
            secondReminder.setEnabled(true);
            thirdReminder.setEnabled(true);
        }
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
        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                int month = monthOfYear + 1;
                Toast.makeText(getContext(), "end date set done " + dayOfMonth + "-" + month + "-" + year,
                        Toast.LENGTH_SHORT).show();
                endDate = dayOfMonth + "-" + month + "-" + year;
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
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
                        case "Pick time for refill reminder":
                            textRefillReminderTime.setText(hourOfDay + ":" + minute);
                            break;

                    }
                    textRemindersRestrictions();
                }
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, 20, 0, true);
        timePickerDialog.setTitle("Set Reminder");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    private void actionChangeMedImage(int imgResource){
        int setImgResource;
        switch (imgResource){
            case 1:
                setImgResource = R.drawable.ic_pill;
                break;
            case 2:
                setImgResource = R.drawable.ic_injection;
                break;
            case 3:
                setImgResource = R.drawable.ic_drops;
                break;
            case 4:
                setImgResource = R.drawable.ic_medicine_other;
                break;
            default:
                setImgResource = R.drawable.ic_medicine_other;
                break;
        }

        imgCurrent.setImageResource(setImgResource);
        imgRes = imgResource;
    }

    private void actionDone(Medicine medicine){
        editMed(medicine);
//        getActivity().finish();
    }

    private void editMed(Medicine medicine){

        //update med name
        updateMed.setName(inputEditName.getText().toString());

        // update med reminder(s)
        String time = "";

        if(!medReminderSwitch.isChecked()){
            updateMed.setTime("");
            updateMed.setOften(getString(R.string.selection_only_as_needed));
        } else {
            switch (reminderTimesOftenSpinner.getSelectedItemPosition()){
                case 0:
                    if(!firstReminder.getText().equals(getString(R.string.click_to_add_reminder)))
                        time = firstReminder.getText().toString();
                    break;

                case 1:
                    if(!firstReminder.getText().equals(getString(R.string.click_to_add_reminder)))
                        time = firstReminder.getText().toString();
                    if(!secondReminder.getText().equals(getString(R.string.click_to_add_reminder)))
                        time += ", " + secondReminder.getText().toString();
                    break;

                case 2:
                    if(!firstReminder.getText().equals(getString(R.string.click_to_add_reminder)))
                        time = firstReminder.getText().toString();
                    if(!secondReminder.getText().equals(getString(R.string.click_to_add_reminder)))
                        time += ", " + secondReminder.getText().toString();
                    if(!thirdReminder.getText().equals(getString(R.string.click_to_add_reminder)))
                        time += ", " + thirdReminder.getText().toString();
                    break;
            }
            updateMed.setTime(time);

            String editOften;
            String[] times;
            if(time.contains(", ")){

                times = time.split(", ");

                // two times
                if (times.length==2){
                    editOften = "Twice Daily";

                } else {    // three times

                    editOften = "3 times a day";
                }
            } else {
                editOften = "Once Daily";
            }
            if(!time.equals(""))
                updateMed.setOften(editOften);
            else
                updateMed.setOften(getString(R.string.selection_only_as_needed));
        }

        // set end date
        if(radioOngoing.isChecked())
            updateMed.setEndDate(getString(R.string.selection_ongoing_treatment));
        else
            updateMed.setEndDate(endDate);


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {

            Date date = sdf.parse(endDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            medicine.setEndDateMillis(calendar.getTimeInMillis());

        } catch(ParseException e){
            e.printStackTrace();
        }

        //update med icon
        updateMed.setImage(imgRes);

        //update med reason
        updateMed.setReason(inputEditCondition.getText().toString());

        //update strength
        String strength = inputEditStrength.getText().toString() + " " + strengthType;
        updateMed.setStrength(strength);

        //update refill reminders
        updateMed.setMedLeft(Integer.parseInt(inputEditMedAmount.getText().toString()));
        updateMed.setRefillLimit(Integer.parseInt(inputEditRefillLimit.getText().toString()));
        updateMed.setRefillReminder(refillReminderSwitch.isChecked());
        updateMed.setRefillReminderTime(textRefillReminderTime.getText().toString());

        // update med locally
        updateMed(medicine);

        // update med firestore
        if(FirebaseHelper.isInternetAvailable(getContext()))
            if(FirebaseHelper.isUserLoggedIn(getContext())){
                String email = FirebaseHelper.getUserEmail(getContext());
                updateMedFirestore(medicine, email, id);
            }

    }

    @Override
    public void updateMed(Medicine medicine) {
        presenterInterface.updateMed(medicine);
        Toast.makeText(getContext(), "Med Updated!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteMed(Medicine medicine) {

    }

    @Override
    public void updateMedFirestore(Medicine medicine, String email, long id) {
        presenterInterface.updateMedFirestore(medicine, email, id);
        Log.i(TAG, "updateMedFirestore: firestore updated with id: " + id);
    }

    @Override
    public void deleteMedFirestore(String email, long id) {

    }

    @Override
    public void getMedByIdFirestore(Medicine medicine) {

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
            case R.id.refill_reminder_time:
                actionReminderText("Pick time for refill reminder");
                break;
            case R.id.radio_set_end_date:
                actionRadioSetEndDate();
                break;
            case R.id.med_pill_icon:
                actionChangeMedImage(Constants.IMAGE_PILL);
                break;
            case R.id.med_injection_icon:
                actionChangeMedImage(Constants.IMAGE_INJECTION);
                break;
            case R.id.med_drops_icon:
                actionChangeMedImage(Constants.IMAGE_DROPS);
                break;
            case R.id.med_other_icon:
                actionChangeMedImage(Constants.IMAGE_MED_OTHER);
                break;
            case R.id.btn_done_edit:
                actionDone(updateMed);
                break;
        }
    }

}
