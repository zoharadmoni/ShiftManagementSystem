package com.zohar.nisayon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
private Button btEmployee,btCompany;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btCompany=findViewById(R.id.btCompany);
        btEmployee=findViewById(R.id.btEmployee);
        btCompany.setOnClickListener(this);
        btEmployee.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==btCompany)
        {
            Intent intent =new Intent(SignUp.this,AddCompany.class);
            startActivity(intent);
        }
        else if(v==btEmployee)
        {
            Intent intent =new Intent(SignUp.this,AddEmployee.class);
            startActivity(intent);
        }

    }
}
