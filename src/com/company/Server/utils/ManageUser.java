package com.company.Server.utils;

import com.company.Server.model.User;

import java.util.ArrayList;

public class ManageUser {
    private static final String fileName = "file.txt";
    public ManageUser() {

    }

    public boolean registerData(User user){
        FileManager fileManager = new FileManager(fileName);
        ArrayList<User> users = fileManager.getData();

        for (User userIndex : users)
            if (userIndex.getUsername().equals(user.getUsername()))
                return false;

        fileManager.writeData(user);

        return true;
    }

    public boolean loginData(User user){
        FileManager fileManager = new FileManager(fileName);
        ArrayList<User> users = fileManager.getData();

        for (User userIndex : users)
            if (userIndex.getUsername().equals(user.getUsername()) && userIndex.getPassword().equals(user.getPassword()))
                return true;

        return false;
    }
}
