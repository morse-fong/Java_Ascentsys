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
    //产品的各种信息
    private JTextField productName;
    private JTextField cas;
    private JTextField structure;
    private JTextField formula, price, realStock, category;
    private JButton button;                 // 修改按钮

    private Product pre;
    private Product now;

    private JLabel tip;						// 提示标签
    ProductDataClient myProductClient;      // 产品数据对象

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
        this.setTitle("请修改对应的信息");
        // 获取一个容器，用来存放面板
        Container container = this.getContentPane();
        // 设置为盒式布局
        container.setLayout(new BorderLayout());
        JPanel changePanel = new JPanel();

        JLabel name = new JLabel("产品名: ");
        JLabel casLabel = new JLabel("CAS: ");
        JLabel struct = new JLabel("结构图: ");
        JLabel formula1 = new JLabel("公式: ");
        JLabel price1 = new JLabel("价格: ");
        JLabel realStock1 = new JLabel("数量: ");
        JLabel category1 = new JLabel("类别: ");

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

        // 新建一个面板用于输出提示信息
        JPanel tipPanel = new JPanel();

        // 提示标签实例化
        tip = new JLabel("请填写相关产品信息，并点击更改按钮");

        // 将标签加入到提示面板
        tipPanel.add(tip);

        // 将添加内容面板设置在容器居中，提示设置在容器顶部
        container.add(BorderLayout.CENTER, changePanel);
        container.add(BorderLayout.NORTH, tip);

        button = new JButton("修改");
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

            // 新建修改后的对象
            now = new Product(sName, sCas, sStruct, sFormula, sPrice, sRealStock, Scat);

            boolean bo = myProductClient.changeProduct(pre, now);

            if(bo)
            {
                tip.setText("修改成功");
            }
            else
            {
                tip.setText("修改失败");
            }
        }
    }
}
