package com.example.emmanuel.pipochat;

import android.app.Application;

/**
 * Created by Emilio on 6/5/2015.
 */
public class MyApplication extends Application {

    private String someVariable;

    public String getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(String someVariable) {
        this.someVariable = someVariable;
    }
}
