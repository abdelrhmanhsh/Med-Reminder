package com.med.medreminder.db;

import android.content.Context;

import com.med.medreminder.model.Medicine;

public class ConcreteLocalSource implements LocalSource{

    private MedicineDao dao;
    private static ConcreteLocalSource localSource = null;

    public ConcreteLocalSource(Context context) {
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());
        dao = db.medicineDao();
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

}
