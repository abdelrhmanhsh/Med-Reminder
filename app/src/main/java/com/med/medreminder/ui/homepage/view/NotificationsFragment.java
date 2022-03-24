package com.med.medreminder.ui.homepage.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.med.medreminder.R;
import com.med.medreminder.databinding.FragmentNotificationsBinding;
import com.med.medreminder.firebase.FirebaseHelper;
import com.med.medreminder.ui.MainActivity;
import com.med.medreminder.ui.displayHelpers.view.DisplayHelpersActivity;
import com.med.medreminder.ui.displayMedFriends.view.DisplayMedFriendsActivity;
import com.med.medreminder.ui.medfriend.view.MedFriendActivity;
import com.med.medreminder.ui.request.view.RequestsActivity;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class NotificationsFragment extends Fragment {


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    FirebaseDatabase database;

   // TextView logout_txt;

    YourPreference yourPreference;

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

        yourPreference = YourPreference.getInstance(getContext());

        if (yourPreference.getData(Constants.IS_LOGIN).equals("false") || yourPreference.getData(Constants.IS_LOGIN).equals("")){
           // logout_txt.setText(R.string.login);
            binding.logoutTxt.setText(R.string.login);
            binding.logoutTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.login_ic,0,0,0);

        }
        else {
            //logout_txt.setText(R.string.logout);
           binding.logoutTxt.setText(R.string.logout);
            binding.logoutTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.logout_ic,0,0,0);

        }


        yourPreference = YourPreference.getInstance(getContext());
        TextView myMedFriendsRequestsTxt = view.findViewById(R.id.myMedFriendsRequests_txt);
        TextView showMedFriendsTxt = view.findViewById(R.id.showMedFriends_txt);
        TextView helpersTxt = view.findViewById(R.id.helpers_txt);
        TextView sendMedFriendTxt = view.findViewById(R.id.sendMedFriend_txt);


        binding.logoutTxt.setOnClickListener(view1 -> {
            Log.d("TAG", "onViewCreated: inside onclick logout");
            Log.d("TAG", "onViewCreated: ISLOGIN NOW--->"+ yourPreference.getData(Constants.IS_LOGIN));
            Log.d("TAG", "onViewCreated: " +binding.logoutTxt.getText().toString());

            if (yourPreference.getData(Constants.IS_LOGIN).equals("true")){
                Log.d("TAG", "onViewCreated: inside if is login");
                mAuth.signOut();
                binding.logoutTxt.setText(R.string.login);
                binding.logoutTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.login_ic,0,0,0);
                yourPreference.saveData(Constants.IS_LOGIN,"");
                yourPreference.saveData(Constants.EMAIL,"");
                yourPreference.saveData(Constants.FIRST_NAME,"");
                yourPreference.saveData(Constants.SECOND_NAME,"");
                Log.d("TAG", "onViewCreated: inside if is login" + yourPreference.getData(Constants.IS_LOGIN));
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
            yourPreference = YourPreference.getInstance(getContext());
            yourPreference.saveData(Constants.isMedFriend,"false");
            startActivity(new Intent(getActivity(), HomeActivity.class));
        });

        if(yourPreference.getData(Constants.isMedFriend).equals("true")){
            myMedFriendsRequestsTxt.setVisibility(TextView.INVISIBLE);
        }else{
            myMedFriendsRequestsTxt.setOnClickListener(view1 -> {
                if(FirebaseHelper.isUserLoggedIn(getContext()) && FirebaseHelper.isInternetAvailable(getContext())){
                    startActivity(new Intent(getActivity(), RequestsActivity.class));
                }else{
                    Toast.makeText(getContext(), "You must login first and be connected to the internet!", Toast.LENGTH_SHORT).show();
                }
            });
        }


        if(yourPreference.getData(Constants.isMedFriend).equals("true")){
            showMedFriendsTxt.setVisibility(TextView.INVISIBLE);
        }else{
            showMedFriendsTxt.setOnClickListener(view1 -> {
                if(FirebaseHelper.isUserLoggedIn(getContext()) && FirebaseHelper.isInternetAvailable(getContext())){
                    startActivity(new Intent(getActivity(), DisplayMedFriendsActivity.class));
                }else{
                    Toast.makeText(getContext(), "You must login first and be connected to the internet!", Toast.LENGTH_SHORT).show();
                }
            });
        }


        if(yourPreference.getData(Constants.isMedFriend).equals("true")){
            helpersTxt.setVisibility(TextView.INVISIBLE);
        }else{
            helpersTxt.setOnClickListener(view1 -> {

                if(FirebaseHelper.isUserLoggedIn(getContext()) && FirebaseHelper.isInternetAvailable(getContext())){
                    startActivity(new Intent(getActivity(), DisplayHelpersActivity.class));
                }else{
                    Toast.makeText(getContext(), "You must login first and be connected to the internet!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(yourPreference.getData(Constants.isMedFriend).equals("true")){
            sendMedFriendTxt.setVisibility(TextView.INVISIBLE);
        }else{
            sendMedFriendTxt.setOnClickListener(view1 -> {
                startActivity(new Intent(getActivity(), MedFriendActivity.class));
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}