package com.med.medreminder.ui.addmedicine.view;

import com.med.medreminder.model.Medicine;

public interface AddMedViewInterface {

    void addMed(Medicine medicine);
    void addMedToFirestore(Medicine medicine, String email, long id);

}
