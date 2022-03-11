package com.med.medreminder.ui.starterPage.view;

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
import android.widget.TextView;

import com.med.medreminder.R;
import com.med.medreminder.ui.homepage.view.HomeActivity;


public class StartingFragment extends Fragment {
    Button signup_btn;
    TextView login_txt;
    TextView skip_txt;


    public StartingFragment() {
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
        return inflater.inflate(R.layout.fragment_starting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signup_btn = view.findViewById(R.id.signup_btn);
        login_txt = view.findViewById(R.id.login_txt);
        skip_txt = view.findViewById(R.id.skip_txt);

        signup_btn.setOnClickListener(view1 -> {
            findNavController(this).navigate(R.id.fragmentToSignup);
        });

        login_txt.setOnClickListener(view1 -> {
            findNavController(this).navigate(R.id.fragmentToLogin);
        });

        skip_txt.setOnClickListener(view1 -> {
            getContext().startActivity(new Intent(getContext(), HomeActivity.class));

        });


    }

    public static NavController findNavController(@NonNull Fragment fragment) {

        View view = fragment.getView();
        return Navigation.findNavController(view);

    }
}