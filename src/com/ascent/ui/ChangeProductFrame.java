package com.ascent.ui;

import com.ascent.util.ProductDataClient;
import com.ascent.bean.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChangeProductFrame extends JFrame
{
    //��Ʒ�ĸ�����Ϣ
    private JTextField productName;
    private JTextField cas;
    private JTextField structure;
    private JTextField formula, price, realStock, category;
    private JButton button;                 // �޸İ�ť

    private Product pre;
    private Product now;

    private JLabel tip;						// ��ʾ��ǩ
    ProductDataClient myProductClient;      // ��Ʒ���ݶ���

    public ChangeProductFrame(Product product)
    {
        try
        {
            myProductClient = new ProductDataClient();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        pre = product;
        this.setTitle("���޸Ķ�Ӧ����Ϣ");
        // ��ȡһ������������������
        Container container = this.getContentPane();
        // ����Ϊ��ʽ����
        container.setLayout(new BorderLayout());
        JPanel changePanel = new JPanel();

        JLabel name = new JLabel("��Ʒ��: ");
        JLabel casLabel = new JLabel("CAS: ");
        JLabel struct = new JLabel("�ṹͼ: ");
        JLabel formula1 = new JLabel("��ʽ: ");
        JLabel price1 = new JLabel("�۸�: ");
        JLabel realStock1 = new JLabel("����: ");
        JLabel category1 = new JLabel("���: ");

        productName = new JTextField(pre.getProductname(),15);
        cas = new JTextField(pre.getCas(), 15);
        structure = new JTextField(pre.getStructure(), 15);
        formula = new JTextField(pre.getFormula(),15);
        price = new JTextField(pre.getPrice(),15);
        realStock = new JTextField(pre.getRealstock(),15);
        category = new JTextField(pre.getCategory(),15);

        changePanel.add(name);
        changePanel.add(new JScrollPane(productName));
        changePanel.add(casLabel);
        changePanel.add(new JScrollPane(cas));
        changePanel.add(struct);
        changePanel.add(new JScrollPane(structure));
        changePanel.add(formula1);
        changePanel.add(new JScrollPane(formula));
        changePanel.add(price1);
        changePanel.add(new JScrollPane(price));
        changePanel.add(realStock1);
        changePanel.add(new JScrollPane(realStock));
        changePanel.add(category1);
        changePanel.add(new JScrollPane(category));

        // �½�һ��������������ʾ��Ϣ
        JPanel tipPanel = new JPanel();

        // ��ʾ��ǩʵ����
        tip = new JLabel("����д��ز�Ʒ��Ϣ����������İ�ť");

        // ����ǩ���뵽��ʾ���
        tipPanel.add(tip);

        // �������������������������У���ʾ��������������
        container.add(BorderLayout.CENTER, changePanel);
        container.add(BorderLayout.NORTH, tip);

        button = new JButton("�޸�");
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
    class changeListener implements ActionListener
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

            // �½��޸ĺ�Ķ���
            now = new Product(sName, sCas, sStruct, sFormula, sPrice, sRealStock, Scat);

            boolean bo = myProductClient.changeProduct(pre, now);

            if(bo)
            {
                tip.setText("�޸ĳɹ�");
            }
            else
            {
                tip.setText("�޸�ʧ��");
            }
        }
    }
}
