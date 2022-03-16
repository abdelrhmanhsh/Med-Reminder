package com.med.medreminder.ui.homepage.presenter;

import android.util.Log;

import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.ui.homepage.view.homeMedViewInterface;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public class HomeMedPresenter implements homeMedPresenterInterface {

    homeMedViewInterface homeMedViewInterface;
    RepositoryInterface repositoryInterface;

    public HomeMedPresenter(homeMedViewInterface homeMedViewInterface, RepositoryInterface repositoryInterface) {
        this.homeMedViewInterface = homeMedViewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    @Override
    public void showAllStoredMedicines(LifecycleOwner owner) {
        Log.d("TAG", "showAllStoredMedicines: " + owner);
        Log.d("TAG", "showAllStoredMedicines: " + repositoryInterface);
        Log.d("TAG", "showAllStoredMedicines: " + repositoryInterface);
       repositoryInterface.getStoredMedicines().observe(owner, new Observer<List<Medicine>>() {
           @Override
           public void onChanged(List<Medicine> medicines) {
               homeMedViewInterface.getAllStoredMedicines(medicines);
           }
       });
    }

    @Override
    public void updateMed(Medicine medicine) {
        repositoryInterface.updateMedicine(medicine);
    }
}
