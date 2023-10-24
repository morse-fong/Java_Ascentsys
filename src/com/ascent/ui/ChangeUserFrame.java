package com.ascent.ui;

import com.ascent.bean.User;
import com.ascent.util.UserDataClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChangeUserFrame extends JFrame {
    //产品的各种信息
    private JTextField Name, Password, Jurisdiction;
    private JButton button;                 // 修改按钮

    private User pre;
    private User now;

    private JLabel tip;                        // 提示标签
    UserDataClient myProductClient;      // 产品数据对象

    public ChangeUserFrame(User product) {
        try {
            myProductClient = new UserDataClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pre = product;
        this.setTitle("请修改");
        // 获取一个容器，用来存放面板
        Container container = this.getContentPane();
        // 设置为盒式布局
        container.setLayout(new BorderLayout());
        JPanel changePanel = new JPanel();

        JLabel name = new JLabel("账号: ");
        JLabel password = new JLabel("密码: ");
        JLabel jurisdiction = new JLabel("权限: ");

        Name = new JTextField(pre.getUsername(), 15);
        Password = new JTextField(pre.getPassword(), 15);
        Jurisdiction = new JTextField(String.valueOf(pre.getAuthority()), 15);
        changePanel.add(name);
        changePanel.add(new JScrollPane(Name));
        changePanel.add(password);
        changePanel.add(new JScrollPane(Password));
        changePanel.add(jurisdiction);
        changePanel.add(new JScrollPane(Jurisdiction));

        // 新建一个面板用于输出提示信息
        JPanel tipPanel = new JPanel();

        // 提示标签实例化
        tip = new JLabel("请填写相关产品信息，并点击确定按钮");

        // 将标签加入到提示面板
        tipPanel.add(tip);

        // 将添加内容面板设置在容器居中，提示设置在容器顶部
        container.add(BorderLayout.CENTER, changePanel);
        container.add(BorderLayout.NORTH, tip);

        button = new JButton("确定");
        JPanel panel = new JPanel();
        panel.add(button);
        container.add(BorderLayout.SOUTH, panel);
        button.addActionListener(new changeListener());


        setResizable(false);
        setSize(230, 300);
        setLocation(100, 100);
    }

    /**
     * 监听修改按钮
     */
    class changeListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            String sName = Name.getText();
            String sPassword = Password.getText();
            String sJurisdiction = Jurisdiction.getText();

            // 新建修改后的对象
            now = new User(sName, sPassword, Integer.parseInt(sJurisdiction));

            boolean bo = myProductClient.changeUser(pre, now);
            System.out.println(bo);
            if (bo) {
                tip.setText("修改成功");
            } else {
                tip.setText("修改失败");
            }
        }
    }
}
