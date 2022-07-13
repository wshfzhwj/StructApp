package com.saint.struct.practice;


import android.app.Person;
import android.os.IInterface;

import java.util.List;

public interface PersonManager extends IInterface {
    void addPerson(Person p);
    List<Person> getPersonList();
}
