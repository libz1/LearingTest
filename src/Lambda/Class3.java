package Lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class Class3 {

	// 高阶函数
	public static Predicate<String> checkIfStartsWith(final String letter) {
		return name -> name.startsWith(letter);
	}

	public static void main(String[] arg) {
		// 如何使用如下的这个lambda表达式？
		BinaryOperator<Long> add = (x, y) -> x + y;
		Long x = (long) 1, y = (long) 1;
		// add(x,y);

		final List<String> editors = Arrays.asList("Brian", "Jackie", "John", "Mike");
		final List<String> comrades = Arrays.asList("Kate", "Ken", "Nick", "Paula", "Zach");
		// 查找N开头的
		final Predicate<String> startsWithN_p1 = name -> name.startsWith("N");
		// 查找B开头的
		final Predicate<String> startsWithN_p2 = name -> name.startsWith("B");
		final long countFriendsStartN1 = editors.stream().filter(startsWithN_p1).count();
		final long countEditorsStartB1 = comrades.stream().filter(startsWithN_p2).count();

		// 使用词法作用域来避免冗余

		// 1、通过创建了一个高阶函数（checkIfStartsWith）并且使用了词法作用域
		// 我们成功的去除了代码中的冗余。我们不用再重复的判断name是不是以某个字母开头了
		
		// Predicate接受一个类型为T的参数，返回一个boolean值来代表它对应的判断条件的真假。
		// 当我们需要做条件判断的时候，我们可以使用Predicateg来完成。像filter这类对元素进行筛选的方法都接收Predicate作为参数
		
		final long countFriendsStartN2 = editors.stream().filter(checkIfStartsWith("N")).count();
		final long countFriendsStartB2 = comrades.stream().filter(checkIfStartsWith("B")).count();

		
		// 2、 入参是String，出参是Predicate的Function
		
		// Funciton代表的是一个函数，它的入参是类型为T的变量，返回的是R类型的一个结果。
		// 它和只能返回boolean的Predicate相比要更加通用。
		// 只要是将输入转化成一个输出的，我们都可以使用Function，因此map使用Function作为参数也是情理之中的事情了
		
		// 第1次 构造
		final Function<String, Predicate<String>> startsWithLetter1 = (String letter) -> {
			Predicate<String> checkStarts = (String name) -> name.startsWith(letter);
			return checkStarts;
		};

		// 第2次 构造
		final Function<String, Predicate<String>> startsWithLetter2 = 
				( String letter) -> (String name) -> name.startsWith(letter);
		// 第3次 构造		
		final Function<String, Predicate<String>> startsWithLetter3 =
				letter -> name -> name.startsWith(letter);
			
		final long countFriendsStartN3 = editors.stream()
				.filter(startsWithLetter3.apply("N")).count();
		final long countFriendsStartB3 = comrades.stream()
				.filter(startsWithLetter3.apply("B")).count();				
	}

}
