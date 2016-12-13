package fpij;

public class Class6 {
	// 字符串及方法引用  http://www.thinksaas.cn/topics/0/417/417383.html
	
	public static void main(String arg[]){
		final String str = "w0102t";
		
		// Java 8中的一个新方法来遍历字符串  chars
		// 重点是，将一个集合转为stream，然后就可以lambda的方式进行各种数据处理
		
		// chars得到一个stream
		str.chars()
		     .forEach(ch -> System.out.println(ch));
		// 对前面的简化
		str.chars()
	     .forEach(System.out::println);
		// 希望打印输出的是字符，而不是其ASCII数值
		str.chars()
	     .forEach(Class6::printChar);
		
		// mapToObj  我们希望从一开始就处理的就是字符而不是int，可以在调用完chars后直接将int转化成字符
		str.chars()
	     .mapToObj(ch -> Character.valueOf((char)ch))
	     .forEach(System.out::println);
		
		// 使用filter过滤保留其中的isDigit
		str.chars()
	     .filter(ch -> Character.isDigit(ch))
	     .forEach(ch -> printChar(ch));
		
		// 对前面的简化
		str.chars()
	     .filter(Character::isDigit)
	     .forEach(Class6::printChar);
	}
	
	private static void printChar(int aChar) {
	      System.out.println((char)(aChar));
	}

}
