package fpij;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Class5 {

	// MapReduce http://www.thinksaas.cn/topics/0/417/417384.html

	public static void main(String[] arg) {
		// map（映射）和reduce(归约，化简）是数学上两个很基础的概念，它们很早就出现在各类的函数编程语言里了
		// 2003年Google将其发扬光大，运用到分布式系统中进行并行计算后，这个组合的名字才开始在计算机界大放异彩

		List<String> names = Arrays.asList("Brian", "Jackie", "John", "Mike", "Hackie1");

		// 对集合进行归约
		
		// 取得所有人名的字母总数
		// map()方法进行映射 对每个元素，得到对应的元素（长度信息）
		// sum()方法是一个比较常用的reduce操作 ，还有max、min、sorted（排序）、average
		int all = names.stream().mapToInt(name -> name.length()).sum();
		System.out.println(all);

		// 遍历所有的名字，然后打印出名字最长的那个。如果最长的名字有好几个，我们就打印出最开始找到的那个
		// 传给reduce()方法的lambda表达式接收两个参数，name1和name2，它会比较它们的长度，返回最长的那个
		final Optional<String> aLongName = names.stream()
				.reduce((name1, name2) -> name1.length() >= name2.length() ? name1 : name2);

		aLongName.ifPresent(name -> System.out.println(String.format("A longest name: %s", name)));

		// 遍历所有的名字，取名字长度大于"Steve"中最长的
		final String steveOrLonger = names.stream()
				.reduce("Steve", (name1, name2) -> name1.length() >= name2.length() ? name1 : name2);
		System.out.println(steveOrLonger);
		
		// 合并元素
		
		// 在底层实现中，String.join()方法调用了StringJoiner类来将第二个参数传进来的值（这是个变长参数）拼接成一个长的字符串，用第一个参数作为分隔符。这个方法当然不止是能拼接逗号这么简单了
		// 比如说，我们可以传入一堆路径，然后很容易的拼出一个类路径（classpath)，这可真是多亏了这些新增加的方法和类
		System.out.println(String.join(", ", names));
		
		// 使用map方法来进行列表转化了
		// 用filter()方法过滤出我们想要的那些元素
		// reduce操作 :1\可以用reduce()方法将元素拼接成一个字符串
		// 			2\JDK有一个十分方便的collect()方法，它也是reduce()的一个变种，我们可以用它来把元素合并成一个想要的值
		// 在转化后的列表上调用了collect()方法，给它传入了一个joining()方法返回的collector，joining是Collectors工具类里的一个静态方法。
		// collector就像是个接收器，它接收collect传进来的对象，并把它们存储成你想要的格式：ArrayList, String等
		System.out.println(
			      names.stream()
			          .map(String::toUpperCase)
			          .collect(Collectors.joining(", ")));
	}
}
