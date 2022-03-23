package com.med.medreminder.ui.displayHelpers.view;

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
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.displayHelpers.presenter.HelperPresenter;
import com.med.medreminder.ui.displayHelpers.presenter.HelperPresenterInterface;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;
import java.util.ArrayList;
import java.util.List;

public class DisplayHelpersActivity extends AppCompatActivity implements HelperViewInterface{

    RecyclerView displayHelper_recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    FirebaseDatabase database;
    YourPreference yourPrefrence;
    String currUserEmail;
    DisplayHelperAdapter adapter;
    List<String> helpersEmail = new ArrayList<>();
    HelperPresenterInterface helperPresenterInterface;

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

        helperPresenterInterface = new HelperPresenter(this, (Repository.getInstance(getApplicationContext(), ConcreteLocalSource.getInstance(getApplicationContext()), FirebaseWork.getInstance(getApplicationContext()))));
        helperPresenterInterface.Helpers(currUserEmail);

        displayHelper_recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        displayHelper_recyclerView.setLayoutManager(layoutManager);
        adapter = new DisplayHelperAdapter(getApplicationContext(),helpersEmail);
        displayHelper_recyclerView.setAdapter(adapter);

    }


    @Override
    public void successToDisplayRequests(List<String> helper_email) {

          if (helper_email.size() == 0) {
            Log.d("TAG", "successToFetchHelpers: " + "NO Helpers");
            adapter.removeHelpers();
            Toast.makeText(getApplicationContext(), "You don't have any helpers", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setHelpers(helper_email);
        }
    }

    @Override
    public void failedToDisplayRequests(String msg) {
        Log.d("TAG", "failedToFetchHelpers: " + "FAILED TO FETCH HELPERS");
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}