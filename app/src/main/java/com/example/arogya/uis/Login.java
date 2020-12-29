package com.example.arogya.uis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.arogya.R;
import com.example.arogya.helpers.UIHelper;
import com.example.arogya.manager.NetworkManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;


public class Login extends AppCompatActivity implements TextWatcher {

    private EditText userName;
    private EditText password;
    private Button loginBtn;
    private LoginButton loginButton;

    private TextView registerText;
    private TextInputLayout loginInput, passInput;
    private ProgressBar progressBar;
    private boolean isUserValid, isPassValid;
    private ImageView mLogo;


    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private CallbackManager mCallbackManager;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      SharedPreferences  sharedPreferences = PreferenceManager
//                .getDefaultSharedPreferences(this);
//      if (sharedPreferences.getBoolean())
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
                    AccessToken accessToken = AccessToken.getCurrentAccessToken();
            boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn == false) {


            setContentView(R.layout.login);
            UIHelper.setupUI(findViewById(R.id.loginLayout), this);

            loginInput = findViewById(R.id.loginIdTextInputLayout);
            passInput = findViewById(R.id.passwordTextInputLayout);


            userName = loginInput.getEditText();
            password = findViewById(R.id.password);


            progressBar = findViewById(R.id.webViewProgressBar2);
            loginBtn = findViewById(R.id.loginButton);
            registerText = findViewById(R.id.registerText);

            userName.addTextChangedListener(this);
            password.addTextChangedListener(this);

            UIHelper.disableElement(loginBtn);

            //         Initialize Firebase Auth

            FacebookSdk.sdkInitialize(getApplicationContext());

            mLogo = findViewById(R.id.image_logo);
            loginButton = findViewById(R.id.login_button);
            loginButton.setReadPermissions("email", "public_profile");
            mCallbackManager = CallbackManager.Factory.create();

            final KProgressHUD kProgressHUD = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDimAmount(0.5f);



            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   kProgressHUD.isShowing();

                    LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            kProgressHUD.dismiss();
                            handleFacebookToken(loginResult.getAccessToken());


                        }

                        @Override
                        public void onCancel() {

                            Toast.makeText(Login.this, "User Canceled ", Toast.LENGTH_SHORT).show();
                            kProgressHUD.dismiss();

                        }

                        @Override
                        public void onError(FacebookException error) {

                            Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });


            registerText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent RegistrationIntent = new Intent(Login.this, Registration.class);
                    startActivity(RegistrationIntent);
                }
            });

            loginBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {


                   kProgressHUD.isShowing();
                    if (NetworkManager.isAvailable(Login.this)) {


                        final String loginIDSrt = userName.getText().toString().trim();
                        final String passwordStr = password.getText().toString().trim();

                        mAuth.signInWithEmailAndPassword(loginIDSrt, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    kProgressHUD.dismiss();

                                    Toast.makeText(Login.this, "Done", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(Login.this,Home.class);
                                    startActivity(i);
                                } else {
                                    kProgressHUD.dismiss();
                                    Toast.makeText(Login.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                }
            });

        } else {

            Intent RegistrationIntent = new Intent(Login.this, Home.class);
            startActivity(RegistrationIntent);




        }


    }






        @Override
        public void beforeTextChanged (CharSequence charSequence,int i, int i1, int i2){

        }

        @Override
        public void onTextChanged (CharSequence charSequence,int i, int i1, int i2){

        }

        @Override
        public void afterTextChanged (Editable editable){

            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (editable.hashCode() != 0 && editable.length() > 1) {
                String enteredText = editable.toString().trim().toLowerCase();
                if (editable.hashCode() == userName.getText().hashCode()) {
                    if (enteredText.length() >= 4 && !TextUtils.isDigitsOnly(enteredText) && userName.getText().toString().matches(emailPattern)) {
                        isUserValid = true;
                        loginInput.setErrorEnabled(false);
                    } else {
                        isUserValid = false;

                        loginInput.setError("Enter Valid Email");
                        loginInput.setErrorEnabled(true);
                    }
                } else if (editable.hashCode() == password.getText().hashCode()) {
                    if (enteredText.length() >= 6 && !TextUtils.isDigitsOnly(enteredText)) {
                        isPassValid = true;
//                    password.setError(false);
                    } else {
                        isPassValid = false;

                        password.setError("Enter password");
//                    password.setErrorEnabled(true);
                    }

                }
            }
            validateFields();


        }

        private void validateFields () {
            if (isUserValid && isPassValid) {
                UIHelper.enableElement(loginBtn);
            } else {
                UIHelper.disableElement(loginBtn);
            }
        }




    private void handleFacebookToken (AccessToken token){

        final KProgressHUD kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
                .show();


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    kProgressHUD.dismiss();
                    FirebaseUser user = mAuth.getCurrentUser();
                    UpdateUI(user);



                } else {
                    kProgressHUD.dismiss();

                    Toast.makeText(Login.this, "Authentication Fail", Toast.LENGTH_SHORT).show();
//                    UpdateUI(null);
                }
            }
        });


    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


    }

    private void UpdateUI (FirebaseUser user) {

        progressBar.setVisibility(ProgressBar.GONE);
        Uri x = user.getPhotoUrl();

        if (x == null) {
            String photoUrl = user.getPhotoUrl().toString();
            photoUrl = photoUrl + "?type=large";
            Picasso.get().load(photoUrl).into(mLogo);
//            isLoggedIn();
//            saveLoggedIn(true);

            Intent homeIntent = new Intent(Login.this, Home.class);
            startActivity(homeIntent);


        } else {
            mLogo.setImageResource(R.drawable.ic_launcher_foreground);

            Intent homeIntent = new Intent(Login.this, Home.class);
            startActivity(homeIntent);


        }
    }

//    private boolean isLoggedIn() {
//        SharedPreferences sharedPreferences = PreferenceManager
//                .getDefaultSharedPreferences(this);
//        //The false represents the default value, if the variable is not stored
//        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
//        return isLoggedIn;
//    }
//
//    private void saveLoggedIn(boolean value) {
//        SharedPreferences sharedPreferences = PreferenceManager
//                .getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("isLoggedIn", value);
//        editor.commit();
//    }

//    @Override
//    protected void onStart () {
//        super.onStart();
//        mAuth.addAuthStateListener(authStateListener);
//    }
//
//    @Override
//    protected void onStop () {
//        super.onStop();
//
//        if (authStateListener != null) {
//            mAuth.removeAuthStateListener(authStateListener);
//        }
//    }
}

