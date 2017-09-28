package com.example.srushti.shoppe_smh;

/**
 * Created by srushti on 03-11-2015.
 */
public class chat_class {

    int pk_msg_id;
    String sender, reciever, message, msg_date;
    int fk_pro_id;


    public chat_class(int pk_msg_id, String sender, String reciever, String message, String msg_date, int fk_pro_id) {
        this.pk_msg_id = pk_msg_id;
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.msg_date = msg_date;
        this.fk_pro_id = fk_pro_id;
    }

    public chat_class(String sender, String reciever, String message) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
    }

    public int getPk_msg_id() {
        return pk_msg_id;
    }

    public void setPk_msg_id(int pk_msg_id) {
        this.pk_msg_id = pk_msg_id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsg_date() {
        return msg_date;
    }

    public void setMsg_date(String msg_date) {
        this.msg_date = msg_date;
    }

    public int getFk_pro_id() {
        return fk_pro_id;
    }

    public void setFk_pro_id(int fk_pro_id) {
        this.fk_pro_id = fk_pro_id;
    }
}
