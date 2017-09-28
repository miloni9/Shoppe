package com.example.srushti.shoppe_smh;

/**
 * Created by srushti on 24-10-2015.
 */
public class product_list_class {
    int pk_pro_id,pro_price,count,unique;
    String pro_category,pro_name,fk_email_id,description,pro_condition,pro_img1,pro_img2;

    public product_list_class(int pk_pro_id, int pro_price, int count, int unique, String pro_category, String pro_name, String fk_email_id, String description, String pro_condition, String pro_img1, String pro_img2) {

        this.pk_pro_id = pk_pro_id;
        this.pro_price = pro_price;
        this.count = count;
        this.unique = unique;
        this.pro_category = pro_category;
        this.pro_name = pro_name;
        this.fk_email_id = fk_email_id;
        this.description = description;
        this.pro_condition = pro_condition;
        this.pro_img1 = pro_img1;
       // this.pro_img1_temp = pro_img1_temp;
        this.pro_img2 = pro_img2;
        //this.pro_img2_temp = pro_img2_temp;
    }

    public product_list_class(int pk_pro_id, int pro_price, String pro_name, String pro_img1) {
        this.pk_pro_id = pk_pro_id;
        this.pro_price = pro_price;
        this.pro_name = pro_name;
        this.pro_img1 = pro_img1;
    }

    public int getPk_pro_id() {
        return pk_pro_id;
    }

    public void setPk_pro_id(int pk_pro_id) {
        this.pk_pro_id = pk_pro_id;
    }

    public int getPro_price() {
        return pro_price;
    }

    public void setPro_price(int pro_price) {
        this.pro_price = pro_price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getUnique() {
        return unique;
    }

    public void setUnique(int unique) {
        this.unique = unique;
    }

    public String getPro_category() {
        return pro_category;
    }

    public void setPro_category(String pro_category) {
        this.pro_category = pro_category;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getFk_email_id() {
        return fk_email_id;
    }

    public void setFk_email_id(String fk_email_id) {
        this.fk_email_id = fk_email_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPro_condition() {
        return pro_condition;
    }

    public void setPro_condition(String pro_condition) {
        this.pro_condition = pro_condition;
    }

    public String getPro_img1() {
        return pro_img1;
    }

    public void setPro_img1(String pro_img1) {
        this.pro_img1 = pro_img1;
    }


    public String getPro_img2() {
        return pro_img2;
    }

    public void setPro_img2(String pro_img2) {
        this.pro_img2 = pro_img2;
    }

}
