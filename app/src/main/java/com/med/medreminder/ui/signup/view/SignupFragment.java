package com.med.medreminder.ui.signup.view;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.med.medreminder.R;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Repository;
import com.med.medreminder.model.User;
import com.med.medreminder.ui.signup.presenter.SignupPresenter;
import com.med.medreminder.ui.signup.presenter.signupPresenterInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SignupFragment extends Fragment implements signupViewInterface{

      EditText dob_edt;
    private int mYear, mMonth, mDay;
    Button signup_btn;
    ImageView cancel_ic;
    ProgressBar progressbar;
    EditText firstName_edt;
    EditText secondName_edt;
    EditText password_edt;
    EditText email_edt;
    RadioButton radio_gender;
    RadioGroup gender_radio_btn;

    User user;

    signupPresenterInterface signupPresenterInterface;

    String email, password, firstName, secondName, gender, dob;
    
    boolean isAllFieldsChecked;

    private FirebaseAuth mAuth;

    // creating a variable
    // for firebasefirestore.
    private FirebaseFirestore db;

    public SignupFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dob_edt = view.findViewById(R.id.dob_edt);
        signup_btn = view.findViewById(R.id.signup_btn);
        cancel_ic = view.findViewById(R.id.cancel_ic);
        progressbar = view.findViewById(R.id.progressbar);
        firstName_edt = view.findViewById(R.id.firstName_edt);
        secondName_edt = view.findViewById(R.id.secondName_edt);
        password_edt = view.findViewById(R.id.password_edt);
        email_edt = view.findViewById(R.id.email_edt);
        gender_radio_btn = view.findViewById(R.id.gender_radio_btn);



        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dob_edt.setText(dateFormat.format(new Date())); // it will show 16/07/2013

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // getting our instance
        // from Firebase Firestore.
        db = FirebaseFirestore.getInstance();


        signup_btn.setOnClickListener(view1 -> {
          // getContext().startActivity(new Intent(getContext(), HomeActivity.class));
           //registerNewUser();
            // get selected radio button from radioGroup
            int selectedId = gender_radio_btn.getCheckedRadioButtonId();


            // find the radiobutton by returned id
            radio_gender = view.findViewById(selectedId);
            isAllFieldsChecked = CheckAllFields();

            if (isAllFieldsChecked) {
                progressbar.setVisibility(View.VISIBLE);

                email = email_edt.getText().toString();
                signupPresenterInterface.isUserExist(email);
            }

        });

        signupPresenterInterface = new SignupPresenter(Repository.getInstance(getContext(),
                ConcreteLocalSource.getInstance(getContext()), FirebaseWork.getInstance(getContext())),this);

        cancel_ic.setOnClickListener(view1 -> {
         findNavController(this).popBackStack();

        });

        dob_edt.setOnClickListener(view1 -> {
            final Calendar calendar = Calendar.getInstance ();
            mYear = calendar.get ( Calendar.YEAR );
            mMonth = calendar.get ( Calendar.MONTH );
            mDay = calendar.get ( Calendar.DAY_OF_MONTH );

            //show dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog ( getContext(), R.style.DialogTheme ,new DatePickerDialog.OnDateSetListener () {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dob_edt.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                }
            }, mYear, mMonth, mDay );
            datePickerDialog.show ();


        });
    }

    private void registerNewUser() {
        progressbar.setVisibility(View.VISIBLE);

        isAllFieldsChecked = CheckAllFields();

        if (isAllFieldsChecked) {
            email = email_edt.getText().toString();
            password = password_edt.getText().toString();


//
//            mAuth.fetchSignInMethodsForEmail(email)
//                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//
//                            boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
//
//                            if (isNewUser) {
//                                Log.d("TAG", "Is New User!");
//                                createAccount();
//                            } else {
//                                Log.d("TAG", "Is Old User!");
//                                Toast.makeText(getContext(), "" + "This email address is already exist !", Toast.LENGTH_LONG).show();
//                                progressbar.setVisibility(View.GONE);
//                            }
//
//                        }
//                    });
        }
    }

    private void createAccount(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firstName = firstName_edt.getText().toString();
                            secondName = secondName_edt.getText().toString();
                            gender = radio_gender.getText().toString();
                            dob = dob_edt.getText().toString();

                             //user = new User(firstName, secondName, gender, dob, email, password);
                            // calling method to add data to Firebase Firestore.
                            //task.getResult().getUser().getUid();
                            addDataToFirestore(user);

                        } else {
                            // Registration failed
                            Log.d("TAG", "onComplete: " + task.getResult().toString());

//                                if (email == task.getResult().getUser().getEmail()) {
//                                    Toast.makeText(getContext(), "" + task.getResult().getUser().getEmail() + " " + task.getResult().toString(), Toast.LENGTH_SHORT).show();
//                                }
                            //   else {

                            Toast.makeText(getContext(), "Registration failed!" + " Please try again later", Toast.LENGTH_SHORT).show();
                            //  }

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private boolean CheckAllFields() {
        if (firstName_edt.length() == 0) {
            firstName_edt.setError("Please enter First Name");
            progressbar.setVisibility(View.GONE);
            return false;
        }
        if (secondName_edt.length() == 0) {
            secondName_edt.setError("Please enter Second Name");
            progressbar.setVisibility(View.GONE);
            return false;
        }
        if (dob_edt.length() == 0) {
            dob_edt.setError("Please enter Birth Date");
            progressbar.setVisibility(View.GONE);
            return false;
        }
        if (password_edt.length() == 0) {
            password_edt.setError("Please enter Password");
            progressbar.setVisibility(View.GONE);
            return false;
        }
        if (email_edt.length() == 0) {
            email_edt.setError("Please enter Email");
            progressbar.setVisibility(View.GONE);
            return false;
        }

        return true;
    }

    private void addDataToFirestore(User user) {
        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbUsers = db.collection("Users");
        //db.document(user.getEmail());
        // below method is use to add data to Firebase Firestore.
            //yourPreference.saveData(YOUR_KEY,YOUR_VALUE);
        dbUsers.document(user.getEmail()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);
                    findNavController(SignupFragment.this).navigate(R.id.signupToLogin);
                }
                else {
                    Log.d("TAG", "onFailure: ");
                    progressbar.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "Fail to create account \n" , Toast.LENGTH_LONG).show();
                }
            }
        });

