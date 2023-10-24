package com.ascent.ui;

import com.ascent.bean.User;
import com.ascent.util.UserDataClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChangeUserFrame extends JFrame {
    //��Ʒ�ĸ�����Ϣ
    private JTextField Name, Password, Jurisdiction;
    private JButton button;                 // �޸İ�ť

    private User pre;
    private User now;

    private JLabel tip;                        // ��ʾ��ǩ
    UserDataClient myProductClient;      // ��Ʒ���ݶ���

    public ChangeUserFrame(User product) {
        try {
            myProductClient = new UserDataClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pre = product;
        this.setTitle("���޸�");
        // ��ȡһ������������������
        Container container = this.getContentPane();
        // ����Ϊ��ʽ����
        container.setLayout(new BorderLayout());
        JPanel changePanel = new JPanel();

        JLabel name = new JLabel("�˺�: ");
        JLabel password = new JLabel("����: ");
        JLabel jurisdiction = new JLabel("Ȩ��: ");

        Name = new JTextField(pre.getUsername(), 15);
        Password = new JTextField(pre.getPassword(), 15);
        Jurisdiction = new JTextField(String.valueOf(pre.getAuthority()), 15);
        changePanel.add(name);
        changePanel.add(new JScrollPane(Name));
        changePanel.add(password);
        changePanel.add(new JScrollPane(Password));
        changePanel.add(jurisdiction);
        changePanel.add(new JScrollPane(Jurisdiction));

        // �½�һ��������������ʾ��Ϣ
        JPanel tipPanel = new JPanel();

        // ��ʾ��ǩʵ����
        tip = new JLabel("����д��ز�Ʒ��Ϣ�������ȷ����ť");

        // ����ǩ���뵽��ʾ���
        tipPanel.add(tip);

        // �������������������������У���ʾ��������������
        container.add(BorderLayout.CENTER, changePanel);
        container.add(BorderLayout.NORTH, tip);

        button = new JButton("ȷ��");
        JPanel panel = new JPanel();
        panel.add(button);
        container.add(BorderLayout.SOUTH, panel);
        button.addActionListener(new changeListener());


        setResizable(false);
        setSize(230, 300);
        setLocation(100, 100);
    }

    /**
     * �����޸İ�ť
     */
    class changeListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            String sName = Name.getText();
            String sPassword = Password.getText();
            String sJurisdiction = Jurisdiction.getText();

            // �½��޸ĺ�Ķ���
            now = new User(sName, sPassword, Integer.parseInt(sJurisdiction));

            boolean bo = myProductClient.changeUser(pre, now);
            System.out.println(bo);
            if (bo) {
                tip.setText("�޸ĳɹ�");
            } else {
                tip.setText("�޸�ʧ��");
            }
        }
    }
}
