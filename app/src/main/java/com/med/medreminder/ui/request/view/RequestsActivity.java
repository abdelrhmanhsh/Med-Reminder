package com.med.medreminder.ui.request.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.firestore.FirebaseFirestore;

import com.med.medreminder.R;

import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseHelper;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.request.presenter.RequestPresenter;
import com.med.medreminder.ui.request.presenter.RequestPresenterInterface;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;


import java.util.ArrayList;
import java.util.List;

public class RequestsActivity extends AppCompatActivity implements RequestViewInterface {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    RecyclerView request_recyclerView;
    FirebaseDatabase database;
    public static String currUserEmail;
    String currUser;
    RequestAdapter requestAdapter;
    YourPreference yourPrefrence;
    List<String> emails = new ArrayList<>();
    RequestPresenterInterface requestPresenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        request_recyclerView = findViewById(R.id.request_recyclerView);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();
        yourPrefrence = YourPreference.getInstance(getApplicationContext());
        currUser = yourPrefrence.getData(Constants.FIRST_NAME);
        currUserEmail = yourPrefrence.getData(Constants.EMAIL);

        if(FirebaseHelper.isUserLoggedIn(getApplicationContext()) && FirebaseHelper.isInternetAvailable(getApplicationContext())){
            requestPresenterInterface = new RequestPresenter(this, (Repository.getInstance(getApplicationContext(), ConcreteLocalSource.getInstance(getApplicationContext()), FirebaseWork.getInstance(getApplicationContext()))));
            requestPresenterInterface.loadRequests(currUserEmail);
        }else{
            Toast.makeText(this, "You must login first and be connected to the internet!", Toast.LENGTH_SHORT).show();
        }
        request_recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        request_recyclerView.setLayoutManager(layoutManager);
        requestAdapter = new RequestAdapter(emails,getApplicationContext());
        request_recyclerView.setAdapter(requestAdapter);
    }

    @Override
    public void updateStatusInFirestore(String helperEmail,String patientEmail,String status) {
        requestPresenterInterface = new RequestPresenter(this,Repository.getInstance(this,ConcreteLocalSource.getInstance(this), FirebaseWork.getInstance(this)));
        requestPresenterInterface.updateStatusInFirestore(helperEmail,patientEmail,status);
    }

    @Override
    public void addHelperToFirestore(String helperEmail,String patientEmail) {
        requestPresenterInterface = new RequestPresenter(this,Repository.getInstance(this,ConcreteLocalSource.getInstance(this), FirebaseWork.getInstance(this)));
        requestPresenterInterface.addHelperToFirestore(helperEmail,patientEmail);
    }

    @Override
    public void successToLoadRequests(List<String> helper_email) {
        if (helper_email.size() == 0) {
            Log.d("TAG", "successToFetchHelpers: " + "NO requests");
            requestAdapter.removeRequests();
            Toast.makeText(getApplicationContext(), "You don't have any requests", Toast.LENGTH_SHORT).show();
        } else {
            requestAdapter.setRequests(helper_email);
        }
    }

    @Override
    public void failedToLoadRequests(String msg) {
        Log.d("TAG", "failedToFetchHelpers: " + "FAILED TO FETCH requests");
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}