package com.ascent.ui;

import javax.swing.*;
import javax.swing.event.*;

// �����Ʒ�Ͳ�Ʒ������
import com.ascent.bean.Product;
import com.ascent.util.ProductDataClient;
import com.ascent.util.ShoppingCart;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.io.*;

/**
 * ����๹����Ʒ���
 * @author ascent
 * @version 1.0
 */
@SuppressWarnings("serial")
public class WLPanel extends JPanel
{

    protected JLabel selectionLabel;			// ѡ���ǩ

    protected JComboBox categoryComboBox;		// ��Ͽ�

    protected JPanel topPanel;					// �������

    protected JList productListBox;				// ��Ʒ�б�

    protected JScrollPane productScrollPane;	// ��Ʒ�ƶ����

    protected JButton detailsButton;			// ��ϸ��ť

    protected JButton clearButton;				// ��հ�ť

    protected JButton exitButton;				// �˳���ť

    protected JButton refreshButton;			// �鿴���ﳵ��ť

    protected JPanel bottomPanel;				// �ײ����

    protected MainFrame parentFrame;			// �����ڣ�����MainFrame

    protected ArrayList<Product> productArrayList;	// ��Ʒ�����б�

    protected ProductDataClient myDataClient;		// ��Ʒ���ݶ���
    protected ShoppingCart shoppingCart;
    protected WLPanel wlPanel;		// ��Ʒ����࣬�Զ����


    /**
     * ������Ʒ���Ĺ��췽��
     * @param theParentFrame ���ĸ�����
     */
    public WLPanel(MainFrame theParentFrame)
    {
        try
        {
            // ������
            parentFrame = theParentFrame;
            // �½�һ����Ʒ�����������ʵ����׼����ȡ��Ʒ����
            myDataClient = new ProductDataClient();
            // ѡ���ǩ�Ӹ�����
            selectionLabel = new JLabel("ѡ�����");
            // �½�һ����Ͽ����
            categoryComboBox = new JComboBox();
            // �������Ͽ��----- �������
            categoryComboBox.addItem("-------");
            categoryComboBox.addItem("δ����");


            // ��Ӧ�½�����
            topPanel = new JPanel();
            productListBox = new JList();
            // ����ѡ��ʽ-----����ѡ��
            productListBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            // ��Ʒ�������
            productScrollPane = new JScrollPane(productListBox);

            // �½��ĸ���ť
            detailsButton = new JButton("��ϸ...");
            clearButton = new JButton("���");
            exitButton = new JButton("�˳�");
            refreshButton = new JButton("֧��");

            // �ײ����
            bottomPanel = new JPanel();

            // ����Ϊ��ʽ����
            this.setLayout(new BorderLayout());

            // ��������Ϊ��ʽ���֣��Ҿ���
            topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            // ��ѡ���ǩ�������Ͽ���뵽����
            topPanel.add(selectionLabel);
            topPanel.add(categoryComboBox);

            // ��������������ڶ���
            this.add(BorderLayout.NORTH, topPanel);
            // ����Ʒ���������뵽��ʽ���ֵ��м�
            this.add(BorderLayout.CENTER, productScrollPane);

            // �ײ��������Ϊ��ʽ����
            bottomPanel.setLayout(new FlowLayout());
            bottomPanel.add(refreshButton);
            bottomPanel.add(detailsButton);
            bottomPanel.add(clearButton);
            bottomPanel.add(exitButton);

            // ���ײ������뵽��ʽ���ֵĵײ�
            this.add(BorderLayout.SOUTH, bottomPanel);

            // ����ť�������򣬲�Ʒ��������Ӷ�Ӧ���¼�
            detailsButton.addActionListener(new DetailsActionListener());
            clearButton.addActionListener(new ClearActionListener());
            exitButton.addActionListener(new ExitActionListener());
            refreshButton.addActionListener(new refreshActionListener());
            categoryComboBox.addItemListener(new GoItemListener());
            productListBox.addListSelectionListener(new ProductListSelectionListener());

            // ��ʼʱ�����˳���ť������Ϊ���ɵ��
            detailsButton.setEnabled(false);
            clearButton.setEnabled(false);
            //refreshButton.setEnabled(false);

        }
        // ����IO�쳣
        catch (IOException exc)
        {
            JOptionPane.showMessageDialog(this, "�������� " + exc, "��������", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * ����������ѡ�еķ���ѡ��
     */
    protected void populateListBox()
    {
        // ��ȡ���
        String category = (String) categoryComboBox.getSelectedItem();
        shoppingCart = new ShoppingCart();

        // ֻҪ���ǿ���𣬾ͻ�ȡ��Ӧ�Ĳ�Ʒ����
        if (!category.startsWith("---"))
        {
            productArrayList = shoppingCart.getShoppingList();
        }
        // ����ֱ���½�һ���յĲ�Ʒ����
        else
        {
            productArrayList = new ArrayList<Product>();
        }

        // ��data���黯
        Object[] theData = productArrayList.toArray();

        // ��ӡ��Ӧ�Ĳ�Ʒ��Ϣ
        System.out.println(productArrayList + ">>>>>>>>>>>");
        // �����б�����
        productListBox.setListData(theData);
        // ����UI��������ʵ���½���
        productListBox.updateUI();

        // �����Ʒ����������0����հ�ť����Ϊ�ɵ��
        if (productArrayList.size() > 0)
        {
            clearButton.setEnabled(true);
        }
        else
        {
            clearButton.setEnabled(false);
        }
    }

    /**
     * ����ѡ����ϸ...��ťʱ�������¼�������
     * @author ascent
     */
    class DetailsActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int index = productListBox.getSelectedIndex();
            Product product = (Product) productArrayList.get(index);
            ProductDetailsDialog myDetailsDialog = new ProductDetailsDialog(parentFrame, product, refreshButton);
            myDetailsDialog.setVisible(true);
        }
    }

    /**
     * ����ѡ��鿴���ﳵ��ťʱ�������¼�������
     * @author ascent
     */
    class ShoppingActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            ShoppingCartDialog myShoppingDialog = new ShoppingCartDialog(
                    parentFrame, refreshButton);
            myShoppingDialog.setVisible(true);
        }
    }

    /**
     * ����ѡ���˳���ťʱ�������¼�������
     * @author ascent
     */
    class ExitActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            parentFrame.exit();
        }
    }

    /**
     * ����ѡ����հ�ťʱ�������¼�������
     * @author ascent
     */
    class ClearActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            Object[] noData = new Object[1];
            productListBox.setListData(noData);
            categoryComboBox.setSelectedIndex(0);
        }
    }

    /**
     * ����ѡ�з���������ѡ��ѡ��ʱ�������¼�������
     * @author ascent
     */
    class GoItemListener implements ItemListener {
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                populateListBox();
            }
        }
    }

    /**
     * ����ѡ�з����б���ѡ��ʱ�������¼�������
     * @author ascent
     */
    class ProductListSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (productListBox.isSelectionEmpty()) {
                detailsButton.setEnabled(false);
            } else {
                detailsButton.setEnabled(true);
            }
        }
    }
    class refreshActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            categoryComboBox.updateUI();

        }
    }

    protected void log(Object msg)
    {
        System.out.println("ProductDataAccessor��: " + msg);
    }
}