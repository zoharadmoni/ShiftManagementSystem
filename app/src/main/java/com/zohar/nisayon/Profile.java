package com.zohar.nisayon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.core.view.View;

public class Profile extends AppCompatActivity{
    Button btAvailability,btSchedule;
    TextView tvTitle2;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btAvailability=findViewById(R.id.btAvailability);
        btSchedule=findViewById(R.id.btSchedule);
        tvTitle2=findViewById(R.id.tvTitle2);
        Intent i = getIntent();
        String s=i.getExtras().getString("tvTitle2");
        user=i.getExtras().getString("tvTitle3");
        tvTitle2.setText(s);


    }
    public void schedule(View view)
    {

    }
    public void availability(View view)
    {

    }
}
