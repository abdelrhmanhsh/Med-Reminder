package com.med.medreminder.ui.request.view;

public interface RequestViewInterface {

    void updateStatusInFirestore(String helperEmail,String patientEmail,String status);
}
