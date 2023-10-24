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
import javax.swing.JPasswordField;		// 密码框组件
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class AddFrame extends JFrame
{
    // 产品的各种信息
    private JTextField productName;
    private JTextField cas;
    private JTextField structure;
    private JTextField formula, price, realStock, category;
    private JButton button;                 // 添加按钮

    private JLabel tip;						// 提示标签
    ProductDataClient myProductClient;      // 产品数据对象

    public AddFrame()
    {
        this.setTitle("添加产品");
        // 获取一个容器，用来存放面板
        Container container = this.getContentPane();
        // 设置为盒式布局
        container.setLayout(new BorderLayout());
        JPanel addPanel = new JPanel();

        JLabel name = new JLabel("产品名: ");
        JLabel casLabel = new JLabel("CAS: ");
        JLabel struct = new JLabel("结构图: ");
        JLabel formula1 = new JLabel("公式: ");
        JLabel price1 = new JLabel("价格: ");
        JLabel realStock1 = new JLabel("数量: ");
        JLabel category1 = new JLabel("类别: ");

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

        // 新建一个面板用于输出提示信息
        JPanel tipPanel = new JPanel();

        // 提示标签实例化
        tip = new JLabel("请填写相关产品信息，并点击添加按钮");

        // 将标签加入到提示面板
        tipPanel.add(tip);

        // 将添加内容面板设置在容器居中，提示设置在容器顶部
        container.add(BorderLayout.CENTER, addPanel);
        container.add(BorderLayout.NORTH, tip);

        button = new JButton("添加");
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
                tip.setText("添加成功");
            }
            else
            {
                tip.setText("添加失败");
            }
        }
    }
}
