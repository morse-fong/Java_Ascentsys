package com.ascent.ui;

import com.ascent.util.ProductDataClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;		// ��������
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class AddFrame extends JFrame
{
    // ��Ʒ�ĸ�����Ϣ
    private JTextField productName;
    private JTextField cas;
    private JTextField structure;
    private JTextField formula, price, realStock, category;
    private JButton button;                 // ��Ӱ�ť

    private JLabel tip;						// ��ʾ��ǩ
    ProductDataClient myProductClient;      // ��Ʒ���ݶ���

    public AddFrame()
    {
        this.setTitle("��Ӳ�Ʒ");
        // ��ȡһ������������������
        Container container = this.getContentPane();
        // ����Ϊ��ʽ����
        container.setLayout(new BorderLayout());
        JPanel addPanel = new JPanel();

        JLabel name = new JLabel("��Ʒ��: ");
        JLabel casLabel = new JLabel("CAS: ");
        JLabel struct = new JLabel("�ṹͼ: ");
        JLabel formula1 = new JLabel("��ʽ: ");
        JLabel price1 = new JLabel("�۸�: ");
        JLabel realStock1 = new JLabel("����: ");
        JLabel category1 = new JLabel("���: ");

        productName = new JTextField(15);
        cas = new JTextField(15);
        structure = new JTextField(15);
        formula = new JTextField(15);
        price = new JTextField(15);
        realStock = new JTextField(15);
        category = new JTextField(15);

        addPanel.add(name);
        addPanel.add(new JScrollPane(productName));
        addPanel.add(casLabel);
        addPanel.add(new JScrollPane(cas));
        addPanel.add(struct);
        addPanel.add(new JScrollPane(structure));
        addPanel.add(formula1);
        addPanel.add(new JScrollPane(formula));
        addPanel.add(price1);
        addPanel.add(new JScrollPane(price));
        addPanel.add(realStock1);
        addPanel.add(new JScrollPane(realStock));
        addPanel.add(category1);
        addPanel.add(new JScrollPane(category));


        setResizable(false);
        setSize(230, 300);
        setLocation(100, 100);

        // �½�һ��������������ʾ��Ϣ
        JPanel tipPanel = new JPanel();

        // ��ʾ��ǩʵ����
        tip = new JLabel("����д��ز�Ʒ��Ϣ���������Ӱ�ť");

        // ����ǩ���뵽��ʾ���
        tipPanel.add(tip);

        // �������������������������У���ʾ��������������
        container.add(BorderLayout.CENTER, addPanel);
        container.add(BorderLayout.NORTH, tip);

        button = new JButton("���");
        JPanel panel = new JPanel();
        panel.add(button);
        container.add(BorderLayout.SOUTH, panel);
        button.addActionListener(new AddListener());

        try
        {
            myProductClient = new ProductDataClient();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    class AddListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            String sName = productName.getText();
            String sCas = cas.getText();
            String sStruct = structure.getText();
            String sFormula = formula.getText();
            String sPrice = price.getText();
            String sRealStock = realStock.getText();
            String Scat = category.getText();
            boolean bo = myProductClient.addProduct(sName, sCas, sStruct, sFormula, sPrice, sRealStock, Scat);

            if(bo)
            {
                tip.setText("��ӳɹ�");
            }
            else
            {
                tip.setText("���ʧ��");
            }
        }
    }
}
