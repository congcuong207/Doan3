package com.example.doan3;

import android.util.Log;


import com.example.doan3.Empty.Account;

import java.util.ArrayList;
import java.util.List;


public class ValidateData {
    public static boolean checkEmpty(String content){
        if(content.equals("")) return false;
        return true;
    }
    public static boolean checkUniqueID(String id,List<Account>accountList)
    {
        for (Account acc:accountList
        ) {
            if(acc.getTaikhoan().equalsIgnoreCase(id))
            {
                return false;
            }
        }
        return true;
    }

}
