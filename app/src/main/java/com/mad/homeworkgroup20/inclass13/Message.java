package com.mad.homeworkgroup20.inclass13;

import java.io.Serializable;

/**
 * Created by Aliandro on 4/23/2018.
 */

public class Message implements Serializable{
    String Text;
    String Date;
    String From;
    String SenderUID;
    String SenderName;
    String SemderEmail;
    Boolean IsRead;
    public String Id;
public Message(){};
    public Message(String text, String date, String from, String senderUID, String semderEmail,String SenderName, Boolean isRead) {
        Text = text;
        Date = date;
        From = from;
        SenderUID = senderUID;
        SemderEmail = semderEmail;
        IsRead = isRead;
        this.SenderName=SenderName;
    }
}
