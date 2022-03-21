package com.med.medreminder.ui.login.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.med.medreminder.R;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Repository;
import com.med.medreminder.model.User;
import com.med.medreminder.ui.homepage.view.HomeActivity;
import com.med.medreminder.ui.login.presenter.LoginPresenter;
import com.med.medreminder.ui.login.presenter.loginPresenterInterface;
import com.med.medreminder.utils.Constants;
import com.med.medreminder.utils.YourPreference;


public class LoginFragment extends Fragment implements loginViewInterface {

    SignInButton sign_in_google_btn;

    ImageView cancel_ic;
    Button login_btn;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    FirebaseDatabase database;
    // DatabaseReference myRef;
    private FirebaseFirestore db;

    loginPresenterInterface loginPresenterInterface;

    ProgressBar progressbar;
    TextInputEditText email_edt;
    TextInputEditText password_edt;
    boolean isAllFieldsChecked;
    String email,password;


    public LoginFragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        mAuth = FirebaseAuth.getInstance();
        login_btn = view.findViewById(R.id.login_btn);

        sign_in_google_btn = view.findViewById(R.id.sign_in_google_btn);
        cancel_ic = view.findViewById(R.id.cancel_ic);
        email_edt = view.findViewById(R.id.email_edt);
        password_edt = view.findViewById(R.id.password_edt);

        database = FirebaseDatabase.getInstance();
        //myRef = database.getReference("users");
        progressbar = view.findViewById(R.id.progressbar);
        db = FirebaseFirestore.getInstance();

        loginPresenterInterface = new LoginPresenter(Repository.getInstance(getContext(),
                ConcreteLocalSource.getInstance(getContext()), FirebaseWork.getInstance(getContext())),this);


        sign_in_google_btn.setOnClickListener(view1 -> {
           // loginWithGoogle();
            progressbar.setVisibility(View.VISIBLE);
            loginPresenterInterface.loginWithGoogle(getContext());
        });

        login_btn.setOnClickListener(view1 -> {
            //login();
            progressbar.setVisibility(View.VISIBLE);

            isAllFieldsChecked = CheckAllFields();

            if (isAllFieldsChecked){
                email = email_edt.getText().toString();
                password = password_edt.getText().toString();

                loginPresenterInterface.login(email,password,getContext());

            }
        });


        cancel_ic.setOnClickListener(view1 -> {
            findNavController(this).popBackStack();

        });
    }

    private void login() {
        progressbar.setVisibility(View.VISIBLE);

        isAllFieldsChecked = CheckAllFields();

        if (isAllFieldsChecked){
            email = email_edt.getText().toString();
            password = password_edt.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithEmail:success");

                                //FirebaseUser user = mAuth.getCurrentUser();

                                db.collection("Users").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        User user = documentSnapshot.toObject(User.class);
                                        Log.d("TAG", "onSuccess: " + documentSnapshot);
                                        Log.d("TAG", "onSuccess: " + user.getFirstName());
                                        YourPreference yourPrefrence = YourPreference.getInstance(getContext());
                                        yourPrefrence.saveData(Constants.IS_LOGIN,"true");
                                        yourPrefrence.saveData(Constants.FIRST_NAME,user.getFirstName());
                                        yourPrefrence.saveData(Constants.SECOND_NAME,user.getSecondName());
                                        yourPrefrence.saveData(Constants.EMAIL,user.getEmail());

                                        progressbar.setVisibility(View.GONE);


                                        Intent intent = new Intent(getContext(), HomeActivity.class);

                                        getContext().startActivity(intent);
                                    }
                                });

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                Toast.makeText(getContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                progressbar.setVisibility(View.GONE);

                            }
                        }
                    });
        }

    }


    public static NavController findNavController(@NonNull Fragment fragment) {

        View view = fragment.getView();
        return Navigation.findNavController(view);

    }

