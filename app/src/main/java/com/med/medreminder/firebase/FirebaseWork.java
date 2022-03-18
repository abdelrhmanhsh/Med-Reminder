package com.med.medreminder.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.User;

public class FirebaseWork implements FirebaseSource {

    public static final String TAG = "FirebaseWork";
    private static FirebaseWork localSource = null;

    private FirebaseFirestore db;

    public FirebaseWork() {
        db = FirebaseFirestore.getInstance();
    }

    public static FirebaseWork getInstance(){
        if (localSource == null){
            localSource = new FirebaseWork();
        }
        return localSource;
    }

    @Override
    public void addUserToFirestore(User user) {

    }

    @Override
    public void addMedToFirestore(Medicine medicine, String email, long id) {
        CollectionReference dbUsers = db.collection("Users");

        dbUsers.document(email).collection("Meds").document(String.valueOf(id))
                .set(medicine).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i(TAG, "onSuccess: med added to firestore with id: " + id);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e.fillInStackTrace());
            }
        });
    }

    @Override
    public void updateMedFirestore(Medicine medicine, String email, long id) {
        CollectionReference dbUsers = db.collection("Users");

        dbUsers.document(email).collection("Meds").document(String.valueOf(id))
                .set(medicine).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i(TAG, "onSuccess: med updated to firestore with id: " + id);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e.fillInStackTrace());
            }
        });
    }

    @Override
    public void deleteMedFirestore(String email, long id) {
        CollectionReference dbUsers = db.collection("Users");

        dbUsers.document(email).collection("Meds").document(String.valueOf(id))
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i(TAG, "onSuccess: delete from firestore successfully: " + id);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e.fillInStackTrace());
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


}