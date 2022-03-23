package com.med.medreminder.firebase;

import com.med.medreminder.model.Medicine;

import java.util.List;

public interface FirebaseInactiveMedDelegate {
    void successToFetchInactiveMeds(List<Medicine> meds);
    void failedToFetchInactiveMeds(String msg);
}
