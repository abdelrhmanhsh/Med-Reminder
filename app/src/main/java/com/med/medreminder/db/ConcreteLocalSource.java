package com.med.medreminder.db;

import android.content.Context;

import com.med.medreminder.model.Medicine;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ConcreteLocalSource implements LocalSource{

    private MedicineDao dao;
    private static ConcreteLocalSource localSource = null;
    private LiveData<List<Medicine>> medicines;


    public ConcreteLocalSource(Context context) {
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());
        dao = db.medicineDao();
        medicines = dao.getAllMedicines();

    }

    public static ConcreteLocalSource getInstance(Context context){
        if (localSource == null){
            localSource = new ConcreteLocalSource(context);
        }
        return localSource;
    }

    @Override
    public void insert(Medicine medicine) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.insertMedicine(medicine);
            }
        }).start();
    }

    @Override
    public LiveData<List<Medicine>> getAllStoredMedicines() {
        return medicines;
    }


}
