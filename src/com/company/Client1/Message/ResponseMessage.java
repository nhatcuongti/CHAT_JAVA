package com.company.Client1.Message;


import com.company.Client1.model.User;
import java.io.Serializable;
import java.util.ArrayList;

public class ResponseMessage implements Serializable {
    String type;
    boolean status; // Set if type = "Login" or "Register"
    ArrayList<User> listUserOnline = new ArrayList<>();
    String Message;
    User fromUser;

    public ResponseMessage() {
        fromUser = new User();
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "type='" + type + '\'' +
                ", status=" + status +
                ", listUserOnline=" + listUserOnline +
                ", Message='" + Message + '\'' +
                ", fromUser=" + fromUser +
                '}';
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

    public ArrayList<User> getListUserOnline() {
        return listUserOnline;
    }

    public void setListUserOnline(ArrayList<User> listUserOnline) {
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
