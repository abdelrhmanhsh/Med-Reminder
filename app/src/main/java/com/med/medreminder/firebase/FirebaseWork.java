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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.med.medreminder.R;
import com.med.medreminder.db.AppDatabase;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.db.MedicineDao;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.User;
import com.med.medreminder.ui.signup.view.SignupFragment;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public void updateStatusInFirestore(String helperEmail,String patientEmail,String status) {
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

    public void showActiveMedFirestore(String email){
        CollectionReference dbRequests = db.collection("Users");
        db.collection("Users").document(email).collection("Meds").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> meds = new ArrayList<>();
                Map<String, Object> data = new HashMap<>();
                List<DocumentSnapshot> documentSnapshot = queryDocumentSnapshots.getDocuments();
                for(int i=0; i<documentSnapshot.size(); i++){
                    data = documentSnapshot.get(i).getData();
                    meds.add(data.toString());
                }
            }
        });


    }

    @Override
    public void addHelperToFirestore(String helperEmail, String patientEmail) {

            Map<String, Object> data = new HashMap<>();
            data.put("helper_email",helperEmail);

            CollectionReference dbRequests = db.collection("Users");
            dbRequests.document(patientEmail).collection("Helpers").document(helperEmail)
                    .set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
    }

    @Override
    public void addRequestsToFirestore(String email,String name,String status,String helper_email) {
        Map<String, Object> data = new HashMap<>();
        data.put("name",name);
        data.put("email",email);
        data.put("status",status);


        CollectionReference dbRequests = db.collection("Users");
        dbRequests.document(helper_email).collection("Requests").document(email)
                .set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               // Toast.makeText((), "Request sent successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               // Toast.makeText(getApplicationContext(), "Fail to send request \n", Toast.LENGTH_LONG).show();
            }
        });
    }


}