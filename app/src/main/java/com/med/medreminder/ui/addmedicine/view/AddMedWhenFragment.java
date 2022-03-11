//package com.med.medreminder.ui.addmedicine;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.navigation.NavDirections;
//import androidx.navigation.Navigation;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.med.medreminder.R;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class AddMedWhenFragment extends Fragment implements View.OnClickListener {
//
//    public static final String TAG = "AddMedWhenFragment";
//
//    Button btnMorning, btnNoon, btnEvening, btnNight;
//    ProgressBar progressBar;
//    TextView textTitle;
//    String incomingMedicine, when;
//    JSONObject outgoingMedicine;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_add_med_when, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        btnMorning = view.findViewById(R.id.selection_morning);
//        btnNoon = view.findViewById(R.id.selection_noon);
//        btnEvening = view.findViewById(R.id.selection_evening);
//        btnNight = view.findViewById(R.id.selection_night);
//        progressBar = view.findViewById(R.id.progress_bar);
//        textTitle = view.findViewById(R.id.title);
//
//        progressBar.setProgress(70);
//
//        btnMorning.setOnClickListener(this);
//        btnNoon.setOnClickListener(this);
//        btnEvening.setOnClickListener(this);
//        btnNight.setOnClickListener(this);
//
//        outgoingMedicine = getArgs();
//
//    }
//
//    private JSONObject getArgs(){
//        AddMedWhenFragmentArgs args = AddMedWhenFragmentArgs.fromBundle(getArguments());
//        incomingMedicine = args.getOften();
//        Log.i(TAG, "getArgs: " + incomingMedicine);
//
//        JSONObject incomingJson = null;
//
//        try {
//            incomingJson = new JSONObject(incomingMedicine);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        textTitle.setText(incomingMedicine);
//        return incomingJson;
//    }
//
//    private void actionSelectionMorning(View view){
//        when = getString(R.string.selection_morning);
//
//        try {
//            outgoingMedicine.put("when", when);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String medicine = outgoingMedicine.toString();
//
//        AddMedWhenFragmentDirections.ActionAddMedWhenToTime
//                action = AddMedWhenFragmentDirections.actionAddMedWhenToTime();
//        action.setWhen(medicine);
//        Navigation.findNavController(view).navigate(action);
//    }
//
//    private void actionSelectionNoon(View view){
//        when = getString(R.string.selection_noon);
//
//        try {
//            outgoingMedicine.put("when", when);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String medicine = outgoingMedicine.toString();
//
//        AddMedWhenFragmentDirections.ActionAddMedWhenToTime
//                action = AddMedWhenFragmentDirections.actionAddMedWhenToTime();
//        action.setWhen(medicine);
//        Navigation.findNavController(view).navigate(action);
//    }
//
//    private void actionSelectionEvening(View view){
//        when = getString(R.string.selection_evening);
//
//        try {
//            outgoingMedicine.put("when", when);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String medicine = outgoingMedicine.toString();
//
//        AddMedWhenFragmentDirections.ActionAddMedWhenToTime
//                action = AddMedWhenFragmentDirections.actionAddMedWhenToTime();
//        action.setWhen(medicine);
//        Navigation.findNavController(view).navigate(action);
//    }
//
//    private void actionSelectionNight(View view){
//        when = getString(R.string.selection_night);
//
//        try {
//            outgoingMedicine.put("when", when);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String medicine = outgoingMedicine.toString();
//
//        AddMedWhenFragmentDirections.ActionAddMedWhenToTime
//                action = AddMedWhenFragmentDirections.actionAddMedWhenToTime();
//        action.setWhen(medicine);
//        Navigation.findNavController(view).navigate(action);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.selection_morning:
//                actionSelectionMorning(view);
//                break;
//            case R.id.selection_noon:
//                actionSelectionNoon(view);
//                break;
//
//            case R.id.selection_evening:
//                actionSelectionEvening(view);
//                break;
//
//            case R.id.selection_night:
//                actionSelectionNight(view);
//                break;
//
//            default:
//                Log.e(TAG, "onClick: error");
//
//        }
//    }
//}