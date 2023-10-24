package com.ascent.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AccountDetailsFrame extends JFrame{

    protected JTabbedPane tabbedPane;

    protected AccountPanel userPanel;

    public AccountDetailsFrame(){
        setTitle("账号管理");

        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        userPanel = new AccountPanel(this);
        tabbedPane.addTab("用户管理",userPanel);
        this.add(tabbedPane,BorderLayout.CENTER);
        tabbedPane.setVisible(true);
        setSize(500, 400);
        setLocation(100, 100);
    }

    public void exit() {
        setVisible(false);
        dispose();
    }
}

