package com.med.medreminder.firebase;

import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.User;

public interface FirebaseSource {

    void addUserToFirestore(User user);
    void addMedToFirestore(Medicine medicine, String email, long id);
    void updateMedFirestore(Medicine medicine, String email, long id);
    void deleteMedFirestore(String email, long id);
    void updateStatusInFirestore(String helperEmail,String patientEmail,String status);

}