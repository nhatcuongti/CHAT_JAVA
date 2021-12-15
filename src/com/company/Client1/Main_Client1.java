package com.company.Client1;

import javax.swing.*;

public class Main_Client1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
               @Override
               public void run() {
                   new Frame_Client("Client1");
               }
           }
        );
    }
}
