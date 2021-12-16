package com.company.Server.model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String Username, Password;

    public User(String username, String password) {
        Username = username;
        Password = password;
    }

    public User() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
