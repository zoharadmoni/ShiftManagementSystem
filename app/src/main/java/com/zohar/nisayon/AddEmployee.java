package com.zohar.nisayon;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class AddEmployee extends AppCompatActivity {

    EditText etFirsName, etLastName, etPassword, etEmail, etPhone;
    Button btnNex;
    TextView tvTitle;
    ListView myListView;
    ArrayList<String> myArrayList = new ArrayList<>();
    DatabaseReference mRef, rootRef, workersRef, companyRef;
    FirebaseAuth fAuth;
    String userID, companyID;
    long id;
    int flag = 0;
    String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        tvTitle = findViewById(R.id.tvTitle);
        etFirsName = findViewById(R.id.etFirstName);
        // etBirthDate = findViewById(R.id.etBirthDate);
        etLastName = findViewById(R.id.etLastName);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        btnNex = findViewById(R.id.btnNex);
        myListView = (ListView) findViewById(R.id.listView1);
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(AddEmployee.this, android.R.layout.simple_list_item_1, myArrayList);
        myListView.setAdapter(myArrayAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                tvTitle.setText(parent.getItemAtPosition(position).toString());
            }
        });
        rootRef = FirebaseDatabase.getInstance().getReference();
        companyRef = rootRef.child("Companies");

        companyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    myArrayList.add(snapshot.child("companyName").getValue().toString());
                }
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        fAuth = FirebaseAuth.getInstance();

        btnNex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEmployee();
            }
        });
        path=companyRef.toString();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fAuth.getCurrentUser() != null) {

        }
    }


    public void registerEmployee() {
        final String eName = etFirsName.getText().toString();
        final String eLastName = etLastName.getText().toString();
        //final Date eBirthDate= (Date) etBirthDate.getText().getDate();
        final String eEmail = etEmail.getText().toString();
        final String ePassword = etPassword.getText().toString();
        final String ePhone = etPhone.getText().toString();
        final String eCompany = tvTitle.getText().toString();
        boolean c = checkFields(eName, eLastName, ePhone, eEmail, ePassword, eCompany);
        if (!c) {
            Toast.makeText(AddEmployee.this, "Registration failed", Toast.LENGTH_LONG).show();
            return;
        } else {
            DatabaseReference ref = rootRef.child("Companies/" + eCompany + "/Workers/" + eLastName);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        User user = new User(eName, eLastName, ePhone, eEmail, ePassword, eCompany);
                        companyRef.child(eCompany).child("Workers").child(eLastName).setValue(user);
                        profile(eName,eLastName,eCompany);
                    }
                    else {
                        Toast.makeText(AddEmployee.this, "username exist", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }


    public boolean checkFields(String firstName, String lastName, String phone, String email, String pass, String companyName) {
        if (firstName.isEmpty()) {
            Toast.makeText(AddEmployee.this, "Name required", Toast.LENGTH_LONG).show();
            etFirsName.setText(" ");
            etFirsName.requestFocus();
            return false;
        }
        if (email.isEmpty()) {
            Toast.makeText(AddEmployee.this, "Email required", Toast.LENGTH_LONG).show();
            etEmail.setText(" ");
            etEmail.requestFocus();
            return false;
        }
        if (lastName.isEmpty()) {
            Toast.makeText(AddEmployee.this, "Last name required", Toast.LENGTH_LONG).show();
            etLastName.setText(" ");
            etLastName.requestFocus();
            return false;
        }
        if (phone.isEmpty()) {
            Toast.makeText(AddEmployee.this, "Phone required", Toast.LENGTH_LONG).show();
            etPhone.setText(" ");
            etPhone.requestFocus();
            return false;
        }
        if (companyName.isEmpty()) {
            Toast.makeText(AddEmployee.this, "Company name required", Toast.LENGTH_LONG).show();
            tvTitle.setText(" ");
            tvTitle.requestFocus();
            return false;
        }
        if (pass.isEmpty()) {
            Toast.makeText(AddEmployee.this, "Password required", Toast.LENGTH_LONG).show();
            etPassword.setText(" ");
            etPassword.requestFocus();
            return false;
        }
      /*  if (eBirthDate)
        {
            etBirthDate.setError("Password required");
            etBirthDate.requestFocus();
            return false;
        }*/
        return true;
    }
    public void profile(String eName,String eUserName,String eCompany)
    {
        String s=eName+"\n"+"User name:"+eUserName+"\n"+"Company:"+eCompany+"\n";
        Intent intent = new Intent(AddEmployee.this, Profile.class);
        intent.putExtra("tvTitle3",eUserName);
        intent.putExtra("tvTitle2", s);
        startActivity(intent);

    }
}
