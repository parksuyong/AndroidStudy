package com.study.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.d("service", "Service is running");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return mCalc;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d("service", "Service is destroying");
    }

    ICalc.Stub mCalc = new ICalc.Stub() {

        @Override
        public int Sub(int a, int b) throws RemoteException {
            // TODO Auto-generated method stub
            Log.d("service", "Sub was called!!");
            return a-b;
        }

        @Override
        public int Mul(int a, int b) throws RemoteException {
            // TODO Auto-generated method stub
            Log.d("service", "Mul was called!!");
            return a*b;
        }

        @Override
        public int Div(int a, int b) throws RemoteException {
            // TODO Auto-generated method stub
            Log.d("service", "Div was called!!");
            if(b == 0)
                return 0;

            return a/b;
        }

        @Override
        public int Add(int a, int b) throws RemoteException {
            // TODO Auto-generated method stub
            Log.d("service", "Add was called!!");
            return a+b;
        }
    };
}
