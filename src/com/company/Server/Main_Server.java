package com.company.Server;

import javax.swing.*;

public class Main_Server {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame_Server();
            }

        });
    }
}
