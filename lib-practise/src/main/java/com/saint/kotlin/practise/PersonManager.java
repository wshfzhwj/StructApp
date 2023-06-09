package com.saint.kotlin.practise;


import android.app.Person;
import android.os.IInterface;

import java.util.List;

public interface PersonManager extends IInterface {
    void addPerson(Person p);
    List<Person> getPersonList();
}
