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
    long insertMedicine(Medicine medicine);

    @Query("Select * From medicines Where id =:id")
    LiveData<Medicine> getMedicineById(int id);

    @Update
    void updateMedicine(Medicine medicine);

    @Delete
    void deleteMedicine(Medicine medicine);

    @Query("SELECT * from medicines")
    LiveData<List<Medicine>> getAllMedicines();

    @Query("SELECT * FROM medicines WHERE (:time Between startDateMillis AND endDateMillis)")
    LiveData<List<Medicine>> getActiveMedications(long time);

    @Query("SELECT * FROM medicines WHERE (:time > endDateMillis)")
    LiveData<List<Medicine>> getInactiveMedications(long time);

    @Query("SELECT * FROM medicines WHERE (:time Between startDateMillis AND endDateMillis)")
    LiveData<List<Medicine>> getActiveMedicationsOnDateSelected(long time);


    //time = startDate || time <= endDate

    //OR :time <= endDateMillis


}
