package com.zohar.nisayon;

import android.provider.ContactsContract;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
public class Company {
    private String companyName,companyPassword,companyEmail;
    private User manager;
    private ArrayList<User>workers;
    public Company(String companyName,String email, String pass)
    {
        this.companyEmail=email;
        this.companyPassword=pass;
        this.companyName=companyName;
        this.manager=new User( email, pass, companyName);
    }

    public String getCompanyPassword() {
        return companyPassword;
    }

    public void setCompanyPassword(String companyPassword) {
        this.companyPassword = companyPassword;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public void setCompanyName(String companyName)
 {
     this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
    public void updateList(User user)
    {
        workers.add(user);
    }
}
