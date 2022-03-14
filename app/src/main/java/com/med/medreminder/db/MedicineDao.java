package com.med.medreminder.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.med.medreminder.model.Medicine;

import java.util.List;

@Dao
public interface MedicineDao {

    @Insert
    void insertMedicine(Medicine medicine);

    @Query("Select * From medicines Where id =:id")
    LiveData<Medicine> getMedicineById(int id);

    @Update
    void updateMedicine(Medicine medicine);

    @Delete
    void deleteMedicine(Medicine medicine);

    @Query("SELECT * from medicines")
    LiveData<List<Medicine>> getAllMedicines();

}
