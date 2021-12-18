package com.company.Client1.View;


import com.company.Client1.Message.ResponseMessage;
import com.company.Client1.View.Panel.ChatPanel;
import com.company.Client1.View.Panel.TopPanel;
import com.company.Client1.model.ClientSocket;
import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class Home extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form Home
     */
    public Home(ArrayList<ClientSocket> onlineUser, Socket currentSocket, String username) {
        this.onlineUser = onlineUser;
        this.currentSocket = currentSocket;
        this.currenUser = username;

        listUser = new String[onlineUser.size()];
        Thread uploadListThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    BufferedReader br = new BufferedReader(new InputStreamReader(currentSocket.getInputStream()));
                    DataInputStream dis = new DataInputStream(currentSocket.getInputStream());
                    while(true){
                        String msg = br.readLine();
                        ResponseMessage responseMessage = gson.fromJson(msg, ResponseMessage.class);
                        if (responseMessage.getType().equals("NewUser")){
                            ClientSocket user = responseMessage.getNewUser();
                            dlm.addElement(user.getUsername());

                            ChatPanel newChatPanel = new ChatPanel(user, currentSocket, currenUser);
                            chatPanelList.add(newChatPanel);

                            centerPanel.add(newChatPanel, user.getUsername());
                            System.out.println("response message : " + responseMessage);
                            System.out.println("New User : " + user);
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    listOnlineUser.setModel(dlm);
                                    if (dlm.size() == 1)
                                        cardLayout.show(centerPanel, user.getUsername());
                                }
                            });
                        }
                        else if (responseMessage.getType().equals("Delete")){
                            ClientSocket deleteUser = responseMessage.getDeleteUser();

                            int indexRemove = 0;
                            for (int i = 0; i < dlm.size(); i++){
                                String checkuser = dlm.getElementAt(i);
                                if (checkuser.equals(deleteUser.getUsername())){
                                    indexRemove = i;
                                    break;
                                }
                            }

                            dlm.remove(indexRemove);
                            cardLayout.removeLayoutComponent(chatPanelList.get(indexRemove));
                            chatPanelList.remove(indexRemove);
                        }
                        else if (responseMessage.getType().equals("Chat") || responseMessage.getType().equals("File")){
                            System.out.println("-------------------------------");
                            System.out.println("Current User : " + currenUser);
                            System.out.println("Received" );
                            System.out.println(msg);
                            System.out.println("-------------------------------");
                            //Bước 1 : Tìm FromClientSocket
                            ClientSocket fromClientSocket = responseMessage.getFromUser();
                            //Bước 2 : Chọn phòng ChatPannel trùng với FromClientSocket
                            for (ChatPanel chatRoom : chatPanelList)
                                if (chatRoom.getToUser().getUsername().equals(fromClientSocket.getUsername())) {
                                    if (responseMessage.getType().equals("Chat"))
                                        chatRoom.setMessage(responseMessage.getMessage());
                                    else {
                                        chatRoom.setFile();
                                    }
                                }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Something error");
                }
            }
        });
        uploadListThread.start();
        int i = 0;
        for (ClientSocket user : onlineUser)
            listUser[i++] = user.toString();
        initComponents();
    }

    public Home(){
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        mainPanel.add(topPanel(), BorderLayout.PAGE_START);
        mainPanel.add(westPanel(), BorderLayout.WEST);
        mainPanel.add(centerPanel(), BorderLayout.CENTER);

        mainPanel.setPreferredSize(new Dimension(1000, 600));

        pack();
    }// </editor-fold>

    private JPanel topPanel() {
        return new TopPanel(currenUser, this);
    }

    private JPanel westPanel(){
        westPanel = new JPanel();
        westPanel.setLayout(new BorderLayout());

        listOnlineUser = new JList();
        listOnlineUser.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                jList1ActionPerformed(e);
            }
        });

        dlm = new DefaultListModel();
        for (ClientSocket user : onlineUser)
            dlm.addElement(user.getUsername());

        listOnlineUser.setModel(dlm);
        JScrollPane sp = new JScrollPane(listOnlineUser);

        westPanel.add(sp);
        return westPanel;
    }

    private JPanel centerPanel(){
        centerPanel = new JPanel();
        cardLayout = new CardLayout();
        centerPanel.setLayout(cardLayout);
        // Initialize ChatPanel for every user online
        for (ClientSocket toUser : onlineUser) {
            ChatPanel chatPanel = new ChatPanel(toUser, currentSocket, currenUser);
            chatPanelList.add(chatPanel);
            centerPanel.add(chatPanel, toUser.getUsername());
        }

        return centerPanel;
    }

    private ClientSocket getClientSocket(String username){
        for (ClientSocket user : onlineUser)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    private void jList1ActionPerformed(ListSelectionEvent evt) {
        // TODO add your handling code here:
        String username = listOnlineUser.getSelectedValue();
        System.out.println("Current User : " + username);
        cardLayout.show(centerPanel, username);
    }


    // Variables declaration - do not modify
    private JPanel mainPanel, topPanel, westPanel, centerPanel;
    private javax.swing.JButton jButton2;
    private JList<String> listOnlineUser;
    private DefaultListModel<String> dlm;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;

    private ArrayList<ChatPanel> chatPanelList =new ArrayList<>();
    CardLayout cardLayout;
    private ArrayList<ClientSocket>  onlineUser = new ArrayList<>();
    private Socket currentSocket;
    String[] listUser = null;
    String currenUser;

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            currentSocket.close();
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Login().setVisible(true);
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    // End of variables declaration
}
