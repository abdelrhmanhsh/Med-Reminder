package com.med.medreminder.ui.homepage.view;

import android.content.Intent;
import android.os.Bundle;
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
import com.med.medreminder.databinding.FragmentNotificationsBinding;
import com.med.medreminder.firebase.FirebaseHelper;
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
    YourPreference yourPrefrence;



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

        binding.logoutTxt.setOnClickListener(view1 -> {

        });
        binding.profileTxt.setOnClickListener(view1 -> {
            yourPrefrence = YourPreference.getInstance(getContext());
            yourPrefrence.saveData(Constants.isMedFriend,"false");
            startActivity(new Intent(getActivity(), HomeActivity.class));
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