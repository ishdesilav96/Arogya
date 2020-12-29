package com.example.arogya.uis;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arogya.R;
import com.example.arogya.helpers.UIHelper;
import com.example.arogya.manager.NetworkManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;

//import static com.example.firebasesample.helpers.UIHelper.setupUI;

public class Registration extends AppCompatActivity implements TextWatcher {

    private EditText loginId,password;
    private TextInputLayout loginTxtInput,passTxtInput;
    private Button registerBtn;
    private TextView loginback;
    private FirebaseAuth mAuth;
    private  boolean isLoginValid,isPassValid,isCnfPassValid;
    private ProgressBar progressBar;


    private MediaPlayer mPlayer;
    private List<String> mQueuedFiles;
    String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        UIHelper.setupUI(findViewById(R.id.registraionLayout),this);


        loginTxtInput= findViewById(R.id.loginIdTextInputLayout);
        passTxtInput= findViewById(R.id.passwordTextInputLayout);

        loginId = loginTxtInput.getEditText();
        password = passTxtInput.getEditText();

        registerBtn = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.webViewProgressBar);

        mAuth = FirebaseAuth.getInstance();
        loginId.addTextChangedListener(this);
        password.addTextChangedListener(this);
//        UIHelper.disableElement(registerBtn);

        final KProgressHUD kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);


        registerBtn.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {


                final String loginIDSrt = loginId.getText().toString().trim();
                final String passwordStr = password.getText().toString().trim();

                if (loginIDSrt.isEmpty() || passwordStr.isEmpty()) {
                    Toast.makeText(Registration.this, "Enter Login ID and Password to Register", Toast.LENGTH_SHORT).show();
                } else {

                    kProgressHUD.show();


                    if (NetworkManager.isAvailable(Registration.this)) {


                        mAuth.createUserWithEmailAndPassword(loginIDSrt, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    kProgressHUD.dismiss();
                                    Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    Intent homeIntent = new Intent(Registration.this, Login.class);
                                    startActivity(homeIntent);

                                    kProgressHUD.dismiss();
                                } else {

                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        kProgressHUD.dismiss();
                                        Toast.makeText(Registration.this, "You are already Registered", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG);
                                    }
//                                Toast.makeText(Registration.this, "Some Error occurred", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        kProgressHUD.dismiss();
                        Toast.makeText(Registration.this, "No Internet Connection ", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }

    public void onStart() {
        super.onStart();
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                System.out.println("OnCompletion called");
//                playNextFile();
            }
        });
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                System.out.println("MediaPlayer error: " + what + " " + extra);
                mPlayer.release();
                mPlayer = new MediaPlayer();
                return false;
            }
        });
        
    }




    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (editable.hashCode() != 0 && editable.length() > 1) {
            String enteredText = editable.toString().trim().toLowerCase();
            if (editable.hashCode() == loginId.getText().hashCode()) {
                if (enteredText.length() >= 4 && !TextUtils.isDigitsOnly(enteredText) && loginId.getText().toString().matches(emailPattern)) {
                    isLoginValid = true;
                    loginTxtInput.setErrorEnabled(false);
                }else {
                    isLoginValid = false;

                    loginTxtInput.setError("Enter Valid Email");
                    loginTxtInput.setErrorEnabled(true);
            }
            }else if (editable.hashCode() == password.getText().hashCode()) {
                if (enteredText.length() >= 6 && !TextUtils.isDigitsOnly(enteredText)) {
                    UIHelper.enableElement(registerBtn);
                    isPassValid = true;
                    passTxtInput.setErrorEnabled(false);
                  //  validateFields();
                }else {
                    isLoginValid = false;

                    passTxtInput.setError("Enter password");
                    passTxtInput.setErrorEnabled(true);
                }

            }
          //  validateFields();
            }
       // validateFields();

        }


//    private void validateFields() {
//        if (isLoginValid && isPassValid  ) {
//            UIHelper.enableElement(registerBtn);
//        } else {
//            UIHelper.disableElement(registerBtn);
//        }
//    }


}