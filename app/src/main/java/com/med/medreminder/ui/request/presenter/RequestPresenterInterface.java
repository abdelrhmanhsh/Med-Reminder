package com.med.medreminder.ui.request.presenter;

import com.med.medreminder.firebase.FirebaseLoadRequestsDelegate;

public interface RequestPresenterInterface {
    void updateStatusInFirestore(String helperEmail,String patientEmail,String status);
    void addHelperToFirestore(String helperEmail,String patientEmail);
    void loadRequests(String myEmail);

}
