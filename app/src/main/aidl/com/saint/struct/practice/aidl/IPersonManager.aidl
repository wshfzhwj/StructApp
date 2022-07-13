// IPersonManager.aidl
package com.saint.struct.practice.aidl;
import com.saint.struct.practice.aidl.Person;
// Declare any non-default types here with import statements

interface IPersonManager {
     List<Person> getPersonList();
     //in：from client to service
     boolean addPerson(in Person person);
}