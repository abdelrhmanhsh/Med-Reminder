package com.med.medreminder.ui.addmedicine;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.med.medreminder.model.Medicine;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedBaseFragment extends Fragment {

//    Medicine medicine;
    JSONObject incomingMedicine, outgoingMedicine;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        medicine = new Medicine();
        outgoingMedicine = getArgs();

    }

    private JSONObject getArgs(){
//        AddMedFormFragmentArgs args = AddMedFormFragmentArgs.fromBundle(getArguments());
//        incomingMedicine = args.getMedicine();
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
        return incomingMedicine;
    }

}
