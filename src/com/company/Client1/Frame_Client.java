package com.company.Client1;

import javax.swing.*;
import java.awt.*;

public class Frame_Client extends JFrame {
    JPanel panel = new JPanel();
    JTextArea textArea = new JTextArea();
    JTextField textField = new JTextField();
    JButton btnSend = new JButton("Send");

    public Frame_Client(String tittle){
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(tittle);
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

    private void processChat() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                TCP_Client tcpClient = new TCP_Client(textArea, textField);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        btnSend.addActionListener(tcpClient);
                    }
                });
            }
        });
        thread.start();

    }
}
