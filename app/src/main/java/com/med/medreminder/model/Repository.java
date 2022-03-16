package com.med.medreminder.model;

import android.content.Context;

import com.med.medreminder.db.LocalSource;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Repository implements RepositoryInterface {

    private Context context;
    LocalSource localSource;
    private static Repository repository = null;

    public Repository(Context context, LocalSource localSource) {
        this.context = context;
        this.localSource = localSource;
    }

    public static Repository getInstance(Context context, LocalSource localSource) {
        if(repository == null){
            repository = new Repository(context, localSource);
        }
        return repository;
    }


    @Override
    public void insertMedicine(Medicine medicine) {
        localSource.insert(medicine);
    }

    @Override
    public void updateMedicine(Medicine medicine) {
        localSource.update(medicine);
    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        localSource.delete(medicine);
    }

    @Override
    public LiveData<Medicine> getMedicineById(int id) {
        return localSource.getMedicineById(id);
    }

    @Override
    public LiveData<List<Medicine>> getStoredMedicines() {
        return localSource.getAllStoredMedicines();
    }

    @Override
    public LiveData<List<Medicine>> getActiveMedications(long time) {
        return localSource.getActiveMedications(time);
    }

    @Override
    public LiveData<List<Medicine>> getInactiveMedications(long time) {
        return localSource.getInactiveMedications(time);
    }

    @Override
    public LiveData<List<Medicine>> getActiveMedsOnDateSelected(long time) {
        return localSource.getActiveMedsOnDateSelected(time);
    }
}
