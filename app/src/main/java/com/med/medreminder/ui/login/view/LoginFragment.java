package com.med.medreminder.ui.login.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.med.medreminder.R;
import com.med.medreminder.ui.homepage.view.CalendarHomeAdapter;
import com.med.medreminder.ui.homepage.view.CalendarUtils;
import com.med.medreminder.ui.homepage.view.HomeActivity;

import java.time.LocalDate;
import java.util.ArrayList;


public class LoginFragment extends Fragment implements CalendarHomeAdapter.OnItemListener{

    private static final int RC_SIGN_IN = 10;
    SignInButton sign_in_google_btn;

    ImageView cancel_ic;
    ImageView back_ic;
    ImageView next_ic;
    Button login_btn;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    FirebaseDatabase database;
    DatabaseReference myRef;

    RecyclerView days_rv;
    TextView monthYearTV;


    public LoginFragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CalendarUtils.selectedDate = LocalDate.now();
        monthYearTV = view.findViewById(R.id.monthYearTV);
        days_rv = view.findViewById(R.id.days_rv);
        back_ic = view.findViewById(R.id.back_ic);
        next_ic = view.findViewById(R.id.next_ic);

        back_ic.setOnClickListener(view1 -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
            setMonthView();
        });

        next_ic.setOnClickListener(view1 -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
            setMonthView();
        });

        setMonthView();

        mAuth = FirebaseAuth.getInstance();
        login_btn = view.findViewById(R.id.login_btn);

        sign_in_google_btn = view.findViewById(R.id.sign_in_google_btn);
        cancel_ic = view.findViewById(R.id.cancel_ic);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        sign_in_google_btn.setOnClickListener(view1 -> {
            loginWithGoogle();
        });

        login_btn.setOnClickListener(view1 -> {
          getContext().startActivity(new Intent(getContext(),HomeActivity.class));
        });

/*
        FragmentManager aFragmentManager = getActivity()
                .getSupportFragmentManager();
*/

        cancel_ic.setOnClickListener(view1 -> {
           findNavController(this).popBackStack();

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        monthYearTV.setText(CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = CalendarUtils.daysInMonthArray(CalendarUtils.selectedDate);
        CalendarHomeAdapter calendarAdapter = new CalendarHomeAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        days_rv.setLayoutManager(layoutManager);
        days_rv.setAdapter(calendarAdapter);
    }

    public static NavController findNavController(@NonNull Fragment fragment) {

        View view = fragment.getView();
        return Navigation.findNavController(view);

    }

    public void loginWithGoogle(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("350185735188-l93uh9iporegenf9avndd82k80mj5515.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("TAG", "onComplete: " + user.getEmail());

                            myRef.push().setValue(user.getEmail());

                            Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            getContext().startActivity(new Intent(getContext(),HomeActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date) {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }
}