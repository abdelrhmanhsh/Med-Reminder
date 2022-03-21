package com.med.medreminder.ui.displayMedFriends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.med.medreminder.R;
import com.med.medreminder.ui.displayHelpers.DisplayHelperAdapter;
import com.med.medreminder.ui.homepage.view.HomeActivity;
import com.med.medreminder.ui.homepage.view.HomeFragment;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayMedFriendsActivity extends AppCompatActivity implements OnClickListener {

    RecyclerView displayMedFriend_recyclerView;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    FirebaseDatabase database;
    YourPreference yourPrefrence;
    String currUserEmail;
    DisplayMedFriendsAdapter adapter;
    List<String> patientsEmail = new ArrayList<>();

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

        AcceptedRequests(currUserEmail);

        displayMedFriend_recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        displayMedFriend_recyclerView.setLayoutManager(layoutManager);
        adapter = new DisplayMedFriendsAdapter(getApplicationContext(),patientsEmail,this);
        displayMedFriend_recyclerView.setAdapter(adapter);

        //onClick -> CardView -> home layout of patient
    }

    private void AcceptedRequests(String myEmail) {
        db.collection("Users").document(myEmail).collection("Requests").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> patientsEmail = new ArrayList<>();
                Map<String, Object> data = new HashMap<>();
                List<DocumentSnapshot> documentSnapshot = queryDocumentSnapshots.getDocuments();
                for(int i=0; i<documentSnapshot.size(); i++){
                    data = documentSnapshot.get(i).getData();
                    String status = data.get("status").toString();
                    if(status.equals("ACCEPTED")){
                        patientsEmail.add(data.get("email").toString());
                    }
                    adapter.setMedFriends(patientsEmail);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onMedFriendClick(String medFriend_email) {
        yourPrefrence.saveData(Constants.MED_FRIEND_EMAIL,medFriend_email);
        yourPrefrence.saveData(Constants.isMedFriend,"true");
        Log.d("TAG","Homeeeee: med friend email"+medFriend_email);
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        getApplicationContext().startActivity(intent);
    }
}