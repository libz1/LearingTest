package ifever;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

public class Class2 {

	// Stream ，Lambda的好基友
	
	public static void main(String[] arg){
		// Stream是元素的集合，这点让Stream看起来用些类似Iterator；
		// 可以支持顺序和并行的对原Stream进行汇聚的操作；
		
		List<Integer> nums = Lists.newArrayList(1,null,3,4,null,6);
		nums.stream()
			.filter(num -> num != null)
			.count();
		
//		在此我们总结一下使用Stream的基本步骤：
//			1、创建Stream；
//	    	2、转换Stream，每次转换原有Stream对象不改变，返回一个新的Stream对象（**可以有多次转换**）；
//	    	3、对Stream进行聚合（Reduce）操作，获取想要的结果；
		
		// 2. 创建Stream
		// 2.1 使用Stream静态方法来创建Stream
		// 1.1 of方法
		Stream<Integer> integerStream = Stream.of(1, 2, 3, 5);
		Stream<String> stringStream = Stream.of("taobao");
		// 1.2 generator方法
		// 生成一个无限长度的Stream，其中值是随机的。这个无限长度Stream是懒加载，一般这种无限长度的Stream都会配合Stream的limit()方法来用
		// 这个无穷的Stream不能直接调用forEach()，会无限下去
		// 		但是我们可以利用limit()变换，把这个无穷Stream变换为有限的Stream
		Stream.generate(() -> Math.random());
		Stream.generate(Math::random);
		
		usingGenerator();
		
		// 1.3 iterate方法
		// 也是生成无限长度的Stream，和generator不同的是，
		// 		其元素的生成是重复对给定的种子值(seed)调用用户指定函数来生成的。
		//		其中包含的元素可以认为是：seed，f(seed),f(f(seed))无限循环
		Stream.iterate(1, item -> item + 2).limit(10).forEach(System.out::println);
		
		//2.2 通过Collection子类获取Stream  
		List<Integer> nums2 = Lists.newArrayList(1,null,3,4,null,6);
		nums2.stream();
		
		
		//3. 转换Stream   http://ifeve.com/stream/
		// distinct、filter、map、flatMap、peek、limit、skip
		//	flatMap和map类似，不同的是其每个元素转换得到的是Stream对象，会把子Stream中的元素压缩到父集合中
		//	生成一个包含原Stream的所有元素的新Stream，同时会提供一个消费函数（Consumer实例），新Stream每个元素被消费的时候都会执行给定的消费函数
		//
		System.out.println("在一起,在一起...");
		List<Integer> nums3 = Lists.newArrayList(1,1,null,2,3,4,null,5,6,7,8,9,10);
		System.out.println("sum is:"+
				nums3.stream()
					.filter(num -> num != null)
					.distinct()
		            .mapToInt(num -> num * 2)
		            .peek(System.out::println)
		            .skip(2)
		            .limit(4)
		            .sum());
		
		// peek(System.out::println)在每个元素被消费的时候打印自身
		
		//4. 汇聚（Reduce）Stream
		// 4.1 可变汇聚
		List<Integer> numsWithoutNull = nums.stream()
				.filter(num -> num != null)
				.collect(Collectors.toList());
		
		// 4.2 其他汇聚
		List<Integer> ints = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10);
		// reduce  (有两个参数)
		//		第一个参数是上次函数执行的返回值（也称为中间结果）
		//		第二个参数是stream中的元素，这个函数把这两个值相加，得到的和会被赋值给下次执行这个函数的第一个参数
		//		第一次执行的时候第一个参数的值是Stream的第一个元素，第二个参数是Stream的第二个元素
		System.out.println("ints sum is:" + 
				ints.stream()
					.reduce((sum, item) -> sum + item)
					.get());
		
		// count
		System.out.println("ints sum is:" + ints.stream().count());
		
		// allMatch、anyMatch、findFirst、noneMatch、max\min
		// ifPresent是Optional类的属性，防止数据为空的处理过程
		System.out.println(ints.stream().allMatch(item -> item < 100));
		ints.stream()
			.max((o1, o2) -> o1.compareTo(o2))
			.ifPresent(System.out::println);
		
	}
	
	private static void usingGenerator(){
		class FibonacciSupplier implements Supplier<Long> {
		    long a = 0;
		    long b = 1;
		    @Override
		    public Long get() {
		        long x = a + b;
		        a = b;
		        b = x;
		        return a;
		    }
		}

		// 使用流的时候应当格外注意。它只能消费一次  下面创建了两次generate，就是这个原因
        // fibonacci 取前10个
        Stream<Long> fibonacci1 = Stream.generate(new FibonacciSupplier());
        List<Long> list1 = fibonacci1.limit(10).collect(Collectors.toList());
        list1.forEach(System.out::println);
		
        // 取20到30 
        Stream<Long> fibonacci2 = Stream.generate(new FibonacciSupplier());
        List<Long> list2 = fibonacci2.skip(10).limit(10).collect(Collectors.toList());
        list2.forEach(System.out::println);
		
	}
}
