package com.example.user.firebasedemo;

import android.app.Application;

/**
 * Created by User on 9/7/2017.
 */

public class FireApp  {

    String department,name,university,contact,accname,accontact,jscname,jsccontact;

    public FireApp()
    {

    }

    public FireApp(String department, String name, String university, String contact, String accname, String accontact, String jscname, String jsccontact) {
        this.department = department;
        this.name = name;
        this.university = university;
        this.contact = contact;
        this.accname = accname;
        this.accontact = accontact;
        this.jscname = jscname;
        this.jsccontact = jsccontact;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAccname() {
        return accname;
    }

    public void setAccname(String accname) {
        this.accname = accname;
    }

    public String getAccontact() {
        return accontact;
    }

    public void setAccontact(String accontact) {
        this.accontact = accontact;
    }

    public String getJscname() {
        return jscname;
    }

    public void setJscname(String jscname) {
        this.jscname = jscname;
    }

    public String getJsccontact() {
        return jsccontact;
    }

    public void setJsccontact(String jsccontact) {
        this.jsccontact = jsccontact;
    }
}

