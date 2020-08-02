package com.zohar.nisayon;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved=null;
    ArrayList<String> companies=new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }
    public Boolean save(Company company )
    {
        if(company==null)
        {
            saved=false;

        }else
        {
            try{
                db.child("Company").push().setValue(company);
                saved=true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;

            }
        }
        return saved;

    }
    public ArrayList<String> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return companies;
    }
    private void fetchData ( DataSnapshot dataSnapshot)
    {
        for(DataSnapshot ds:dataSnapshot.getChildren())
        {
            String name=ds.getValue(Company.class).getCompanyName();
            companies.add(name);
        }
    }
}
