package com.example.srushti.shoppe_smh;

import android.app.Application;

/**
 * Created by srushti on 05-11-2015.
 */
public class global_class extends Application {
    private String email;

    public global_class(String email) {
        this.email = email;
    }

    public global_class() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
