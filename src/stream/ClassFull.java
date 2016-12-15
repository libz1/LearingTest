package stream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fpij.Person;

public class ClassFull {

	public static void main(String[] args) {
		// http://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/
		// http://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/

		// parallelStream 并行流
		
		// 需求：找到 type为 grocery 的所有交易，然后返回以交易值降序排序好的交易 ID 集合
		
		// 1、传统代码实现过程
		List<Transaction> transactions = new ArrayList<>();
		initData(transactions);
		
		List<Transaction> groceryTransactions = new ArrayList<>();
		// 找到type为 grocery 的
		for (Transaction t : transactions) {
			if (t.getType() == Transaction.GROCERY) {
				groceryTransactions.add(t);
			}
		}
		// 以交易值降序排序
		Collections.sort(groceryTransactions, new Comparator<Transaction>() {
			@Override
			public int compare(Transaction o1, Transaction o2) {
				// 注意o2在前，因为是降序
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		// 得到交易 ID集合
		List<Integer> transactionIds = new ArrayList<>();
		for (Transaction t : groceryTransactions) {
			transactionIds.add(t.getId());
		}
		// 打印输出ID
		transactionIds.forEach(System.out::println);
		
		
		// 2、lambda
		// parallelStream并发
		// filter实现过滤
		// sorted实现排序
		// map实现提取数据的一部分
		// forEach遍历
		transactions.parallelStream()
			.filter(t->t.getType()==Transaction.GROCERY)
			.sorted(Comparator.comparing(Transaction::getValue).reversed())
			.map(Transaction::getId).collect(Collectors.toList())
			.forEach(System.out::println);
		
		
		//构造流的几种常见方法
		{
			// 1. Individual values
			Stream stream = Stream.of("a", "b", "c");
			// 2. Arrays
			String [] strArray = new String[] {"a", "b", "c"};
			stream = Stream.of(strArray);
			stream = Arrays.stream(strArray);
			// 3. Collections
			List<String> list = Arrays.asList(strArray);
			stream = list.stream();
			
			//数值流的构造
			{
				IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
				IntStream.range(1, 3).forEach(System.out::println);
				IntStream.rangeClosed(1, 3).forEach(System.out::println);
			}
			
			//流转换为其它数据结构
			{
				// 因为stream只能被消费一次
				// 1. Array
//				String[] strArray1 = (String[]) stream.toArray(String[]::new);
				// 2. Collection
//				List<String> list1 = (List<String>) stream.collect(Collectors.toList());
//				List<String> list2 = (List<String>) stream.collect(Collectors.toCollection(ArrayList::new));
//				Set set1 = (Set) stream.collect(Collectors.toSet());
//				Object stack1 = stream.collect(Collectors.toCollection(Stack::new));
				// 3. String
				String str = stream.collect(Collectors.joining()).toString();				
				System.out.println(str);
			}
			
		}
		
		{
//			流的操作类型分为两种：
//		    	Intermediate：一个流可以后面跟随零个或多个 intermediate 操作。
//						其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，交给下一个操作使用。
//						这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。
//					map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 
//					limit、 skip、 parallel、 sequential、 unordered
//		    	Terminal：一个流只能有一个 terminal 操作，当这个操作执行后，流就被使用“光”了，无法再被操作。所以这必定是流的最后一个操作。Terminal 操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect。
//		    			在对于一个 Stream 进行多次转换操作 (Intermediate 操作)，每次都对 Stream 的每个元素进行转换，而且是执行多次，
//						这样时间复杂度就是 N（转换次数）个 for 循环里把所有操作都做掉的总和吗？
//						其实不是这样的，转换操作都是 lazy 的，多个转换操作只会在 Terminal 操作的时候融合起来，一次循环完成。
//						我们可以这样简单的理解，Stream 里有个操作函数的集合，每次转换操作就是把转换函数放入这个集合中，
//						在 Terminal 操作的时候循环 Stream 对应的集合，然后对每个元素执行所有的函数。
//					forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 
//					count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
//		    	还有一种操作被称为 short-circuiting。用以指：
//		    			对于一个 intermediate操作，如果它接受的是一个无限大（infinite/unbounded）的 Stream，但返回一个有限的新 Stream。
//		    			对于一个 terminal 操作，如果它接受的是一个无限大的 Stream，但能在有限的时间计算出结果。
//	    				当操作一个无限大的 Stream，而又希望在有限时间内完成操作，则在管道内拥有一个 short-circuiting 操作是必要非充分条件。
//					anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit
		}
		
		{
			//map 生成的是个 1:1 映射，每个输入元素，都按照规则转换成为另外一个元素
			List<String> wordList = Arrays.asList("a", "b", "c");
			List<String> output = wordList.stream().
					map(String::toUpperCase).
					collect(Collectors.toList());
			
			List<Integer> nums = Arrays.asList(1, 2, 3, 4);
			List<Integer> squareNums = nums.stream().
			map(n -> n * n).
			collect(Collectors.toList());
			
			//一对多映射关系的，这时需要 flatMap
			//flatMap 把 input Stream 中的层级结构扁平化，就是将最底层元素抽出来放到一起，最终 output 的新 Stream 里面已经没有 List 了，都是直接的数字
			Stream<List<Integer>> inputStream = Stream.of(
					 Arrays.asList(1),
					 Arrays.asList(2, 3),
					 Arrays.asList(4, 5, 6));
			Stream<Integer> outputStream = inputStream.
			flatMap((childList) -> childList.stream());
			outputStream.collect(Collectors.toList())
				.forEach(System.out::println);
		}
		{
			// filter
			Integer[] sixNums = {1, 2, 3, 4, 5, 6};
			Integer[] evens = Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);
			Stream.of(sixNums).filter(n -> n%2 == 0)
				.collect(Collectors.toList()).forEach(System.out::println);
			
			// 首先把每行的单词用 flatMap 整理到新的 Stream，然后保留长度不为 0 的，就是整篇文章中的全部单词了
			String REGEXP = "\\s+";
			try {
				
				// JDK8读文件只需一行代码  
				List<String> output = Files.lines(Paths.get("c:\\config.ini"), StandardCharsets.UTF_8).
						 peek(System.out::println).
						 flatMap(line -> Stream.of(line.split(REGEXP))).
						 filter(word -> word.length() > 0).
						 collect(Collectors.toList());
				output.forEach(System.out::println);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
		{
			// forEach 方法接收一个 Lambda 表达式，然后在 Stream 的每一个元素上执行该表达式
			//		当需要为多核系统优化时，可以 parallelStream().forEach()，只是此时原有元素的次序没法保证，并行的情况下将改变串行时操作的行为
			// 		forEach 是 terminal 操作，因此它执行后，Stream 的元素就被“消费”掉了
			// 具有相似功能的 intermediate 操作 peek 可以达到上述目的
		}
		{
			//findFirst Optional
			
			// Stream 中的 findAny、max/min、reduce 等方法等返回 Optional 值。还有例如 IntStream.average() 返回 OptionalDouble 等等
			String strA = " abcd ", strB = null;
			print(strA);
			print("");
			print(strB);
			getLength(strA);
			getLength("");
			getLength(strB);
		}
		
		{
			//reduce
			//	这个方法的主要作用是把 Stream 元素组合起来。
			//		它提供一个起始值（种子），然后依照运算规则（BinaryOperator），和前面 Stream 的第一个、第二个、第 n 个元素组合。
			//		从这个意义上说，字符串拼接、数值的 sum、min、max、average 都是特殊的 reduce。
			//		例如 Stream 的 sum 就相当于Integer sum = integers.reduce(0, (a, b) -> a+b); 
			//							或Integer sum = integers.reduce(0, Integer::sum);
		
			//第一个参数（空白字符）即为起始值，
			//第二个参数（String::concat）为 BinaryOperator。
			//	这类有起始值的 reduce() 都返回具体的对象。
			//	而对于第四个示例没有起始值的 reduce()，由于可能没有足够的元素，返回的是 Optional
			
			// 字符串连接，concat = "ABCD"
			String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat); 
			// 求最小值，minValue = -3.0
			double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min); 
			// 求和，sumValue = 10, 有起始值
			int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
			// 求和，sumValue = 10, !!!!无起始值  返回的是 Optional!!!
			sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
			// 过滤，字符串连接，concat = "ace"
			concat = Stream.of("a", "B", "c", "D", "e", "F").
			 filter(x -> x.compareTo("Z") > 0).
			 reduce("", String::concat);			
		}
		
		{
			
			//limit/skip
			//对一个 parallel 的 Steam 管道来说，如果其元素是有序的，那么 limit 操作的成本会比较大，
			//		因为它的返回对象必须是前 n 个也有一样次序的元素。
			//		取而代之的策略是取消元素间的次序，或者不要用 parallel Stream
		}
		{
			
			//sorted
			List<Transaction> persons = new ArrayList();
			 for (int i = 1; i <= 5; i++) {
				 Transaction person = new Transaction(i, "name" + i,0);
			 persons.add(person);
			 }
			 // 先进行数量限制，然后进行数据排序  倒序
			List<Transaction> personList2 = persons.stream()
					.limit(2)
					.sorted((p1, p2) -> p2.getType().compareTo(p1.getType()))
					.collect(Collectors.toList());
			System.out.println(personList2);
		}
		{
			//min/max/distinct
			
			// 找出文件中最长一行的长度
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader("c:\\config.ini"));
				int longest = br.lines().
						 mapToInt(String::length).
						 max().
						 getAsInt();
				br.close();
				System.out.println(longest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// 找出全文的单词，转小写，并排序   单词不重复 
			List<String> words;
			try {
				words = Files.lines(Paths.get("c:\\config - 副本.ini"), StandardCharsets.UTF_8).
						 flatMap(line -> Stream.of(line.split(" "))).
						 filter(word -> word.length() > 0).
						 map(String::toLowerCase).
						 distinct().
						 sorted().
						 collect(Collectors.toList());
				System.out.println(words);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		{
			//match
			List<Person> persons = new ArrayList();
			persons.add(new Person(1, "name" + 1, 10));
			persons.add(new Person(2, "name" + 2, 21));
			persons.add(new Person(3, "name" + 3, 34));
			persons.add(new Person(4, "name" + 4, 6));
			persons.add(new Person(5, "name" + 5, 55));
			// 存在18岁以上-完成
			boolean isAllAdult = persons.stream().
					allMatch(p -> p.getAge() > 18);
			System.out.println("All are adult? " + isAllAdult);
			// 存在12岁以下-完成
			boolean isThereAnyChild = persons.stream().
					anyMatch(p -> p.getAge() < 12);
			System.out.println("Any child? " + isThereAnyChild);
		}
		
		{
			//Stream.generate
			
			//生成 10 个随机整数 1
			Random seed = new Random();
			Supplier<Integer> random = seed::nextInt;
			Stream.generate(random).limit(10).forEach(System.out::println);
			
			//生成 10 个随机整数 2
			//Another way
			IntStream.generate(() -> (int) (System.nanoTime() % 100)).
			limit(10).forEach(System.out::println);
			
			// Stream.generate() 还接受自己实现的 Supplier。
			// 例如在构造海量测试数据的时候，用某种自动的规则给每一个变量赋值；或者依据公式计算 Stream 的每个元素值。这些都是维持状态信息的情形。
			Stream.generate(new PersonSupplier()).
				limit(10).
				forEach(p -> System.out.println(p.getName() + ", " + p.getAge()));
		}
		{
			//Stream.iterate
			//iterate 跟 reduce 操作很像，接受一个种子值，和一个 UnaryOperator（例如 f）。
			//		然后种子值成为 Stream 的第一个元素，f(seed) 为第二个，f(f(seed)) 第三个，以此类推

			//生成一个等差数列
			Stream.iterate(0, n -> n + 3).limit(10)
				.forEach(System.out::println);
		}
		
		{
			//用 Collectors 来进行 reduction 操作
			// 按照年龄归组 
			
			// 首先生成 100 人的信息    Stream.generate(new PersonSupplier()).limit(100)
			// 然后按照年龄归组 collect(Collectors.groupingBy(Person::getAge))
			Map<Integer, List<Person>> personGroups = 
					Stream.generate(new PersonSupplier()).limit(100).
					 collect(Collectors.groupingBy(Person::getAge));

			// 遍历map，打印输出 人员及数量信息
			Iterator it = personGroups.entrySet().iterator();
			while (it.hasNext()) {
				 Map.Entry<Integer, List<Person>> persons = (Map.Entry) it.next();
				 System.out.println("Age " + persons.getKey() + " = " + persons.getValue().size());
			}
			
			// 遍历map，打印输出详细信息 
			personGroups.forEach((k,v)->System.out.println("|Item : " + k + " |data : " + v));
					
			// 按照未成年人和成年人归组
			// 在使用条件“年龄小于 18”进行分组后可以看到，不到 18 岁的未成年人是一组，成年人是另外一组。
			//		partitioningBy 其实是一种特殊的 groupingBy，它依照条件测试的是否两种结果来构造返回的数据结构，
			//		get(true) 和 get(false) 能即为全部的元素对象
			Map<Boolean, List<Person>> children = Stream.generate(new PersonSupplier()).
					 limit(100).
					 collect(Collectors.partitioningBy(p -> p.getAge() < 18));
			
					System.out.println("Adult number: " + children.get(false).size());
					System.out.println("Children number: " + children.get(true).size());
			children.forEach((k,v)->System.out.println("|Item : " + k + " |data : " + v));
		}
	}
	
	public static void print(String text) {
		// Java 8
		Optional.ofNullable(text).ifPresent(System.out::println);
		// Pre-Java 8
		if (text != null) {
			System.out.println(text);
		}
	};
	public static int getLength(String text) {
		// 	Java 8
		return Optional.ofNullable(text).map(String::length).orElse(-1);
		// Pre-Java 8
		// return if (text != null) ? text.length() : -1;
	};
	

	private static void initData(List<Transaction> transactions) {
		Transaction t1 = new Transaction(1,Transaction.GROCERY,40);
		transactions.add(t1);
		
		t1 = new Transaction(2,Transaction.GROCERY,101);
		transactions.add(t1);
		
		t1 = new Transaction(3,Transaction.GROCERY1,1);
		transactions.add(t1);
		
		t1 = new Transaction(4,Transaction.GROCERY,2);
		transactions.add(t1);
		
	}

}
