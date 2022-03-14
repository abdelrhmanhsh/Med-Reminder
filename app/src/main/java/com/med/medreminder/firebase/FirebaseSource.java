package com.med.medreminder.firebase;

import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.User;

public interface FirebaseSource {

    void addUserToFirestore(User user);
    void addMedToFirestore(Medicine medicine, String email);

}
