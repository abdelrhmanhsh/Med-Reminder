package com.med.medreminder.firebase;

import java.util.List;

public interface FirebaseLoadRequestsDelegate {
    void successToLoadRequests(List<String> helper_email);
    void failedToLoadRequests(String msg);
}
