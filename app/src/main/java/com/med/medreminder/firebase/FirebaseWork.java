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
}