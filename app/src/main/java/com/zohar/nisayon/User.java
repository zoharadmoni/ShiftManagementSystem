package com.zohar.nisayon;

public class User {
    private String firstName,lastName,pass;
  //  private Date birthdate;
    private String phone;
    private String email;
    private String companyName;
    private static long id;

    public User( String email,String pass,String companyName)
    {
        this.email=email;
        this.pass=pass;
        this.companyName=companyName;


    }

    public User( String firstName,String lastName,String phone,String email,String pass,String companyName)
    {
        //Name, eLastName, ePhone, eEmail, ePassword, eCompany);
    //    this.birthdate=birthdate;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.phone=phone;
        this.pass=pass;
        this.companyName=companyName;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPass() {
        return pass;
    }

   // public Date getBirthdate() {
    //    return birthdate;
    //}

    public String getPhone() {
        return phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEmail() {
        return email;
    }

    public static long getId() {
        return id;
    }

    public static void setId(long id) {
        User.id = id;
    }
}
