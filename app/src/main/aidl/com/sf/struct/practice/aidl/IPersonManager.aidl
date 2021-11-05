// IPersonManager.aidl
package com.sf.struct.practice.aidl;
import com.sf.struct.practice.aidl.Person;
// Declare any non-default types here with import statements

interface IPersonManager {
     List<Person> getPersonList();
     //in：from client to service
     boolean addPerson(in Person person);
}