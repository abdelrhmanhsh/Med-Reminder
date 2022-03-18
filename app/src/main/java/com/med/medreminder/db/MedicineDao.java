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
    LiveData<Medicine> getMedicineById(long id);

    @Update
    void updateMedicine(Medicine medicine);

    @Delete
    void deleteMedicine(Medicine medicine);

    @Query("SELECT * from medicines")
    LiveData<List<Medicine>> getAllMedicines();

    @Query("SELECT * FROM medicines WHERE (:time Between startDateMillis AND endDateMillis  AND :email = userEmail) OR (" +
            "startDateMillis <= :time AND endDate='Ongoing treatment'  AND :email = userEmail) AND (endDate!='Suspended')")
    LiveData<List<Medicine>> getActiveMedications(long time, String email);

    @Query("SELECT * FROM medicines WHERE (:time > endDateMillis AND endDate!='Ongoing treatment'  AND :email = userEmail) OR (" +
            ":time <= startDateMillis  AND :email = userEmail)")
    LiveData<List<Medicine>> getInactiveMedications(long time, String email);

    @Query("SELECT * FROM medicines WHERE (:time Between startDateMillis AND endDateMillis AND :email = userEmail) OR (" +
            "startDateMillis <= :time AND endDate='Ongoing treatment' AND :email = userEmail) AND (endDate!='Suspended' AND :email = userEmail)")
    LiveData<List<Medicine>> getActiveMedicationsOnDateSelected(long time, String email);


    //time = startDate || time <= endDate

    //OR :time <= endDateMillis


}
