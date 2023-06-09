package com.saint.struct.ipc.binder;

import android.app.Person;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class ServerService extends Service {
    private static final String TAG = "ServerService";
    private List<Person> mPeople = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mPeople.add(new Person.Builder().build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private BinderObj mStub = new BinderObj() {
        @Override
        public void addPerson(Person p) {
            if(p == null){
                p = new Person.Builder().build();
            }
            mPeople.add(p);
        }


        @Override
        public List<Person> getPersonList() {
            return mPeople;
        }
    };
}
