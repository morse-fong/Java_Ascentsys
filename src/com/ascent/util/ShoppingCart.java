package com.ascent.util;

import java.util.ArrayList;
// 获取商品类
import com.ascent.bean.Product;

/**
 * 购物车
 * @author ascent
 * @version 1.0
 */
public class ShoppingCart
{
	private static int num = 10000;
	/**
	 * 存放购买商品信息
	 */
	private static ArrayList<Product> shoppingList = new ArrayList<Product>();
	public static int[] productNum = new int[num];			// 记录商品计数
	private static int index = 0;

	/**
	 * 获取所有购买商品信息
	 * @return shoppingList
	 */
	public ArrayList<Product> getShoppingList() {
		return this.shoppingList;
	}

	/**
	 * 添加商品到购物车
	 * @param myProduct
	 */
	public void addProduct(Product myProduct)
	{
		Product product;
		boolean bo = false;
		// 遍历已经在购物车的药品，如果有则直接退出
		for (int i = 0; i < shoppingList.size(); i++)
		{
			product = shoppingList.get(i);
			if (myProduct.getProductname().trim().equals(product.getProductname().trim()))
			{
				bo = true;
				productNum[i]++;
				break;
			}
		}
		// 如果不在，则直接退出
		if (!bo)
		{
			shoppingList.add(myProduct);
			productNum[index++] = 1;
		}
	}

	/**
	 * 清空购物车所购买商品
	 */
	// 清空购物车
	public void clearProduct() {
		shoppingList.clear();
		index = 0;
	}

}
