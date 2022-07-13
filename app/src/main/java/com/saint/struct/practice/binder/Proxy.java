package com.saint.struct.practice.binder;

import android.app.Person;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.saint.struct.practice.PersonManager;

import java.util.List;

public class Proxy implements PersonManager {
    private IBinder mIBinder;
    public Proxy(IBinder mIBinder){
        this.mIBinder = mIBinder;
    }

    @Override
    public void addPerson(Person p) {
        Parcel data = Parcel.obtain();
        Parcel relay = Parcel.obtain();

        try{
            data.writeInterfaceToken(BinderObj.DESCRIPTOR);
            if(p != null){
                data.writeInt(1);
                p.writeToParcel(data,0);
            }else{
                data.writeInt(0);
            }
            mIBinder.transact(BinderObj.TRANSAVTION_addPerson,data,relay,0);
            relay.readException();
        }catch (RemoteException e){
            e.printStackTrace();
        }finally {
            relay.recycle();
            data.recycle();
        }
    }

    @Override
    public List<Person> getPersonList() {
        Parcel data = Parcel.obtain();
        Parcel replay = Parcel.obtain();
        List<Person> result = null;
        try {
            data.writeInterfaceToken(BinderObj.DESCRIPTOR);
            mIBinder.transact(BinderObj.TRANSAVTION_getPerson, data, replay, 0);
            replay.readException();
            result = replay.createTypedArrayList(Person.CREATOR);
        }catch (RemoteException e){
            e.printStackTrace();
        } finally{
            replay.recycle();
            data.recycle();
        }
        return result;
    }

    @Override
    public IBinder asBinder() {
        return null;
    }
}
