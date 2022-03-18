package com.med.medreminder.ui.medicationScreen.presenter;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.ui.medicationScreen.view.ActiveMedViewInterface;

import java.util.Calendar;
import java.util.List;

public class ActivePresenter implements ActivePresenterInterface {

    ActiveMedViewInterface activeMedViewInterface;
    RepositoryInterface repositoryInterface;
    Long timeNow = Calendar.getInstance().getTimeInMillis();

    public ActivePresenter(ActiveMedViewInterface activeMedViewInterface, RepositoryInterface repositoryInterface) {
        this.activeMedViewInterface = activeMedViewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    @Override
    public void showActiveStoredMedicines(LifecycleOwner owner, String email) {
        repositoryInterface.getActiveMedications(timeNow, email).observe(owner, new Observer<List<Medicine>>() {
            @Override
            public void onChanged(List<Medicine> medicines) {
                activeMedViewInterface.getActiveMeds(medicines);
            }
        });
    }
}
