package com.med.medreminder.model;

import android.content.Context;

import com.med.medreminder.db.LocalSource;
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

    @Override
    public void addUserToFirestore(User user) {

    }

    @Override
    public void addMedToFirestore(Medicine medicine, String email) {
        firebaseSource.addMedToFirestore(medicine, email);
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
