package com.company.Client1.Message;


import com.company.Client1.model.ClientSocket;
import com.company.Client1.model.User;
import java.io.Serializable;
import java.util.ArrayList;

public class ResponseMessage implements Serializable {
    String type;
    boolean status; // Set if type = "Login" or "Register"
    ArrayList<ClientSocket> listUserOnline = new ArrayList<>();
    String Message;
    ClientSocket fromUser;
    ClientSocket toUser;
    ClientSocket deleteUser;

    public ClientSocket getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(ClientSocket deleteUser) {
        this.deleteUser = deleteUser;
    }

    public ClientSocket getToUser() {
        return toUser;
    }

    public void setToUser(ClientSocket toUser) {
        this.toUser = toUser;
    }

    ClientSocket newUser;

    public ClientSocket getNewUser() {
        return newUser;
    }

    public void setNewUser(ClientSocket newUser) {
        this.newUser = newUser;
    }

    public ClientSocket getFromUser() {
        return fromUser;
    }

    public void setFromUser(ClientSocket fromUser) {
        this.fromUser = fromUser;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "type='" + type + '\'' +
                ", status=" + status +
                ", listUserOnline=" + listUserOnline +
                ", Message='" + Message + '\'' +
                ", fromUser=" + fromUser +
                ", toUser=" + toUser +
                ", newUser=" + newUser +
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

    public ArrayList<ClientSocket> getListUserOnline() {
        return listUserOnline;
    }

    public void setListUserOnline(ArrayList<ClientSocket> listUserOnline) {
        this.listUserOnline = listUserOnline;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


}
