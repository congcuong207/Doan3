package com.example.doan3;

import static com.example.doan3.Introl.information;

import com.example.doan3.Empty.Account;

import java.util.ArrayList;

public class AccountCommon {
    public static String SDT = "";

    public static int Login(ArrayList<Account>accounts, String id, String passwood) {

        int result = -1;
        for (Account account :accounts
        ) {

            if (account.getTaikhoan().equalsIgnoreCase(id) && account.getMatkhau().equalsIgnoreCase(passwood) ) {

                SDT = "+84"+account.getSdt();
                information = account;
                result = 0;
            }
            if(account.getTaikhoan().equalsIgnoreCase(id.trim()) && !account.getMatkhau().equalsIgnoreCase(passwood)){
                result = 2;
            }
        }
        return result;
    }

}
