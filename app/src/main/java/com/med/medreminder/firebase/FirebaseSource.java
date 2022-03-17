package com.med.medreminder.firebase;

import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.User;

public interface FirebaseSource {

    void addUserToFirestore(User user);
    void addMedToFirestore(Medicine medicine, String email);
    void updateStatusInFirestore(String helperEmail,String patientEmail,String status);
    void showActiveMedFirestore(String email);
    void addHelperToFirestore(String helperEmail,String patientEmail);
    void addRequestsToFirestore(String email,String name,String status,String helper_email);


    }