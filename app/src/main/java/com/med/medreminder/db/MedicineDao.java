package com.med.medreminder.db;

import androidx.room.Dao;
import androidx.room.Insert;

import com.med.medreminder.model.Medicine;

@Dao
public interface MedicineDao {

    @Insert
    void insertMedicine(Medicine medicine);

}
