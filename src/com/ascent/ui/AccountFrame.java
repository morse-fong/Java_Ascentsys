package com.ascent.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.*;

import com.ascent.bean.Product;
import com.ascent.bean.User;
import com.ascent.util.UserDataClient;
public class AccountFrame extends JFrame{
    private JTextField accountText;

    private JPasswordField password;
    private JLabel tip;

    private JTabbedPane tabbedPane;

    private AccountPanel userPanel;
    private UserDataClient userDataClient;
    protected JList userListBox;

    /**
     * 默认构造方法，初始化用户注册窗体
     */
    public AccountFrame() {
        this.setTitle("账号管理");
        userListBox = new JList();
        userListBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel registPanel = new JPanel();

        JLabel searchAccount = new JLabel("管理帐号：");
        JLabel passwordLabel = new JLabel("账号密码：");

        accountText = new JTextField(15);
        password = new JPasswordField(15);
        JButton search = new JButton("确认");
        JMenu copyItem=new JMenu("修改");
        copyItem.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JOptionPane.showMessageDialog(null, "搞乱了高杨负责", "系统信息", JOptionPane.INFORMATION_MESSAGE);
                ChangeProductFrame changeFrame = new ChangeProductFrame((Product) userListBox.getSelectedValue());
                changeFrame.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        JButton exitButton = new JButton("退出");
        registPanel.add(searchAccount);
        registPanel.add(new JScrollPane(accountText));
        registPanel.add(passwordLabel);
        registPanel.add(new JScrollPane(password));
        registPanel.add(search);
        registPanel.add(exitButton);

        setResizable(false);
        setSize(260, 150);
        setLocation(300, 100);

        JPanel tipPanel = new JPanel();

        tip = new JLabel();

        tipPanel.add(tip);

        container.add(BorderLayout.CENTER, registPanel);
        container.add(BorderLayout.NORTH, tip);

        exitButton.addActionListener(new AccountFrame.ExitActionListener());
        search.addActionListener(new AccountFrame.SearchActionListener());
        this.addWindowFocusListener(new WindowFocusListener() {// 设置父窗口
            public void windowGainedFocus(WindowEvent e) {
            }
            public void windowLostFocus(WindowEvent e) {
                e.getWindow().toFront();
            }
        });
        try {
            userDataClient = new UserDataClient();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public class ExitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }
    }

    public class SearchActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean bo = false;
            boolean isAdmin = false;
            HashMap userTable = userDataClient.getUsers();
            if (userTable != null) {
                if (userTable.containsKey(accountText.getText())) {
                    User userObject = (User) userTable.get(accountText.getText());
                    char[] chr = password.getPassword();
                    String pwd = new String(chr);
                    if (userObject.getPassword().equals(pwd)) {
                        bo = true;
                    }
                    if(userObject.getAuthority() == 1){
                        isAdmin = true;
                    }
                }
                if (bo) {
                    if(isAdmin){
                        userDataClient.closeSocKet();
                        setVisible(false);
                        dispose();
                        AccountDetailsFrame myFrame =new AccountDetailsFrame();
                        myFrame.setVisible(true);
                    }
                    else{
                        setSize(260, 150);
                        tip.setText("不是管理者账号.");
                    }
                } else {
                    setSize(260, 150);
                    tip.setText("帐号不存在,或密码错误.");
                }
            } else {
                tip.setText("服务器连接失败,请稍候再试.");
            }
        }

    }
    public void exit() {
        setVisible(false);
        dispose();
    }

}
