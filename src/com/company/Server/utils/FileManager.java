package com.company.Server.utils;

import com.company.Server.model.User;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
    String fileName = "";
    ArrayList<User> listUsers = new ArrayList<User>();

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    public void writeData(User user){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
            bw.write(user.getUsername());
            bw.newLine();
            bw.write(user.getPassword());
            bw.newLine();
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public void writeData(User user){
//        listUsers = getData();
//        try {
//            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
//            listUsers.add(user);
//            oos.writeObject(listUsers);
//            oos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public ArrayList<User> getData(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while (true){
                String username = br.readLine();
                if (username == null)
                    break;
                String password = br.readLine();

                listUsers.add(new User(username, password));
                br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listUsers;
    }

//    public ArrayList<User> getData(){
//        ObjectInputStream ois;
//        try {
//            ois = new ObjectInputStream(new FileInputStream(fileName));
//            listUsers = (ArrayList<User>) ois.readObject();
//            ois.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return listUsers;
//    }

    public void clearData() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write("");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
