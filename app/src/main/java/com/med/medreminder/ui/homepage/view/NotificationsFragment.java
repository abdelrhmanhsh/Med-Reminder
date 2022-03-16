package com.med.medreminder.ui.homepage.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.med.medreminder.databinding.FragmentNotificationsBinding;
import com.med.medreminder.ui.medfriend.view.MedFriendActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class NotificationsFragment extends Fragment {

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

        binding.logoutTxt.setOnClickListener(view1 -> {

        });
        binding.profileTxt.setOnClickListener(view1 -> {

        });
        binding.myMedFriendsRequestsTxt.setOnClickListener(view1 -> {

        });
        binding.showMedFriendsTxt.setOnClickListener(view1 -> {

        });
        binding.sendMedFriendTxt.setOnClickListener(view1 -> {

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}