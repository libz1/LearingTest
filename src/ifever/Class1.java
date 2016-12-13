package ifever;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class Class1 {

	public static void main(String arg[]) {

		// 没用stream的lambda表达式的例子1
		List<String> names = Arrays.asList("a", "f", "c", "d");
		Collections.sort(names, (o1, o2) -> o1.compareTo(o2));
		names.forEach(System.out::print);
		System.out.println("--------next example--------");
		
		// List定义时，必须有属性信息<String>
		List<String> name = Arrays.asList("a","m","d","c","r","b");
		Collections.sort(name, (o1, o2) -> o1.compareTo(o2));
		name.stream()
			.map(n -> n.toUpperCase())
			.collect(Collectors.toList())
			.forEach(System.out::print);
		
		name.stream()
			.map(String::toLowerCase)
			.collect(Collectors.toList())
			.forEach(System.out::print);
		
		System.out.println("--------next example--------");

		// 例子1修改为标准的匿名内部类
		Collections.sort(names, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		
		// map中的lambda表达式(Strings.padEnd(item, i, '@'))访问外部变量Integer i。并且可以访问外部变量是lambda表达式的一个重要特性
		
		// 以前java的匿名内部类在访问外部变量的时候，外部变量必须用final修饰。
		// 			在java8对这个限制做了优化，可以不用显示使用final修饰，但是编译器隐式当成final来处理
		String[] array = {"a", "b", "c"};
		
		// Lists和Strings都是gauva工程包中的内容
		// guava项目 参考http://ifeve.com/google-guava/
		// gauva 是java API蛋糕上的冰激凌（精华）
		// 它提供了大量相关的应用类，集合，多线程，比较，字符串，输入输出，缓存，网络，原生类型，数学，反射等等
		// 百分百的单元测试，被很多的项目使用，帮助开发者专注业务逻辑而不是写java应用类
		
		
		
		// Lists.newArrayList构建List
		// Strings.padEnd(YYY,M,"X")在字符YYY后添加N个"X"，总共M个字符
		for(Integer i : Lists.newArrayList(1,2,3)){
			Stream.of(array)
		  		.map(item -> Strings.padEnd(item, i, '@'))
			  	.forEach(System.out::println);
			}
	}
}
