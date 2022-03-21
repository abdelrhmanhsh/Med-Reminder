package com.med.medreminder.ui.medfriend.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.med.medreminder.R;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseHelper;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.medfriend.presenter.MedFriendPresenter;
import com.med.medreminder.ui.medfriend.presenter.MedFriendPresenterInterface;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;



public class MedFriendActivity extends AppCompatActivity implements MedFriendViewInterface{

    EditText firstName_edt;
    EditText phone_edt;
    EditText email_edt;
    TextView send_txt;
    ImageView exit_img;
    Switch shareSwitch;
    Boolean isAllFieldsChecked;
    String phoneNumber;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String currUserEmail;
    String currUser;
    String helper_email;
    String helper_name;
    MedFriendPresenterInterface medFriendPresenterInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_friend);

        firstName_edt = findViewById(R.id.firstName_edt);
        phone_edt = findViewById(R.id.phone_edt);
        email_edt = findViewById(R.id.email_edt);
        send_txt = findViewById(R.id.send_txt);
        exit_img = findViewById(R.id.exit_img);
        shareSwitch = findViewById(R.id.shareSwitch);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


       /* exit_img.setOnClickListener(view1 -> {
            findNavController(this).popBackStack();

        });*/

        send_txt.setOnClickListener(view1 -> {
            YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
            String isLogin = yourPrefrence.getData(Constants.IS_LOGIN);
            currUser = yourPrefrence.getData(Constants.FIRST_NAME);
            currUserEmail = yourPrefrence.getData(Constants.EMAIL);
            if (isLogin.equals("true") && FirebaseHelper.isInternetAvailable(getApplicationContext())){
                checkHelperEmail();
            }
            else {
                Toast.makeText(this, "You must login first and be connected to the internet!", Toast.LENGTH_SHORT).show();
            }

        });
    }


    //check if medFriend's email exists
    private void checkHelperEmail() {
        isAllFieldsChecked = CheckAllFields();
        if (isAllFieldsChecked) {
            helper_name = firstName_edt.getText().toString();
            helper_email = email_edt.getText().toString();
            phoneNumber = phone_edt.getText().toString();

            if(!helper_email.equals(currUserEmail)){
                mAuth.fetchSignInMethodsForEmail(helper_email)
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                                boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                                if (isNewUser) {
                                    Log.d("TAG", "User not found!");
                                    Toast.makeText(getApplicationContext(), "" + "This email address doesn't exist !", Toast.LENGTH_LONG).show();
                                } else {
                                    Log.d("TAG", "User found!");
                                    addRequestsToFirestore(currUserEmail,currUser,"PENDING",helper_email);
                                }
                            }
                        });
            }else{
                Toast.makeText(getApplicationContext(), "" + "You can't choose your email!", Toast.LENGTH_LONG).show();
            }
        }
    }

    //send my email and name
    @Override
    public void addRequestsToFirestore(String email, String name, String status, String helper_email) {
        medFriendPresenterInterface = new MedFriendPresenter(Repository.getInstance(this, ConcreteLocalSource.getInstance(this), FirebaseWork.getInstance(this)));
        medFriendPresenterInterface.addRequestsToFirestore(email,name,status,helper_email);
    }

    private boolean CheckAllFields() {
        if (firstName_edt.length() == 0) {
            firstName_edt.setError("Please enter first name");
            return false;
        }

        if (phone_edt.length() == 0) {
            phone_edt.setError("Please enter phone number");
            return false;
        }
        if (email_edt.length() == 0) {
            email_edt.setError("Please enter email address");
            return false;
        }

        return true;
    }


   /* public static NavController findNavController(@NonNull Activity activity) {
        View view = activity.getCurrentFocus();
        return Navigation.findNavController(view);

    }*/
}