//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
          /*  @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(getContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.GONE);
                findNavController(SignupFragment.this).navigate(R.id.signupToLogin);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.

                Log.d("TAG", "onFailure: "+e.getMessage());
                progressbar.setVisibility(View.GONE);

                Toast.makeText(getContext(), "Fail to create account \n" + e, Toast.LENGTH_LONG).show();
            }
        });*/
    }

    public static NavController findNavController(@NonNull Fragment fragment) {

        View view = fragment.getView();
        return Navigation.findNavController(view);

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void userAlreadyExist(String msg) {
        Toast.makeText(getContext(), "" + msg, Toast.LENGTH_LONG).show();
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void newUser() {
        firstName = firstName_edt.getText().toString();
        secondName = secondName_edt.getText().toString();
        gender = radio_gender.getText().toString();
        dob = dob_edt.getText().toString();
        email = email_edt.getText().toString();
        password = password_edt.getText().toString();
        //user = new User(firstName, secondName, gender, dob, email, password,task.getResult().getUser().getUid());
        user = new User(firstName, secondName, gender, dob, email, password);

        signup(email,password,user);
    }

    @Override
    public void signup(String email, String password, User user) {
        signupPresenterInterface.signup(getViewLifecycleOwner(), email,password,user);
    }

    @Override
    public void signupSuccess(User user) {
        addUserToFirestore(user);
    }

    @Override
    public void signupFail(String msg) {
        progressbar.setVisibility(View.GONE);
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void addUserToFirestore(User user) {
      signupPresenterInterface.addUserToFirestore(user);
    }

    @Override
    public void addUserToFirestoreSuccessfully(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        progressbar.setVisibility(View.GONE);
        findNavController(SignupFragment.this).navigate(R.id.signupToLogin);
    }

    @Override
    public void addUserToFirestoreFailed(String msg) {
        progressbar.setVisibility(View.GONE);
        Toast.makeText(getContext(), msg , Toast.LENGTH_LONG).show();
    }
}