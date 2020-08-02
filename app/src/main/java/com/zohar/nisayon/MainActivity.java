package com.zohar.nisayon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogIn, btnAboutUs, btnSignUp;
    EditText etUserName, etPassword;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = mDatabase.getReference("users/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnAboutUs = findViewById(R.id.btnAboutUs);
        btnSignUp = findViewById(R.id.btnSignUp);
        etPassword = findViewById(R.id.etPassword);
        etUserName = findViewById(R.id.etUserName);
        btnSignUp.setOnClickListener(this);
        btnAboutUs.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btnAboutUs) {
        } else if (v == btnLogIn) {
            String name = etUserName.getText().toString();
            String pass = etPassword.getText().toString();

            if (name.length() == 0 || pass.length() == 0) {
                Toast.makeText(this, "Name field must be filled", Toast.LENGTH_LONG).show();
            } else {
                loginUser(name, pass);
            }
        } else if (v == btnSignUp) {
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);

        }


    }

    public void loginUser(final String username, final String password) {
        dbRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String passwordDB = "";
                    String isCompany = "";
                    if (dataSnapshot.exists()) {
                        passwordDB = (String) dataSnapshot.child("password").getValue();
                        isCompany = (String) dataSnapshot.child("isCompany").getValue();
                        if (password.equals(passwordDB)) {
                            if (isCompany.equals("true")) {
                                //move to company activity
                                Toast.makeText(getApplicationContext(), "company activity", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, Settings.class);
                                intent.putExtra("tvCompany", username);
                                startActivity(intent);
                            } else {
                                //move to worker activity
                                Toast.makeText(getApplicationContext(), "worker activity", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "the password is wrong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "the user not exist", Toast.LENGTH_SHORT).show();
                    }
                } catch (DatabaseException e) {
                    System.out.println(e);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

