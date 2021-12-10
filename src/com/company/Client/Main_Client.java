package com.company.Client;

import javax.swing.*;

public class Main_Client {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame_Client();
            }

        });
    }
}
