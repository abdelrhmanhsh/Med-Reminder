package com.med.medreminder.ui.homepage.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.med.medreminder.R;
import com.med.medreminder.databinding.ActivityHomeBinding;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseHelper;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    //FirebaseUser currentUser;
    String currUserName;
    YourPreference yourPreference;
    RepositoryInterface repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



//        currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null){
//            currUserName = currentUser.getDisplayName()
//        }

        repo = Repository.getInstance(this, ConcreteLocalSource.getInstance(this),
                FirebaseWork.getInstance(this));
//        String email = FirebaseHelper.getUserEmail(this);
//        deleteAllMedicines();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        yourPreference = YourPreference.getInstance(this);
        binding.toolbar.setOnClickListener(view -> {

        });

        Log.d("TAG", "onSuccess:**********!!!!!!!!!! " + yourPreference.getData(Constants.FIRST_NAME));

            if (yourPreference.getData(Constants.isMedFriend).equals("true")) {
                binding.usernameTxt.setText(yourPreference.getData(Constants.MED_FRIEND_EMAIL));
            } else {
                binding.usernameTxt.setText(yourPreference.getData(Constants.FIRST_NAME));
            }

    }


    public void deleteAllMedicines() {
        repo.deleteAllMedicines();
    }

}