package com.company.Server;

import com.company.Server.model.User;
import com.company.Server.utils.ManageUser;

public class Main_Test {
    public static void main(String[] args) {
        ManageUser manageUser = new ManageUser();
        System.out.println(manageUser.loginData(new User("nhathao123", "090984528")));
    }
}
