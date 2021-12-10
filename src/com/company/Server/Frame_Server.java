package com.company.Server;

import javax.swing.*;
import java.awt.*;

public class Frame_Server extends JFrame {
    JPanel panel = new JPanel();
    JTextArea textArea = new JTextArea();
    JTextField textField = new JTextField();
    JButton btnSend = new JButton("Send");

    public Frame_Server(){
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Server Chat Box");
        setContentPane(panel);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 5;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;

        textArea.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(textArea, gbc);

        gbc.weighty = 1;
        gbc.weightx = 3;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(textField, gbc);

        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(btnSend, gbc);

        setVisible(true);
        setSize(500, 500);

        processChat();
    }

    void processChat(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                TCP_Server tcpServer = new TCP_Server(textArea, textField);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        btnSend.addActionListener(tcpServer);
                    }
                });
            }
        });
        thread.start();
    }
}
