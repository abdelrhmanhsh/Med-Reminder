package com.med.medreminder.ui.signup.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.med.medreminder.R;
import com.med.medreminder.ui.homepage.view.HomeActivity;

import java.util.Calendar;


public class SignupFragment extends Fragment {

      EditText dob_edt;
    private int mYear, mMonth, mDay;
    Button signup_btn;
    ImageView cancel_ic;

    public SignupFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dob_edt = view.findViewById(R.id.dob_edt);
        signup_btn = view.findViewById(R.id.signup_btn);
        cancel_ic = view.findViewById(R.id.cancel_ic);

       signup_btn.setOnClickListener(view1 -> {
           getContext().startActivity(new Intent(getContext(), HomeActivity.class));

       });

        cancel_ic.setOnClickListener(view1 -> {
         findNavController(this).popBackStack();

        });

        dob_edt.setOnClickListener(view1 -> {
            final Calendar calendar = Calendar.getInstance ();
            mYear = calendar.get ( Calendar.YEAR );
            mMonth = calendar.get ( Calendar.MONTH );
            mDay = calendar.get ( Calendar.DAY_OF_MONTH );

            //show dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog ( getContext(), R.style.DialogTheme ,new DatePickerDialog.OnDateSetListener () {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dob_edt.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                }
            }, mYear, mMonth, mDay );
            datePickerDialog.show ();


        });
    }

    public static NavController findNavController(@NonNull Fragment fragment) {

        View view = fragment.getView();
        return Navigation.findNavController(view);

    }

}