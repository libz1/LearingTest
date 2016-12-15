package jdk8lambda;

import java.util.Arrays;
import java.util.Comparator;

import fpij.Person;



public class Class2 {
	
	// http://zh.lucida.me/blog/java-8-lambdas-insideout-language-features/
	
	Runnable r1 = () -> { System.out.println(this); };
	public String toString() {  return "Hello, this is Class2"; }
	
	public static void main(String[] args) {
		new Class2().r1.run();
		
		// 排序
		Album[] album =  new Album[]{};
		Comparator<Album> byName = Comparator.comparing(Album::getName);
		Arrays.sort(album, byName);
		

//		album.sort(comparing(Person::getLastName).reversed());;
		
//		Java SE 7 中已经存在的函数式接口：
//	    java.lang.Runnable
//	    java.util.concurrent.Callable
//	    java.security.PrivilegedAction
//	    java.util.Comparator
//	    java.io.FileFilter
//	    java.beans.PropertyChangeListener
		
//		Java SE 8中增加了一个新的包：java.util.function，它里面包含了常用的函数式接口，例如：
//	    Predicate<T>——接收 T 并返回 boolean
//	    Consumer<T>——接收 T，不返回值
//	    Function<T, R>——接收 T，返回 R
//	    Supplier<T>——提供 T 对象（例如工厂），不接收值
//	    UnaryOperator<T>——接收 T 对象，返回 T
//	    BinaryOperator<T>——接收两个 T，返回 T
		
//		lambda表达式：
//		(int x, int y) -> x + y;
//		() -> 42
//		(String s) -> { System.out.println(s); }
//		第一个 lambda 表达式接收 x 和 y 这两个整形参数并返回它们的和
//		第二个 lambda 表达式不接收参数，返回整数 ‘42’
//		第三个 lambda 表达式接收一个字符串并把它打印到控制台，不返回值
		
		
	}

}
