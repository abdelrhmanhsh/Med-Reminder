package com.med.medreminder.firebase;

import android.content.Context;

import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.User;

public interface FirebaseSource {

    void addUserToFirestore(User user,firebaseDelegate firebaseDelegate);
    void isUserExist(String email, firebaseDelegate firebaseDelegate);
    void addMedToFirestore(Medicine medicine, String email);
    void updateStatusInFirestore(String helperEmail,String patientEmail,String status);
    void signup(String email, String password, firebaseDelegate firebaseDelegate, User user);

    void loginWithGoogle(Context context, firebaseLoginDelegate firebaseLoginDelegate);
    void isUserExistFromGoogleLogin(String email, firebaseLoginDelegate firebaseLoginDelegate, User user, String idToken);
    void addUserToFirestoreGoogleLogin(User user, firebaseLoginDelegate firebaseLoginDelegate, String idToken, Context context);
    void authWithGoogle(String idToken, String email, Context context, firebaseLoginDelegate firebaseLoginDelegate);
    void login(String email, String password, Context context, firebaseLoginDelegate firebaseLoginDelegate);

    void getMedicinesOnDateFromFirebase(String email, long time, firebaseHomeMedsDelegate firebaseHomeMedsDelegate);

    }