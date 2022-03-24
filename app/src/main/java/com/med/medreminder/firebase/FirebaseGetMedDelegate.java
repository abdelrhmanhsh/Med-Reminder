package com.med.medreminder.firebase;

import com.med.medreminder.model.Medicine;

public interface FirebaseGetMedDelegate {
    void onSuccess(Medicine medicine);
    void onFailure(String msg);
}
