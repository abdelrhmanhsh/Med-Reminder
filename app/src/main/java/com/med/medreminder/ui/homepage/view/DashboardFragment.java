package com.med.medreminder.ui.homepage.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.med.medreminder.R;
import com.med.medreminder.databinding.FragmentDashboardBinding;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseHelper;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.model.User;
import com.med.medreminder.ui.addmedicine.view.AddMedActivity;
import com.med.medreminder.ui.medicationScreen.presenter.InactivePresenter;
import com.med.medreminder.ui.medicationScreen.presenter.InactivePresenterInterface;
import com.med.medreminder.ui.medicationScreen.presenter.ActivePresenter;
import com.med.medreminder.ui.medicationScreen.presenter.ActivePresenterInterface;
import com.med.medreminder.ui.medicationScreen.view.ActiveMedViewInterface;
import com.med.medreminder.ui.medicationScreen.view.ActiveMedsAdapter;
import com.med.medreminder.ui.medicationScreen.view.InactiveMedViewInterface;
import com.med.medreminder.ui.medicationScreen.view.InactiveMedsAdapter;
import com.med.medreminder.ui.medicationScreen.view.OnActiveMedClickListener;
import com.med.medreminder.ui.medicationScreen.view.OnInactiveMedClickListener;
import com.med.medreminder.ui.request.view.RequestsActivity;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DashboardFragment extends Fragment implements OnInactiveMedClickListener, OnActiveMedClickListener, ActiveMedViewInterface, InactiveMedViewInterface {

    private RecyclerView activeMeds;
    private RecyclerView inactiveMeds;
    private ActiveMedsAdapter activeAdapter;
    private InactiveMedsAdapter inactiveAdapter;
    private Button medBtn;
    ActivePresenterInterface activePresenterInterface;
    InactivePresenterInterface inactivePresenterInterface;
    private FragmentDashboardBinding binding;
    private FirebaseFirestore db;
    long curDate;
    YourPreference yourPreference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activeMeds = view.findViewById(R.id.recyclerView_ActiveMeds);
        inactiveMeds = view.findViewById(R.id.recyclerView_InctiveMeds);
        medBtn = view.findViewById(R.id.medBtn);
        curDate  = Calendar.getInstance().getTimeInMillis();

        activeMeds.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activeMeds.setLayoutManager(layoutManager);
        activeAdapter = new ActiveMedsAdapter(this,getContext());
        activeMeds.setAdapter(activeAdapter);

        yourPreference = YourPreference.getInstance(getContext());

        inactiveMeds.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        inactiveMeds.setLayoutManager(layoutManager2);
        inactiveAdapter = new InactiveMedsAdapter(this,getContext());
        inactiveMeds.setAdapter(inactiveAdapter);
        String email = FirebaseHelper.getUserEmail(getContext());

        activePresenterInterface = new ActivePresenter(this, (Repository.getInstance(getContext(),ConcreteLocalSource.getInstance(getContext()), FirebaseWork.getInstance(getContext()))));
        inactivePresenterInterface = new InactivePresenter(this,(Repository.getInstance(getContext(),ConcreteLocalSource.getInstance(getContext()), FirebaseWork.getInstance(getContext()))));

        medBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddMedActivity.class));
            }
        });

        YourPreference yourPrefrence = YourPreference.getInstance(getContext());
        //get all medications from firestore
        if (FirebaseHelper.isInternetAvailable(getContext())){
            if(yourPrefrence.getData(Constants.isMedFriend).equals("true"))
            {
                //med friend
                activePresenterInterface.getActiveMedsFromFirebase(yourPrefrence.getData(Constants.MED_FRIEND_EMAIL),curDate);
                inactivePresenterInterface.getInactiveMedsFromFirebase(yourPrefrence.getData(Constants.MED_FRIEND_EMAIL),curDate);
            } else {
                activePresenterInterface.getActiveMedsFromFirebase(yourPrefrence.getData(Constants.EMAIL),curDate);
                inactivePresenterInterface.getInactiveMedsFromFirebase(yourPrefrence.getData(Constants.EMAIL),curDate);
            }
        }
        else{
            if(yourPrefrence.getData(Constants.isMedFriend).equals("true")){
                Toast.makeText(getContext(), "Internet disconnected!", Toast.LENGTH_SHORT).show();
            } else {
                activePresenterInterface.showActiveStoredMedicines(getViewLifecycleOwner(), email);
                inactivePresenterInterface.showInactiveStoredMedicines(getViewLifecycleOwner(), email);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void getActiveMeds(List<Medicine> medicines) {
        if(medicines.size()==0){

        }else{
            activeAdapter.setMedInfo(medicines);
        }
    }

    @Override
    public void getInactiveMeds(List<Medicine> medicines) {
        inactiveAdapter.setInactiveMedInfo(medicines);
    }

    @Override
    public void successToFetchInactiveMeds(List<Medicine> meds) {
        if(meds.size() == 0){
            inactiveAdapter.removeMeds();
        } else {
            inactiveAdapter.setInactiveMedInfo(meds);
        }
    }

    @Override
    public void failedToFetchInactiveMeds(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateInactiveMed(Medicine medicine) {
        inactivePresenterInterface.updateMed(medicine);
    }

    @Override
    public void successToFetchMeds(List<Medicine> meds) {
        if(meds.size() == 0){
            activeAdapter.removeMeds();
            Toast.makeText(getContext(), "You don't have any active medications", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("TAG", "DASHBOARD: successToFetchMeds: "+ meds.get(0).getName());
            Log.d("TAG", "DASHBOARD: successToFetchMeds: "+ meds.size());
            activeAdapter.setMedInfo(meds);
        }
    }

    @Override
    public void failedToFetchMeds(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateMed(Medicine medicine) {
        activePresenterInterface.updateMed(medicine);
    }


    @Override
    public void onActiveCLick(Medicine medicine) {
        if (!yourPreference.getData(Constants.isMedFriend).equals("true")) {
            Bundle bundle = new Bundle();
            bundle.putLong("id", medicine.getId());
            bundle.putBoolean("suspended", false);
            NavController navController = Navigation.findNavController(getView());
            navController.navigate(R.id.actionNavigationDashboardToDisplayEditMedicationGraph, bundle);
        }

    }

    @Override
    public void onInactiveClick(Medicine medicine) {
        if (!yourPreference.getData(Constants.isMedFriend).equals("true")) {
            Bundle bundle = new Bundle();
            bundle.putLong("id", medicine.getId());
            bundle.putBoolean("suspended", true);
            NavController navController = Navigation.findNavController(getView());
            navController.navigate(R.id.actionNavigationDashboardToDisplayEditMedicationGraph, bundle);
        }

    }



 }

