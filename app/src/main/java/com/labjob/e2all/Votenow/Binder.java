package com.labjob.e2all.Votenow;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Binder extends Service {
    private int count;
    private boolean quit;
    private MyBinder binder = new MyBinder();

    // My Binder
    public class MyBinder extends Binder {
        public int getCount() {
            // get the counting status：count
            return count;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("Service is Binded");
        // return the binder instance
        return (IBinder) binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service is Created");
        // counting work
        new Thread() {
            @Override
            public void run() {
                while (!quit) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    count++;
                }
            }
        }.start();
    }

    // invoke when the service unbind
    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("Service is Unbinded");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit = true;
        System.out.println("Service is Destroyed");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        this.quit = true;
        System.out.println("Service is ReBinded");
    }
}