package com.med.medreminder.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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