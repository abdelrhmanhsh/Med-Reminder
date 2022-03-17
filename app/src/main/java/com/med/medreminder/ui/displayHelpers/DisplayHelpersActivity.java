package com.med.medreminder.ui.displayHelpers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.med.medreminder.R;
import com.med.medreminder.ui.displayMedFriends.DisplayMedFriendsAdapter;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayHelpersActivity extends AppCompatActivity {

    RecyclerView displayHelper_recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    FirebaseDatabase database;
    YourPreference yourPrefrence;
    String currUserEmail;
    DisplayHelperAdapter adapter;
    List<String> helpersEmail = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_helpers);

        displayHelper_recyclerView = findViewById(R.id.displayHelper_recyclerView);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();
        yourPrefrence = YourPreference.getInstance(getApplicationContext());
        currUserEmail = yourPrefrence.getData(Constants.EMAIL);

        Helpers(currUserEmail);

        displayHelper_recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        displayHelper_recyclerView.setLayoutManager(layoutManager);
        adapter = new DisplayHelperAdapter(getApplicationContext(),helpersEmail);
        displayHelper_recyclerView.setAdapter(adapter);

    }

    private void Helpers(String myEmail) {
        db.collection("Users").document(myEmail).collection("Helpers").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> helperEmail = new ArrayList<>();
                Map<String, Object> data = new HashMap<>();
                List<DocumentSnapshot> documentSnapshot = queryDocumentSnapshots.getDocuments();
                for(int i=0; i<documentSnapshot.size(); i++){
                    data = documentSnapshot.get(i).getData();
                    helperEmail.add(data.get("helper_email").toString());
                    adapter.setHelpers(helperEmail);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}