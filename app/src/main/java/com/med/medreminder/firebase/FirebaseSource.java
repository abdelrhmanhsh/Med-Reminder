package com.med.medreminder.firebase;

import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.User;

public interface FirebaseSource {

    void addUserToFirestore(User user,firebaseDelegate firebaseDelegate);
    void isUserExist(String email, firebaseDelegate firebaseDelegate);
    void addMedToFirestore(Medicine medicine, String email);
    void updateStatusInFirestore(String helperEmail,String patientEmail,String status);
    void signup(String email, String password, firebaseDelegate firebaseDelegate, User user);

    }