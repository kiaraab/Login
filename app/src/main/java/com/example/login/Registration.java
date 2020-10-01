package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    private EditText userName, userPasswd, userEmail, passwdCheck;
    private Button signUp;
    private TextView backtoLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // setting up the UI views
        userName = (EditText)findViewById(R.id.usrName);
        userPasswd = (EditText)findViewById(R.id.usrPasswd);
        userEmail = (EditText)findViewById(R.id.email);
        passwdCheck = (EditText)findViewById(R.id.usrPasswdCheck);
        signUp = (Button)findViewById(R.id.btnSignUp);
        backtoLogin = (TextView)findViewById(R.id.tvBacktoLogin);

        firebaseAuth = FirebaseAuth.getInstance();


        // setting onclick for th sign up button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Registration.this, "coming inside submit", Toast.LENGTH_SHORT).show();
                if(checkValidity()){

                    // uploading to the database
                    String usrEmail = userEmail.getText().toString().trim();
                    String usrPasswd = userPasswd.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(usrEmail,usrPasswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendVerification();
                            }
                            else{
                                FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(Registration.this, "Sign up failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        backtoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this,MainActivity.class));
            }
        });


    }
    private Boolean checkValidity(){
        Boolean result = false;
        Toast.makeText(this, "coming inside checkValidity", Toast.LENGTH_SHORT).show();
        String name = userName.getText().toString();
        String password = userPasswd.getText().toString();
        String checkPassword = passwdCheck.getText().toString();
        String email  = userEmail.getText().toString();
        Toast.makeText(this, checkPassword + password, Toast.LENGTH_SHORT).show();
        if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else if(!(checkPassword.equals(password)) ) {
            Toast.makeText(this, "Re-entered Password Doesn't match", Toast.LENGTH_SHORT).show();
        }
        else{
           result = true;
        }
        return result;

    }
    private void sendVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Registration.this,"Successfully registered, Verification email sent. Verify your account please!",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(Registration.this, MainActivity.class));
                    }
                    else{
                        Toast.makeText(Registration.this,"Verification mail hasn't been sent", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }


    

}