package com.company.Server.Message;


import com.company.Server.model.ClientSocket_ClientSide;
import com.company.Server.model.User;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseMessage implements Serializable {
    String type;
    boolean status; // Set if type = "Login" or "Register"
    ArrayList<ClientSocket_ClientSide> listUserOnline = new ArrayList<ClientSocket_ClientSide>();
    String Message;
    User fromUser;
    ClientSocket_ClientSide toUser;
    ClientSocket_ClientSide newUser;

    public ClientSocket_ClientSide getNewUser() {
        return newUser;
    }

    public void setNewUser(ClientSocket_ClientSide newUser) {
        this.newUser = newUser;
    }

    public ClientSocket_ClientSide getToUser() {
        return toUser;
    }

    public void setToUser(ClientSocket_ClientSide toUser) {
        this.toUser = toUser;
    }

    public ResponseMessage() {
        fromUser = new User();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<ClientSocket_ClientSide> getListUserOnline() {
        return listUserOnline;
    }

    public void setListUserOnline(ArrayList<ClientSocket_ClientSide> listUserOnline) {
        this.listUserOnline = listUserOnline;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }
}
