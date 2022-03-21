package com.med.medreminder.firebase;

import com.med.medreminder.model.Medicine;

import java.util.List;

public interface FirebaseActiveMedDelegate {
    void successToFetchActiveMeds(List<Medicine> meds);
    void failedToFetchActiveMeds(String msg);
}
