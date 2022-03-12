package com.med.medreminder.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.med.medreminder.model.Medicine;

import java.util.List;

@Dao
public interface MedicineDao {

    @Insert
    void insertMedicine(Medicine medicine);

    @Query("SELECT * from medicines")
    LiveData<List<Medicine>> getAllMedicines();

}
