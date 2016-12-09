package Thinksaas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Class1 {

	// 源自 http://www.thinksaas.cn/topics/0/417/417390.html
	public static void main(String[] arg) {
		
		// step1 传统代码
		{
			String[] cities = { "Chicago", "NewYork" };
			boolean found = false;
			for (String city : cities) {
				if (city.equals("Chicago")) {
					found = true;
					break;
				}
			}
			System.out.println("Found chicago?:" + found);
		}

		// step1 改进代码
		{
			String[] cities = { "Chicago", "NewYork" };
			List citiesList = new ArrayList();
			// 数组转为集合
			Collections.addAll(citiesList, cities);
			// contains判断集合中是否包含某个元素
			System.out.println("Found chicago?:" + citiesList.contains("Chicago"));
		}
		
	}

	//
}
