package Lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Class2 {

	public static void main(String[] args) {
		
		// http://www.thinksaas.cn/topics/0/417/417387.html

		final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

		// 遍历列表方法1
		for (int i = 0; i < friends.size(); i++) {
			System.out.println(friends.get(i));
		}

		// 遍历列表方法2
		for (String name : friends) {
			System.out.println(name);
		}

		// 遍历列表方法3
		friends.forEach(new Consumer<String>() {
			public void accept(final String name) {
				System.out.println(name);
			}
		});

		// 遍历列表方法4
		friends.forEach((final String name) -> System.out.println(name));

		// 遍历列表方法5
		friends.forEach(System.out::println);

		// 将列表中的名字转化成全大写的
		// 方法1
		final List<String> uppercaseNames = new ArrayList<String>();
		for (String name : friends) {
			uppercaseNames.add(name.toUpperCase());
		}
		
		// 方法2
		friends.forEach(name -> uppercaseNames.add(name.toUpperCase()));
		
		// 方法3
		friends.stream()
		.map(name -> name.toUpperCase())
		.forEach(name -> System.out.print(name + " "));
		
		// 方法4  引用了String.toUpperCase方法
		friends.stream()
		.map(String::toUpperCase);
		
		// JDK8中的所有集合都支持这个stream方法，它把集合封装成一个Steam实例
		// map方法对Stream中的每个元素都调用了指定的lambda表达式或者代码块
		// map方法跟forEach方法很不一样， 
		//			forEach只是简单的对集合中的元素执行了一下指定的函数。
		//			map方法把lambda表达式的运行结果收齐起来，返回一个结果集
		// map方法很适合把一个输入集合转化成一个新的输出集合
		// 			确保了输入输出序列的元素的数量是相同的
		//			然而输入元素和输出元素的类型可以是不一样的
		
		// 转为了人名的字母个数信息序列
		friends.stream()
			.map(name -> name.length());
		
		// 找到其中人名以N开头的		
		final List<String> startsWithN = new ArrayList<String>();
		for(String name : friends) {
			if(name.startsWith("N")) {
				startsWithN.add(name);
			}
		}
		
		// 1、filter方法接收一个返回布尔值的lambda表达式
		// 如果表达式结果为true，运行上下文中的那个元素就会被添加到结果集中;如果不是，就跳过它
		// 2、collect方法把这个集合转化成一个列表 在后面52页的使用collect方法和Collecters类中，我们会对这个方法进去更深入的探讨
		friends.stream()
		.filter(name -> name.startsWith("N"))
		.collect(Collectors.toList());
		
		final List<String> editors = Arrays.asList("Brian", "Jackie", "John", "Mike");
		final List<String> comrades = Arrays.asList("Kate", "Ken", "Nick", "Paula", "Zach");
		
		
		// 代码冗余 如果想改一下lambda表达式，我们得改不止一处地方
		final long countFriendsStartN = friends.stream()
				.filter(name -> name.startsWith("N"))
				.count();
		final long countEditorsStartN =	editors.stream()
				.filter(name -> name.startsWith("N"))
				.count();
		final long countComradesStartN = comrades.stream()
				.filter(name -> name.startsWith("N"))
				.count();
		
		//把lambda表达式赋值给变量，然后对它们进行重用，就像使用对象一样
		final Predicate<String> startsWithN_p = name -> name.startsWith("N");
		final long countFriendsStartN1 = friends.stream()
				.filter(startsWithN_p)		
				.count();
		final long countEditorsStartN1 = editors.stream()
				.filter(startsWithN_p)
				.count();
		final long countComradesStartN1 = comrades.stream()
				.filter(startsWithN_p)
				.count();
		
		// filter 、count、map、reduce都是Stream类的函数
		
		
	}
}
