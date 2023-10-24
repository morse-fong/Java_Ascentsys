package com.ascent.ui;

import com.ascent.bean.Product;
import com.ascent.bean.User;
import com.ascent.util.UserDataClient;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class AccountPanel extends JPanel {

    protected JLabel selectionLabel;
    protected ArrayList<User> productArrayList;
    protected JComboBox categoryComboBox;

    protected JPanel topPanel;

    protected JList userListBox;

    protected JScrollPane userScrollPane;

    protected JButton exitButton;
    protected JButton Item2;
    protected JPanel bottomPanel;

    protected AccountDetailsFrame parentFrame;

    protected UserDataClient myDataClient;


    public AccountPanel(AccountDetailsFrame theParentFrame) {
        try {
//            parentFrame = theParentFrame;
//            myDataClient = new ProductDataClient();
//            selectionLabel = new JLabel("ѡ������");
//            categoryComboBox = new JComboBox();
//            categoryComboBox.setVisible(true);
//            categoryComboBox.addItem("-------");
//
//            ArrayList categoryArrayList = myDataClient.getCategories();
//
//            Iterator iterator = categoryArrayList.iterator();
//            String aCategory;
//
//            while (iterator.hasNext()) {
//                aCategory = (String) iterator.next();
//                categoryComboBox.addItem(aCategory);
//            }
//            topPanel = new JPanel();
//            userListBox = new JList();
//            userListBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//            userScrollPane = new JScrollPane(userListBox);
//
//            exitButton = new JButton("�˳�");
//
//            bottomPanel = new JPanel();
//
//            this.setLayout(new BorderLayout());
//
//            topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//            topPanel.add(selectionLabel);
//            topPanel.add(categoryComboBox);
//
//            this.add(BorderLayout.NORTH, topPanel);
//            this.add(BorderLayout.CENTER, userScrollPane);
//
//            bottomPanel.setLayout(new FlowLayout());
//            bottomPanel.add(exitButton);
//
//            this.add(BorderLayout.SOUTH, bottomPanel);
//
//            exitButton.addActionListener(new ExitActionListener());
            parentFrame = theParentFrame;
            myDataClient = new UserDataClient();
            selectionLabel = new JLabel("ѡ�����");
            categoryComboBox = new JComboBox();
            categoryComboBox.addItem("-------");

//            ArrayList categoryArrayList = myDataClient.getCategories();
//
//            Iterator iterator = categoryArrayList.iterator();
//            String aCategory;
//
//            while (iterator.hasNext()) {
//                aCategory = (String) iterator.next();
//                categoryComboBox.addItem(aCategory);
//            }
            categoryComboBox.addItem("����Ա");
            categoryComboBox.addItem("�ǹ���Ա");
            topPanel = new JPanel();
            userListBox = new JList();
            userListBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            userScrollPane = new JScrollPane(userListBox);

            exitButton = new JButton("�˳�");
            Item2 = new JButton("���");

            bottomPanel = new JPanel();

            this.setLayout(new BorderLayout());

            topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            topPanel.add(selectionLabel);
            topPanel.add(categoryComboBox);

            this.add(BorderLayout.NORTH, topPanel);
            this.add(BorderLayout.CENTER, userScrollPane);

            bottomPanel.setLayout(new FlowLayout());
            bottomPanel.add(exitButton);

            this.add(BorderLayout.SOUTH, bottomPanel);

            exitButton.addActionListener(new ExitActionListener());
            categoryComboBox.addItemListener(new GoItemListener());
            userListBox.addListSelectionListener(new UserListSelectionListener());


        } catch (IOException exc) {
            JOptionPane.showMessageDialog(this, "�������� " + exc, "��������", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    class ExitActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            parentFrame.exit();
        }
    }

    protected void populateListBox() {

        String category = (String) categoryComboBox.getSelectedItem();
        if (!category.startsWith("---")) {
            System.out.println(category);
//                productArrayList = myDataClient.getUsers(category);
            if (category == "�ǹ���Ա") {
                productArrayList = myDataClient.getTwoKindUsers().get(0);
            } else {
                productArrayList = myDataClient.getTwoKindUsers().get(1);
            }
        } else {
            productArrayList = new ArrayList<User>();
        }

        Object[] theData = productArrayList.toArray();

        System.out.println(productArrayList + ">>>>>>>>>>>");
        userListBox.setListData(theData);
        userListBox.updateUI();


        JPopupMenu popupMenu = new JPopupMenu();//����ʽ�˵�
        JMenu Item1 = new JMenu("�޸�");
        Item1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "�����Ⱥ���˵��", "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
                ChangeUserFrame changeFrame = new ChangeUserFrame((User) userListBox.getSelectedValue());
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
        popupMenu.add(Item1);
        JMenu Item2 = new JMenu("ɾ��");
        Item2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog( null, "�������ǲ��������Ҹ�", "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
                boolean bo = myDataClient.delUser((User) userListBox.getSelectedValue());
                if(bo)
                {
                    JOptionPane.showMessageDialog(null, "ɾ���ɹ���", "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "ɾ��ʧ�ܣ�", "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
                }
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
        popupMenu.add(Item2);


        userListBox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.println(userListBox.getSelectedValue());
                    popupMenu.show(userListBox, e.getX(), e.getY());
                }

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
//            if (productArrayList.size() > 0) {
//                clearButton.setEnabled(true);
//            } else {
//                clearButton.setEnabled(false);
//            }

//        } catch (IOException exc) {
//            JOptionPane.showMessageDialog(this, "��������: " + exc, "��������", JOptionPane.ERROR_MESSAGE);
//            System.exit(1);
//        }
    }


    class GoItemListener implements ItemListener {
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                populateListBox();
                System.out.println("GoItemListener");
            }
        }
    }

    class UserListSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
//            System.out.println("aaaaaa");
        }
    }
}
