package com.med.medreminder.db;

import android.content.Context;

import com.med.medreminder.R;
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
    public void update(Medicine medicine) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.updateMedicine(medicine);
            }
        }).start();
    }

    @Override
    public void updateAllMedicines(String email) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.updateAllMedicine(email);
            }
        }).start();
    }

    @Override
    public void updateMedAmount(long id, int newAmount) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.updateMedAmount(id, newAmount);
            }
        }).start();
    }

    @Override
    public void delete(Medicine medicine) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.deleteMedicine(medicine);
            }
        }).start();
    }

    @Override
    public void deleteAllMedicines() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.deleteAllMedicines();
            }
        }).start();
    }

    @Override
    public LiveData<Medicine> getMedicineById(long id) {
        return dao.getMedicineById(id);
    }

    @Override
    public LiveData<List<Medicine>> getAllStoredMedicines() {
        return medicines;
    }

    @Override
    public LiveData<List<Medicine>> getActiveMedications(long time, String email) {
        return dao.getActiveMedications(time, email);
    }

    @Override
    public LiveData<List<Medicine>> getInactiveMedications(long time, String email) {
        return dao.getInactiveMedications(time, email);
    }

    @Override
    public LiveData<List<Medicine>> getActiveMedsOnDateSelected(long time, String email) {
        return dao.getActiveMedicationsOnDateSelected(time, email);
    }



//    @Override
//    public void insertMedStatus(MedStatus medStatus) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                dao.insertMedStatus(medStatus);
//            }
//        }).start();
//    }
//
//    @Override
//    public LiveData<List<MedStatus>> getMedStatus(String date, String email) {
//        return dao.getMedStatus(date, email);
//    }

}