//    public void loginWithGoogle(){
//        // Configure Google Sign In
//        progressbar.setVisibility(View.VISIBLE);
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(Constants.GOOGLE_REQUEST_ID_TOKEN)
//                .requestEmail()
//                .build();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
//
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Constants.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());

                //call method and pass email
                loginPresenterInterface.isUserExistInGoogleLogin(account.getEmail(),new User(account.getGivenName(),account.getFamilyName()
                ,"","", account.getEmail(),""),account.getIdToken());


//                mAuth.fetchSignInMethodsForEmail(account.getEmail())
//                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//
//                                boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
//
//                                if (isNewUser) {
//                                    Log.d("TAG", "Is New User!");
//                                    addDataToFirestore(new User(account.getGivenName(),account.getFamilyName(),"", "",account.getEmail(),""),account.getIdToken());
//                                } else {
//                                    firebaseAuthWithGoogle(account.getIdToken(),account.getEmail());
//
//                                }
//
//                            }
//                        });
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void addDataToFirestore(User user,String idToken) {
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
                    firebaseAuthWithGoogle(idToken,user.getEmail());

                }
                else {
                    Log.d("TAG", "onFailure: ");
                    progressbar.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "Fail to create account \n" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void firebaseAuthWithGoogle(String idToken,String email) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            Log.d("TAG", "onComplete: " + user.getEmail());
//
//                            myRef.push().setValue(user.getEmail());
                            db.collection("Users").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    User user = documentSnapshot.toObject(User.class);
                                    Log.d("TAG", "onSuccess: " + documentSnapshot);
                                    Log.d("TAG", "onSuccess: " + user.getFirstName());
                                    YourPreference yourPrefrence = YourPreference.getInstance(getContext());
                                    yourPrefrence.saveData(Constants.IS_LOGIN,"true");
                                    yourPrefrence.saveData(Constants.FIRST_NAME,user.getFirstName());
                                    Log.d("TAG", "onSuccess:-------------------- " + yourPrefrence.getData(Constants.FIRST_NAME));

                                    yourPrefrence.saveData(Constants.SECOND_NAME,user.getSecondName());
                                    yourPrefrence.saveData(Constants.EMAIL,user.getEmail());

                                    progressbar.setVisibility(View.GONE);

                                    Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                                    getContext().startActivity(new Intent(getContext(),HomeActivity.class));
                                }
                            });



                        } else {
                            // If sign in fails, display a message to the user.
                            progressbar.setVisibility(View.GONE);

                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean CheckAllFields() {
        if (email_edt.length() == 0) {
            email_edt.setError("Please enter Email");
            progressbar.setVisibility(View.GONE);
            return false;
        }
        if (password_edt.length() == 0) {
            password_edt.setError("Please enter Password");
            progressbar.setVisibility(View.GONE);
            return false;
        }
        return true;
    }


    @Override
    public void loginWithGoogle() {
    }

    @Override
    public void intentResultForGoogleLogin(Intent loginIntent) {
        startActivityForResult(loginIntent, Constants.RC_SIGN_IN);

    }

    @Override
    public void userExist(String email, String idToken) {
        //call authenticate with google
        loginPresenterInterface.authWithGoogle(email,idToken,getContext());

    }

    @Override
    public void newUser(User user, String idToken) {
        loginPresenterInterface.addUserToFirestore(getViewLifecycleOwner(), user,idToken,getContext());
    }

    @Override
    public void failedToCreateAccount(String msg) {
        progressbar.setVisibility(View.GONE);
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void authWithGoogleSuccess(String msg) {
        progressbar.setVisibility(View.GONE);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        getContext().startActivity(new Intent(getContext(),HomeActivity.class));
    }

    @Override
    public void authWithGoogleFailed(String msg) {
        progressbar.setVisibility(View.GONE);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccessfully(String msg) {
        progressbar.setVisibility(View.GONE);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), HomeActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void loginFailed(String msg) {
        Toast.makeText(getContext(), "Authentication failed",Toast.LENGTH_SHORT).show();
        progressbar.setVisibility(View.GONE);
    }

}