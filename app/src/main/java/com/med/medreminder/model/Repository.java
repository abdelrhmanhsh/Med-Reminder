package com.med.medreminder.model;

import android.content.Context;

import com.med.medreminder.db.LocalSource;
import com.med.medreminder.firebase.FirebaseDelegate;
import com.med.medreminder.firebase.FirebaseSource;
import com.med.medreminder.firebase.FirebaseWork;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Repository implements RepositoryInterface {

    private Context context;
    LocalSource localSource;
    FirebaseSource firebaseSource;
    private static Repository repository = null;

    public Repository(Context context, LocalSource localSource, FirebaseSource firebaseSource) {
        this.context = context;
        this.localSource = localSource;
        this.firebaseSource = firebaseSource;
    }

    public static Repository getInstance(Context context, LocalSource localSource, FirebaseSource firebaseSource) {
        if(repository == null){
            repository = new Repository(context, localSource, firebaseSource);
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
    public void updateAllMedicines(String email) {
        localSource.updateAllMedicines(email);
    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        localSource.delete(medicine);
    }

    @Override
    public void deleteAllMedicines() {
        localSource.deleteAllMedicines();
    }

    @Override
    public LiveData<Medicine> getMedicineById(long id) {
        return localSource.getMedicineById(id);
    }

    @Override
    public LiveData<List<Medicine>> getStoredMedicines() {
        return localSource.getAllStoredMedicines();
    }

    @Override
    public LiveData<List<Medicine>> getActiveMedications(long time, String email) {
        return localSource.getActiveMedications(time, email);
    }

    @Override
    public LiveData<List<Medicine>> getInactiveMedications(long time, String email) {
        return localSource.getInactiveMedications(time, email);
    }

    @Override
    public LiveData<List<Medicine>> getActiveMedsOnDateSelected(long time, String email) {
        return localSource.getActiveMedsOnDateSelected(time, email);
    }

//    @Override
//    public void insertMedStatus(MedStatus medStatus) {
//        localSource.insertMedStatus(medStatus);
//    }
//
//    @Override
//    public LiveData<List<MedStatus>> getMedStatus(String date, String email) {
//        return localSource.getMedStatus(date, email);
//    }

    @Override
    public void addUserToFirestore(User user) {

    }

    @Override
    public void addMedToFirestore(Medicine medicine, String email, long id) {
        firebaseSource.addMedToFirestore(medicine, email, id);
    }

    @Override
    public void updateMedFirestore(Medicine medicine, String email, long id) {
        firebaseSource.updateMedFirestore(medicine, email, id);
    }

    @Override
    public void deleteMedFirestore(String email, long id) {
        firebaseSource.deleteMedFirestore(email, id);
    }

    @Override
    public void updateStatusInFirestore(String helperEmail, String patientEmail, String status) {
        firebaseSource.updateStatusInFirestore(helperEmail,patientEmail,status);
    }

    @Override
    public void addHelperToFirestore(String helperEmail, String patientEmail) {
        firebaseSource.addHelperToFirestore(helperEmail,patientEmail);
    }

    @Override
    public void addRequestsToFirestore(String email, String name, String status, String helper_email) {
        firebaseSource.addRequestsToFirestore(email,name,status,helper_email);
    }


    @Override
    public void showActiveMedFirestore(String email) {
        firebaseSource.showActiveMedFirestore(email);
    }




}
