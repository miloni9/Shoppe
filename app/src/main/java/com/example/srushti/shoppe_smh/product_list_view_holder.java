package com.example.srushti.shoppe_smh;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by srushti on 24-10-2015.
 */
public class product_list_view_holder {
    ImageView img1;
    TextView txtname,txtprice;

    public product_list_view_holder(ImageView img1, TextView txtname, TextView txtprice) {
        this.img1 = img1;
        this.txtname = txtname;
        this.txtprice = txtprice;
    }

    public ImageView getImg1() {
        return img1;
    }

    public void setImg1(ImageView img1) {
        this.img1 = img1;
    }

    public TextView getTxtname() {
        return txtname;
    }

    public void setTxtname(TextView txtname) {
        this.txtname = txtname;
    }

    public TextView getTxtprice() {
        return txtprice;
    }

    public void setTxtprice(TextView txtprice) {
        this.txtprice = txtprice;
    }
}
