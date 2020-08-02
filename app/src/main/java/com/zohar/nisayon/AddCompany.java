package com.zohar.nisayon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AndroidException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class AddCompany extends AppCompatActivity  {

    EditText etCompanyName, etCompEmail, etCompPassword;
    DatabaseReference myDatabase;
    Button btnNext;
    String path;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        etCompanyName = findViewById(R.id.etCompanyName);
        btnNext = findViewById(R.id.btnNext);
        etCompPassword = findViewById(R.id.etCompanyPassword);
        etCompEmail = findViewById(R.id.etCompEmail);
        mAuth = FirebaseAuth.getInstance();

        myDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {

        }
    }

    public void settings()
    {
        String s=etCompanyName.getText().toString();
        Intent intent = new Intent(AddCompany.this, Settings.class);
        intent.putExtra("tvCompany", path);
        startActivity(intent);

    }
    public void registerCompany(View view)
    {

        final String cName=etCompanyName.getText().toString();
        final String cPass=etCompPassword.getText().toString();
        final String cEmail=etCompEmail.getText().toString();
        if (cName.isEmpty())
        {
            etCompanyName.setError("Company name required");
            etCompanyName.requestFocus();
            return;
        }
        if (cEmail.isEmpty())
        {
            etCompEmail.setError("Company Email required");
            etCompEmail.requestFocus();
            return;
        }
        if (cPass.isEmpty())
        {
            etCompPassword.setError("Password required");
            etCompPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(cEmail,cPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Company company = new Company(cName, cEmail, cPass);
                    myDatabase.child("Companies").child(cName).setValue(company).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(AddCompany.this, "Regisration success", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddCompany.this, "Regisration failed", Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }
        });
        path=myDatabase.child("Companies").child(cName).toString();

        settings();
    }


}
