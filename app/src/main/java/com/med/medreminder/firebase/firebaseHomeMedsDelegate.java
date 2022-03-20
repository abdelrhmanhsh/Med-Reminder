package com.med.medreminder.firebase;

import com.med.medreminder.model.Medicine;

import java.util.List;

public interface firebaseHomeMedsDelegate {
   void successToFetchMeds(List<Medicine> meds);
   void failedToFetchMeds(String msg);
}
