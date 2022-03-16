package com.med.medreminder.ui.request.presenter;

public interface RequestPresenterInterface {
    void updateStatusInFirestore(String helperEmail,String patientEmail,String status);

}
