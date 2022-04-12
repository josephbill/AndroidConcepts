package com.example.androidjoseph;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.androidjoseph.databinding.ActivityAuthenticationScreenBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AuthenticationScreen extends AppCompatActivity {
    private ActivityAuthenticationScreenBinding binding;
    String email,pass;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthenticationScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting the user input
                email = binding.textEmail.getText().toString().trim();
                pass = binding.textPassword.getText().toString().trim();
                Toast.makeText(AuthenticationScreen.this, email + " " + pass, Toast.LENGTH_SHORT).show();
                //data validation
                if (email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(AuthenticationScreen.this, "Field(s) cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email,pass);
                }
            }
        });

    }

    private void loginUser(String email, String pass) {
        //giving user progress
        SweetAlertDialog progressDialog = new SweetAlertDialog(AuthenticationScreen.this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.setTitleText("Signing in.. Please wait");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    SweetAlertDialog successDialog = new SweetAlertDialog(AuthenticationScreen.this, SweetAlertDialog.SUCCESS_TYPE);
                    successDialog.setTitleText("Account Verified" + task.isSuccessful());
                    successDialog.setCancelable(true);
                    successDialog.show();
                    updateUi();
                } else if (!task.isSuccessful()) {
                    progressDialog.dismiss();
                    SweetAlertDialog errorDialog = new SweetAlertDialog(AuthenticationScreen.this, SweetAlertDialog.ERROR_TYPE);
                    errorDialog.setTitleText("Error in signing in");
                    errorDialog.setCancelable(true);
                    errorDialog.setCanceledOnTouchOutside(false);
                    errorDialog.show();
                }
            }
        });


    }





    private void updateUi() {
        Intent intent = new Intent(AuthenticationScreen.this,PostJob.class);
        startActivity(intent);
    }
}