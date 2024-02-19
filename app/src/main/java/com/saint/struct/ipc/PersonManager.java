package com.saint.struct.ipc;


import android.os.IInterface;

import com.saint.struct.ipc.aidl.Person;

import java.util.List;

public interface PersonManager extends IInterface {
    void addPerson(Person p);
    List<Person> getPersonList();
}
