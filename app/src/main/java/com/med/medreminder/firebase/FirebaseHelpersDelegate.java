package com.med.medreminder.firebase;

import java.util.List;

public interface FirebaseHelpersDelegate {
    void successToDisplayRequests(List<String> emails);
    void failedToDisplayRequests(String msg);
}
