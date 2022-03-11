package com.med.medreminder.db;


import com.med.medreminder.model.Medicine;

public interface LocalSource {

    void insert(Medicine medicine);

}
