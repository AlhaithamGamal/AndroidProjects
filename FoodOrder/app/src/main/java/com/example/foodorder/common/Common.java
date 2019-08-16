package com.example.foodorder.common;

import com.example.foodorder.models.User;

public class Common {
    public static User currentUser;
    public  static String convertToStatus(String status) {
        if(status.equals("0")){

            return "Placed";
        }
        else
        if(status.equals("1")){
            return "On my way";
        }

        else
            return "shipped";

    }
}
