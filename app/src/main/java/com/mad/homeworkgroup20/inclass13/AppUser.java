package com.mad.homeworkgroup20.inclass13;

import java.io.Serializable;

/**
 * Created by Aliandro on 4/23/2018.
 */

public class AppUser implements Serializable {
    String UserID;
    String UserName;
    String Email;


    public AppUser() {

    }

    public AppUser(String userID, String userName, String email) {
        UserID = userID;
        UserName = userName;
        Email = email;
    }
}
