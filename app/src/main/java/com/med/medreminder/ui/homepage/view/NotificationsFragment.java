package com.med.medreminder.ui.homepage.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.med.medreminder.R;
import com.med.medreminder.databinding.FragmentNotificationsBinding;
import com.med.medreminder.firebase.FirebaseHelper;
import com.med.medreminder.ui.MainActivity;
import com.med.medreminder.ui.displayHelpers.DisplayHelpersActivity;
import com.med.medreminder.ui.displayMedFriends.DisplayMedFriendsActivity;
import com.med.medreminder.ui.medfriend.view.MedFriendActivity;
import com.med.medreminder.ui.request.view.RequestsActivity;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NotificationsFragment extends Fragment {


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    FirebaseDatabase database;

   // TextView logout_txt;

    YourPreference preference;

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();

        //logout_txt = view.findViewById(R.id.logout_txt);

        preference = YourPreference.getInstance(getContext());

        if (preference.getData(Constants.IS_LOGIN).equals("false") || preference.getData(Constants.IS_LOGIN).equals("")){
           // logout_txt.setText(R.string.login);
            binding.logoutTxt.setText(R.string.login);
            binding.logoutTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.login_ic,0,0,0);

        }
        else {
            //logout_txt.setText(R.string.logout);
           binding.logoutTxt.setText(R.string.logout);
            binding.logoutTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.logout_ic,0,0,0);

        }


        binding.logoutTxt.setOnClickListener(view1 -> {
            Log.d("TAG", "onViewCreated: inside onclick logout");
            Log.d("TAG", "onViewCreated: ISLOGIN NOW--->"+ preference.getData(Constants.IS_LOGIN));
            Log.d("TAG", "onViewCreated: " +binding.logoutTxt.getText().toString());

            if (preference.getData(Constants.IS_LOGIN).equals("true")){
                Log.d("TAG", "onViewCreated: inside if is login");
                mAuth.signOut();
                binding.logoutTxt.setText(R.string.login);
                binding.logoutTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.login_ic,0,0,0);
                preference.saveData(Constants.IS_LOGIN,"");
                preference.saveData(Constants.EMAIL,"");
                preference.saveData(Constants.FIRST_NAME,"");
                preference.saveData(Constants.SECOND_NAME,"");
                Log.d("TAG", "onViewCreated: inside if is login" + preference.getData(Constants.IS_LOGIN));
                Intent intent = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(intent);
                getActivity().finish();
            }
            else {
                Intent intent = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(intent);
                getActivity().finish();
            }
        });



        binding.profileTxt.setOnClickListener(view1 -> {

        });

        binding.myMedFriendsRequestsTxt.setOnClickListener(view1 -> {
            if(FirebaseHelper.isUserLoggedIn(getContext()) && FirebaseHelper.isInternetAvailable(getContext())){
                startActivity(new Intent(getActivity(), RequestsActivity.class));
            }else{
                Toast.makeText(getContext(), "You must login first and be connected to the internet!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.showMedFriendsTxt.setOnClickListener(view1 -> {
            if(FirebaseHelper.isUserLoggedIn(getContext()) && FirebaseHelper.isInternetAvailable(getContext())){
                startActivity(new Intent(getActivity(), DisplayMedFriendsActivity.class));
            }else{
                Toast.makeText(getContext(), "You must login first and be connected to the internet!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.helpersTxt.setOnClickListener(view1 -> {

            if(FirebaseHelper.isUserLoggedIn(getContext()) && FirebaseHelper.isInternetAvailable(getContext())){
                startActivity(new Intent(getActivity(), DisplayHelpersActivity.class));
            }else{
                Toast.makeText(getContext(), "You must login first and be connected to the internet!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.sendMedFriendTxt.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), MedFriendActivity.class));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}