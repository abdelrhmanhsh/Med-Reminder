package com.med.medreminder.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
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
import com.med.medreminder.ui.homepage.view.HomeActivity;
import com.med.medreminder.ui.signup.view.SignupFragment;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class FirebaseWork implements FirebaseSource {

    private static FirebaseWork localSource = null;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    public static final String TAG = "FirebaseWork";


    public FirebaseWork() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    public static FirebaseWork getInstance() {
        if (localSource == null) {
            localSource = new FirebaseWork();
        }
        return localSource;
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
                } else {
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
                        if (task.getResult().getSignInMethods().isEmpty()) {
                            firebaseDelegate.newUser();
                        } else {
                            firebaseDelegate.userExist("This email already exist");
                        }
                    }
                });
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


    public void updateStatusInFirestore(String helperEmail, String patientEmail, String status) {
        Log.d("TAG", "Helper Email: " + helperEmail);
        Log.d("TAG", "Patient Email:" + patientEmail);
        Log.d("TAG", "Status: " + status);
        //  db = FirebaseFirestore.getInstance();

        CollectionReference dbRequests = db.collection("Users");
        dbRequests.document(helperEmail).collection("Requests").document(patientEmail).update("status", status)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "onSuccess: ");
                        } else {
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

    @Override
    public void loginWithGoogle(Context context, firebaseLoginDelegate firebaseLoginDelegate) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Constants.GOOGLE_REQUEST_ID_TOKEN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        firebaseLoginDelegate.intentResultFromGoogleLogin(signInIntent);

        //startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }

    @Override
    public void isUserExistFromGoogleLogin(String email, firebaseLoginDelegate firebaseLoginDelegate, User user, String idToken) {
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.getResult().getSignInMethods().isEmpty()) {
                            firebaseLoginDelegate.newUser(user, idToken);
                        } else {
                            firebaseLoginDelegate.userExist(email, idToken);
                        }
                    }
                });
    }

    @Override
    public void addUserToFirestoreGoogleLogin(User user, firebaseLoginDelegate firebaseLoginDelegate, String idToken, Context context) {
        CollectionReference dbUsers = db.collection("Users");
        //db.document(user.getEmail());
        // below method is use to add data to Firebase Firestore.
        //yourPreference.saveData(YOUR_KEY,YOUR_VALUE);
        dbUsers.document(user.getEmail()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
//                    Toast.makeText(getContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
//                    firebaseAuthWithGoogle(idToken,user.getEmail());
                    firebaseLoginDelegate.firebaseAuthWithGoogle(idToken, user.getEmail(), context);
                } else {
//                    Log.d("TAG", "onFailure: ");
//                    progressbar.setVisibility(View.GONE);
//
//                    Toast.makeText(getContext(), "Fail to create account \n" , Toast.LENGTH_LONG).show();
                    firebaseLoginDelegate.failedToAddUserToFireStore("Fail to create account");
                }
            }
        });
    }

    @Override
    public void authWithGoogle(String idToken, String email, Context context, firebaseLoginDelegate firebaseLoginDelegate) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Log.d("TAG", "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            Log.d("TAG", "onComplete: " + user.getEmail());
//
//                            myRef.push().setValue(user.getEmail());
                            db.collection("Users").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    User user = documentSnapshot.toObject(User.class);
//                                    Log.d("TAG", "onSuccess: " + documentSnapshot);
//                                    Log.d("TAG", "onSuccess: " + user.getFirstName());
                                    YourPreference yourPrefrence = YourPreference.getInstance(context);
                                    yourPrefrence.saveData(Constants.IS_LOGIN, "true");
                                    yourPrefrence.saveData(Constants.FIRST_NAME, user.getFirstName());
                                    //  Log.d("TAG", "onSuccess:-------------------- " + yourPrefrence.getData(Constants.FIRST_NAME));

                                    yourPrefrence.saveData(Constants.SECOND_NAME, user.getSecondName());
                                    yourPrefrence.saveData(Constants.EMAIL, user.getEmail());

                                    firebaseLoginDelegate.authWithGoogleSuccess("Login Successfully");
                                    //progressbar.setVisibility(View.GONE);

//                                    Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
//                                    getContext().startActivity(new Intent(getContext(), HomeActivity.class));
                                }
                            });


                        } else {

                            firebaseLoginDelegate.authWithGoogleFailed("Login Failed");

                            // If sign in fails, display a message to the user.
//                            progressbar.setVisibility(View.GONE);
//
//                            Log.w("TAG", "signInWithCredential:failure", task.getException());
//                            Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void login(String email, String password, Context context, firebaseLoginDelegate firebaseLoginDelegate) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Log.d("TAG", "signInWithEmail:success");

                            //FirebaseUser user = mAuth.getCurrentUser();

                            db.collection("Users").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    User user = documentSnapshot.toObject(User.class);
//                                    Log.d("TAG", "onSuccess: " + documentSnapshot);
//                                    Log.d("TAG", "onSuccess: " + user.getFirstName());
                                    YourPreference yourPrefrence = YourPreference.getInstance(context);
                                    yourPrefrence.saveData(Constants.IS_LOGIN, "true");
                                    yourPrefrence.saveData(Constants.FIRST_NAME, user.getFirstName());
                                    yourPrefrence.saveData(Constants.SECOND_NAME, user.getSecondName());
                                    yourPrefrence.saveData(Constants.EMAIL, user.getEmail());

                                    firebaseLoginDelegate.loginSuccessfully("Login successfully");
//                                    progressbar.setVisibility(View.GONE);
//
//
//                                    Intent intent = new Intent(getContext(), HomeActivity.class);
//
//                                    getContext().startActivity(intent);
                                }
                            });

                        } else {
                            firebaseLoginDelegate.loginFailed("Login Failed");
                            // If sign in fails, display a message to the user.
//                            Log.w("TAG", "signInWithEmail:failure", task.getException());
//                            Toast.makeText(getContext(), "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            progressbar.setVisibility(View.GONE);

                        }
                    }
                });
    }

    @Override
    public void getMedicinesOnDateFromFirebase(String email, long time, firebaseHomeMedsDelegate firebaseHomeMedsDelegate) {
        db.collection("Users").document(email).collection("Meds").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Medicine> allMeds = new ArrayList<>();
                List<Medicine> meds = queryDocumentSnapshots.toObjects(Medicine.class);
                for (int i = 0; i < meds.size(); i++) {

                    if (time >= meds.get(i).getStartDateMillis() && time <= meds.get(i).getEndDateMillis()) {
                        allMeds.add(meds.get(i));
                    }
                }
                firebaseHomeMedsDelegate.successToFetchMeds(allMeds);

//                Map<String, Object> data = new HashMap<>();
//                List<DocumentSnapshot> documentSnapshot = queryDocumentSnapshots.getDocuments();
//               // YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
//                for(int i=0; i<documentSnapshot.size(); i++){
//
//                    data = documentSnapshot.get(i).getData();
//                   long startDate = data.get("startDateMillis");
//                    if(status.equals("PENDING")){
//                        emails.add(data.get("email").toString());
//                    }
//                    requestAdapter.setRequests(emails);
//                    requestAdapter.notifyDataSetChanged();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                firebaseHomeMedsDelegate.failedToFetchMeds("Failed to retrieve medicines");
            }
        });
    }


    @Override
    public void addHelperToFirestore(String helperEmail, String patientEmail) {

        Map<String, Object> data = new HashMap<>();
        data.put("helper_email", helperEmail);

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
    public void addRequestsToFirestore(String email, String name, String status, String helper_email) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("email", email);
        data.put("status", status);


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




