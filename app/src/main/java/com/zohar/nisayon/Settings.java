package com.zohar.nisayon;

import com.zohar.nisayon.listViewShift.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//need to add condition on the radio button for get the specific day and do a root for the DB

public class Settings extends AppCompatActivity implements View.OnClickListener {
    TextView tvCompany;
    RadioButton sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    LinearLayout table_shifts;
    DatabaseReference rootRef, companyRef;
    Button btnAddHours;
    String start,finish,nWorkers;
    ListView mFirebaseList;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dbShiftRef = mDatabase.getReference("days");
    DatabaseReference dbref = mDatabase.getReference();
    ListViewItemShiftAdapter listViewItemShiftAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        tvCompany = findViewById(R.id.tvCompany);
        sunday = findViewById(R.id.sunday);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        //wednesday, thursday, friday, saturday;
        table_shifts = findViewById(R.id.table_layout);
        mFirebaseList = findViewById(R.id.shift_list);

        sunday.setOnClickListener(this);
        rootRef = FirebaseDatabase.getInstance().getReference();
        companyRef = rootRef.child("Companies");
        Intent i = getIntent();

        String path = i.getExtras().getString("tvCompany");
        companyRef = FirebaseDatabase.getInstance().getReference("Companies/" + path);
        table_shifts.setVisibility(View.GONE);

        final List<ListViewItemShift> initItemList=new ArrayList<ListViewItemShift>();
        dbShiftRef.child("sunday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                initItemList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Shift shift = snapshot.getValue(Shift.class);
                    ListViewItemShift shiftItem = new ListViewItemShift();
                    shiftItem.setStartShift(shift.getStart());
                    shiftItem.setFinishShift(shift.getEnd());
                    shiftItem.setEmployeeAmount(shift.getCount());
                    shiftItem.setKey(snapshot.getKey());
                    initItemList.add(shiftItem);
                }
                //listViewItemShiftAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listViewItemShiftAdapter = new ListViewItemShiftAdapter(getApplicationContext(),initItemList);

        //listViewItemShiftAdapter.notifyDataSetChanged();

        mFirebaseList.setAdapter(listViewItemShiftAdapter);

//        mFirebaseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });

    }

    public void dialog1(final String day) {
        final EditText etTemp = new EditText(this);
        final EditText etTemp2 = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Settings")
                .setMessage("Insert hours for shift:")
                .setView(etTemp)
                .setView(etTemp2)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String hours = etTemp.getText().toString();
                        AlertDialog dialog2 = new AlertDialog.Builder(Settings.this)
                                .setTitle("Settings")
                                .setMessage("Insert number of workers:")
                                .setView(etTemp2)
                                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String workers = etTemp2.getText().toString();
                                        companyRef.child("WorkDays").child(day).child(hours).setValue(workers);
                                    }
                                }).setNegativeButton("Cancel", null)
                                .create();
                        dialog2.show();

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void init(final String day) {

        btnAddHours = findViewById(R.id.btnAddHours);
//        TableLayout stk = (TableLayout) findViewById(R.id.table_shifts);
//        TableRow tbrow0 = new TableRow(this);
//
//        TextView tv0 = new TextView(this);
//        tv0.setText(" Shift ");
//        tv0.setTextColor(Color.WHITE);
//        tbrow0.addView(tv0);
//
//        TextView tv1 = new TextView(this);
//        tv1.setText(" Start ");
//        tv1.setTextColor(Color.WHITE);
//        tbrow0.addView(tv1);
//
//        TextView tv2 = new TextView(this);
//        tv2.setText(" Finish ");
//        tv2.setTextColor(Color.WHITE);
//        tbrow0.addView(tv2);
//
//        TextView tv3 = new TextView(this);
//        tv3.setText(" Number of employees ");
//        tv3.setTextColor(Color.WHITE);
//        tbrow0.addView(tv3);
//
//        stk.addView(tbrow0);
        btnAddHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Settings.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
                final EditText startHour = (EditText) mView.findViewById(R.id.startHour);
                final EditText finishHour = (EditText) mView.findViewById(R.id.finishHour);
                final EditText workerCount = (EditText) mView.findViewById(R.id.numOfWorkers);

                Button mLogin = (Button) mView.findViewById(R.id.btnOk);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.setCancelable(false);
                dialog.show();
                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start=startHour.getText().toString();
                        finish=finishHour.getText().toString();
                        nWorkers=workerCount.getText().toString();
                        if (checkFields()) {
                            listViewItemShiftAdapter.notifyDataSetChanged();
                            Toast.makeText(Settings.this,
                                    "Added successfully",
                                    Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        final TableRow tbrow = new TableRow(this);
        final TextView t1v = new TextView(this);

        companyRef.child("WorkDays").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//              t1v.setText(dataSnapshot.child());
                t1v.setTextColor(Color.WHITE);
                t1v.setGravity(Gravity.CENTER);
                tbrow.addView(t1v);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void show_table(String day) {

    }

    @Override
    public void onClick(View v) {

        if (sunday.isChecked()) {

            init("Sunday");

            table_shifts.setVisibility(View.VISIBLE);
            mFirebaseList.setVisibility(View.VISIBLE);

        } else {
            table_shifts.setVisibility(View.GONE);
            mFirebaseList.setVisibility(View.GONE);
        }


    }
    public Boolean checkFields() {
        if (start.length() == 0 || finish.length() == 0 || nWorkers.length() == 0) {
            Toast.makeText(Settings.this, "Please fill in all the fields", Toast.LENGTH_LONG).show();
            return false;
        }
        try {
            int start1 = Integer.parseInt(start);
            int finish1 = Integer.parseInt(finish);
            int nWorker1 = Integer.parseInt(nWorkers);
            if (start1 > finish1 || start1 > 24 || finish1 > 24 || start1 < 0 || finish1 < 0) {
                Toast.makeText(Settings.this, "Enter reasonable hours.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(nWorker1<1)
            {
                Toast.makeText(Settings.this, "Enter reasonable workers.", Toast.LENGTH_SHORT).show();
                return false;

            }
            else
            {       return true;
            }

        } catch (Exception e) {

            Toast.makeText(Settings.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        return false;
    }
}
