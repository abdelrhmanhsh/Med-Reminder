package com.med.medreminder.ui.request.view;

import java.util.List;

public interface RequestViewInterface {

    void updateStatusInFirestore(String helperEmail,String patientEmail,String status);
    void addHelperToFirestore(String helperEmail,String patientEmail);

    void successToLoadRequests(List<String> helper_email);
    void failedToLoadRequests(String msg);


}
