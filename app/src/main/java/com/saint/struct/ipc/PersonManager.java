package com.saint.struct.ipc;


import android.os.IInterface;

import android.app.Person;

import java.util.List;

public interface PersonManager extends IInterface {
    void addPerson(Person p);
    List<Person> getPersonList();
}
