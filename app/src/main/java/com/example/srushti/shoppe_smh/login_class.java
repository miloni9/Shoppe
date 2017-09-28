package com.example.srushti.shoppe_smh;

/**
 * Created by srushti on 24-10-2015.
 */
public class login_class {
    String mobile_no,name;

    public login_class(String mobile_no, String name) {
        this.mobile_no = mobile_no;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
