package Lambda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Class1 {

	// 源自 http://www.thinksaas.cn/topics/0/417/417390.html

	void Step1() {
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

	void Step2() {
		// 一堆价格数据
		// 只有超过20的打九折，计算其总的折扣
		List<BigDecimal> prices = Arrays.asList(new BigDecimal("10"), new BigDecimal("30"), new BigDecimal("17"),
				new BigDecimal("20"), new BigDecimal("15"), new BigDecimal("18"), new BigDecimal("45"),
				new BigDecimal("12"));

		BigDecimal totalOfDiscountedPrices = BigDecimal.ZERO;
		for (BigDecimal price : prices) {
			// 判断价格是否大于20
			if (price.compareTo(BigDecimal.valueOf(20)) > 0)
				// 计算 折扣数
				totalOfDiscountedPrices = totalOfDiscountedPrices.add(price.multiply(BigDecimal.valueOf(0.1)));
		}
		System.out.println("Total of discounted prices: " + totalOfDiscountedPrices);

		// 代码有点像需求规范。这样能缩小业务需求和实现的代码之间的差距，减少了需求被误读的可能性
		// 不再让Java去创建一个变量然后没完没了的给它赋值了，我们要从一个更高层次的抽象去与它沟通

		// 过滤出大于20块的价格，计算其折扣数据，进行数据累加
		// 运用了map\reduce，可以利用好多核cpu
		// 调用 了价格列表的stream方法，这样可以引入数不尽的便捷的迭代器

		// stream用于后续引入的迭代器
		// filter进行数据过滤
		// map进行乘积运算，得到折扣价格
		// reduce进行累加

		// 之前的for循环是迭代过程，效率不高，这样的迭代就是个万金油，啥都会点，但样样稀松
		// Java为许多操作都专门提供了内建的迭代器：
		// 1、只做循环的，2、有做map操作的，过滤值的，3、做reduce操作的，4、还有许多方便的函数比如 最大最小值，平均值等等
		totalOfDiscountedPrices = prices.stream().filter(price -> price.compareTo(BigDecimal.valueOf(20)) > 0)
				.map(price -> price.multiply(BigDecimal.valueOf(0.1))).reduce(BigDecimal.ZERO, BigDecimal::add);

		System.out.println("Total of discounted prices: " + totalOfDiscountedPrices);
	}

	void Step3() {
		// 需求如下：
		// 1、确认某个操作已经正确的进行了安全认证，2、我们要保证事务能够快速执行，并且正确的更新修改日志

		// 传统代码 需要执行以下几个步骤 ：
		// 1、Transaction transaction = getFromTransactionFactory();
		// 2、//... operation to run within the transaction ...
		// 3、checkProgressAndCommitOrRollbackTransaction();
		// 4、UpdateAuditTrail();

		// 带来的问题
		// 1、导致了重复的工作量并且还增加了维护的成本
		// 2、容易忘了业务代码中可能会被抛出来的异常，可能会影响到事务的生命周期和修改日志的更新。
		// 3、应该使用try, finally块来实现，不过每当有人动了这块代码，我们又得重新确认这个策略没有被破坏

		// 改进代码：
		// 1、runWithinTransaction((Transaction transaction) -> {
		// 2、//... operation to run within the transaction ...
		// 3、});
		// 检查状态同时更新日志的这个策略被抽象出来封装到了runWithinTransaction方法里
		// 我们给这个方法发送一段需要在事务上下文里运行的代码
		
		//
		//
		//
		//
	}

	public static void main(String[] arg) {
		Class1 class1 = new Class1();
		class1.Step1();
		class1.Step2();

	}

	//
}
