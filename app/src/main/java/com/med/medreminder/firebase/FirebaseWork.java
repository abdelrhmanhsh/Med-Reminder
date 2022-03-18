package com.med.medreminder.firebase;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.med.medreminder.R;
import com.med.medreminder.db.AppDatabase;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.db.MedicineDao;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.User;
import com.med.medreminder.ui.signup.view.SignupFragment;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

import java.util.List;

public class FirebaseWork implements FirebaseSource{

    private static FirebaseWork localSource = null;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    public FirebaseWork() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    public static FirebaseWork getInstance(){
        if (localSource == null){
            localSource = new FirebaseWork();
        }
        return localSource;
    }

    @Override
    public void addUserToFirestore(User user, firebaseDelegate firebaseDelegate) {
        CollectionReference dbUsers = db.collection("Users");
        //db.document(user.getEmail());
        // below method is use to add data to Firebase Firestore.
        //yourPreference.saveData(YOUR_KEY,YOUR_VALUE);
        dbUsers.document(user.getEmail()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    firebaseDelegate.userAddedToFirestore("Account created successfully");
//                    Toast.makeText(getContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
//                    progressbar.setVisibility(View.GONE);
//                    findNavController(SignupFragment.this).navigate(R.id.signupToLogin);
                }
                else {
                    firebaseDelegate.userFailToFirestore("Fail to create account");
//                    Log.d("TAG", "onFailure: ");
//                    progressbar.setVisibility(View.GONE);
//
//                    Toast.makeText(getContext(), "Fail to create account \n" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void isUserExist(String email, firebaseDelegate firebaseDelegate) {

        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                         if (task.getResult().getSignInMethods().isEmpty()){
                                firebaseDelegate.newUser();
                         }
                         else {
                             firebaseDelegate.userExist("This email already exist");
                         }
                    }
                });
    }

    @Override
    public void addMedToFirestore(Medicine medicine, String email) {
        CollectionReference dbUsers = db.collection("Users");

        dbUsers.document(email).collection("Meds")
                .add(medicine).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(getContext(), "Med added successfully!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(), "Med added failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateStatusInFirestore(String helperEmail,String patientEmail,String status) {
        Log.d("TAG","Helper Email: "+helperEmail);
        Log.d("TAG","Patient Email:"+patientEmail);
        Log.d("TAG","Status: "+status);
      //  db = FirebaseFirestore.getInstance();

        CollectionReference dbRequests = db.collection("Users");
        dbRequests.document(helperEmail).collection("Requests").document(patientEmail).update("status",status)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "onSuccess: ");
                        }
                        else {
                            Log.d("TAG", "onFailure: ");
                        }
                    }
                });
    }

    @Override
    public void signup(String email, String password, firebaseDelegate firebaseDelegate, User user) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                          //  addUserToFirestore(user);
                            firebaseDelegate.signupSuccess(user);

                        } else {
                          firebaseDelegate.signupFail("Fail to create the account");
                        }
                    }
                });
    }


}