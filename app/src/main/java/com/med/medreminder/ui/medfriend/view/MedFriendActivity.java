package com.med.medreminder.ui.medfriend.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.med.medreminder.R;
import com.med.medreminder.model.User;
import com.med.medreminder.ui.signup.view.SignupFragment;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;

import java.util.HashMap;
import java.util.Map;

public class MedFriendActivity extends AppCompatActivity {

    EditText firstName_edt;
    EditText phone_edt;
    EditText email_edt;
    TextView send_txt;
    ImageView exit_img;
    Switch shareSwitch;
    Boolean isAllFieldsChecked;
    String email, phoneNumber, name;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String currUserEmail;



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


        exit_img.setOnClickListener(view1 -> {
            findNavController(this).popBackStack();

        });

        send_txt.setOnClickListener(view1 -> {
            YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
            String isLogin = yourPrefrence.getData(Constants.IS_LOGIN);
            String currUser = yourPrefrence.getData(Constants.FIRST_NAME);
            currUserEmail = yourPrefrence.getData(Constants.EMAIL);
            Log.d("TAG", "onCreate: ???????????????????????" + currUser);
            Log.d("TAG", "onCreate: ???????????????????????" + currUserEmail);
            if (isLogin.equals("true")){
                checkHelperEmail();
            }
            else {
                Toast.makeText(this, "you must login first !", Toast.LENGTH_SHORT).show();
            }

        });
    }


    //check if medFriend's email exists
    private void checkHelperEmail() {
        isAllFieldsChecked = CheckAllFields();
        if (isAllFieldsChecked) {
            name = firstName_edt.getText().toString();
            email = email_edt.getText().toString();
            phoneNumber = phone_edt.getText().toString();


            mAuth.fetchSignInMethodsForEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                            boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                            if (isNewUser) {
                                Log.d("TAG", "User not found!");
                                Toast.makeText(getApplicationContext(), "" + "This email address doesn't exist !", Toast.LENGTH_LONG).show();
                            } else {
                                Log.d("TAG", "User found!");
//                                YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
//                                String email = yourPrefrence.getData(Constants.EMAIL);
//                                String name = yourPrefrence.getData(Constants.FIRST_NAME);
                                addDataToFirestore(email,name,"pending");
                            }
                        }
                    });
        }
    }

    //send my email and name
    private void addDataToFirestore(String email,String name,String status) {
        Map<String, Object> data = new HashMap<>();
        data.put("name",name);
        data.put("email",email);
        data.put("status",status);

        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbRequests = db.collection("Users");
        // below method is use to add data to Firebase Firestore.
        //yourPreference.saveData(YOUR_KEY,YOUR_VALUE);

        dbRequests.document(currUserEmail).collection("Requests")
                .add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Request sent successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                   Toast.makeText(getApplicationContext(), "Fail to send request \n", Toast.LENGTH_LONG).show();
            }
        });

//        dbRequests.document(email).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(getApplicationContext(), "Request sent successfully", Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.d("TAG", "onFailure: ");
//                    Toast.makeText(getApplicationContext(), "Fail to send request \n", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }

   /* public FirebaseUser getCurrentUser() {
        // check if null or not
        if (mAuth == null) {
            // check
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth.getCurrentUser();
    }*/

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


    public static NavController findNavController(@NonNull Activity activity) {
        //check getCurrentFocus
        View view = activity.getCurrentFocus();
        return Navigation.findNavController(view);

    }
}