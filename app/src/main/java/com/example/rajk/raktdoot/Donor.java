package com.example.rajk.raktdoot;

/**
 * Created by RajK on 27-01-2017.
 */
public class Donor {
    private String Name;
    private String Email;
    private String Gender;
    private String Contact;
    private String City;
    private String State;
    private String Diseases;


    private String Age;
    private String BloodGroup;
    private String UId;

    public String getEmail() {
        return Email;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public String getGender() {
        return Gender;
    }

    public String getCity() {
        return City;
    }

    public String getDiseases() {
        return Diseases;
    }

    public String getContact() {
        return Contact;
    }

    public String getName() {
        return Name;
    }

    public String getState() {
        return State;
    }

    public String getUId()
    {
        return UId;
    }

    public Donor(String Name, String Email, String Gender, String Contact, String City, String State, String Diseases, String BloodGroup, String UId,String Age) {
        this.Name=Name;
        this.Email=Email;
        this.Gender = Gender;
        this.Contact = Contact;
        this.City = City;
        this.State = State;
        this.Diseases = Diseases;
        this.BloodGroup = BloodGroup;
        this.UId=UId;
        this.Age = Age;
    }
    public Donor()
    {}

}
