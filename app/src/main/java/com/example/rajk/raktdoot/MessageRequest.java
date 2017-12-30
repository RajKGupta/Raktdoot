package com.example.rajk.raktdoot;

import android.provider.MediaStore;

import java.util.Date;

/**
 * Created by RajK on 24-01-2017.
 */
public class MessageRequest {
    String message;
    String date;
    String sender;
    String contact;

    public MessageRequest()
    {

    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }

    public String getContact() {return contact;    }


    public MessageRequest(String message, String date, String sender,String contact )
    {
        this.message=message;
        this.date=date;
        this.sender=sender;
        this.contact = contact;
    }
}
