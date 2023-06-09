// IPersonManager.aidl
package com.saint.struct.ipc.aidl;
import com.saint.struct.ipc.aidl.Person;
// Declare any non-default types here with import statements

interface IPersonManager {
     List<Person> getPersonList();
     //in：from client to service
     boolean addPerson(in Person person);
}