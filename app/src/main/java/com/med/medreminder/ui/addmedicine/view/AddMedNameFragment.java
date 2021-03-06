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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.med.medreminder.R;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMedNameFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddMedNameFragment";

    Button btnNext;
    ProgressBar progressBar;
    EditText inputMedName;
    YourPreference yourPreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_med_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnNext = view.findViewById(R.id.btn_next_name);
        progressBar = view.findViewById(R.id.progress_bar);
        inputMedName = view.findViewById(R.id.input_med_name);

        progressBar.setProgress(10);
        btnNext.setOnClickListener(this);

        isUserOnline();
    }

    private boolean isUserOnline(){
        boolean isLogin = false;
        yourPreference = YourPreference.getInstance(getContext());

        String isLoginStr = yourPreference.getData(Constants.IS_LOGIN);
        if(isLoginStr.equals("true"))
            isLogin = true;

        Log.i(TAG, "isUserOnline: " + isLogin + " str: " + isLoginStr);

        return  isLogin;
    }

    private void actionNext(View view){
        if(!inputMedName.getText().toString().equals("")){

            Medicine medicine = Medicine.getInstance();
            String medName = inputMedName.getText().toString();
            medicine.setName(medName);

            NavDirections action = AddMedNameFragmentDirections.actionAddMedNameToForm();
            Navigation.findNavController(view).navigate(action);

        } else {
            Toast.makeText(getContext(), getString(R.string.warning_fill_info), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_next_name)
            actionNext(view);

    }
}