package com.med.medreminder.ui.displayMedFriends.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.med.medreminder.R;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.displayMedFriends.presenter.DisplayMedFriendPresenter;
import com.med.medreminder.ui.displayMedFriends.presenter.DisplayMedFriendPresenterInterface;
import com.med.medreminder.ui.homepage.view.HomeActivity;
import com.med.medreminder.ui.request.presenter.RequestPresenter;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayMedFriendsActivity extends AppCompatActivity implements OnClickListener, DisplayMedFriendViewInterface {

    RecyclerView displayMedFriend_recyclerView;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    FirebaseDatabase database;
    YourPreference yourPrefrence;
    String currUserEmail;
    DisplayMedFriendsAdapter adapter;
    List<String> patientsEmail = new ArrayList<>();
    DisplayMedFriendPresenterInterface displayMedFriendPresenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_med_friends);

        displayMedFriend_recyclerView = findViewById(R.id.displayMedFriend_recyclerView);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();
        yourPrefrence = YourPreference.getInstance(getApplicationContext());
        currUserEmail = yourPrefrence.getData(Constants.EMAIL);

        displayMedFriendPresenterInterface = new DisplayMedFriendPresenter((Repository.getInstance(getApplicationContext(), ConcreteLocalSource.getInstance(getApplicationContext()), FirebaseWork.getInstance(getApplicationContext()))),this);
        displayMedFriendPresenterInterface.acceptedRequests(currUserEmail);

        displayMedFriend_recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        displayMedFriend_recyclerView.setLayoutManager(layoutManager);
        adapter = new DisplayMedFriendsAdapter(getApplicationContext(),patientsEmail,this);
        displayMedFriend_recyclerView.setAdapter(adapter);
    }


    @Override
    public void onMedFriendClick(String medFriend_email) {
        yourPrefrence.saveData(Constants.MED_FRIEND_EMAIL,medFriend_email);
        yourPrefrence.saveData(Constants.isMedFriend,"true");
        Log.d("TAG","Homeeeee: med friend email"+medFriend_email);
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        getApplicationContext().startActivity(intent);
    }


    @Override
    public void successToDisplayMedFriend(List<String> emails) {
        if (emails.size() == 0) {
            Log.d("TAG", "successToFetchHelpers: " + "NO requests");
            adapter.removeMedFriends();
            Toast.makeText(getApplicationContext(), "no requests", Toast.LENGTH_SHORT).show();


        } else {
            adapter.setMedFriends(emails);
        }
    }

    @Override
    public void failedToDisplayMedFriend(String msg) {
        Log.d("TAG", "failedToFetchHelpers: " + "FAILED TO FETCH requests");
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}