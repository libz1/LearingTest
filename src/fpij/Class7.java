package fpij;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Class7 {

	// comparator http://www.thinksaas.cn/topics/0/417/417382.html
	public static void main(String arg[]) {

		final List<Person> people = Arrays.asList(new Person("John", 20), new Person("Sara", 21),
				new Person("Jane", 21), new Person("Greg", 35));

		// 按照人员的年龄进行排序   在对象中添加了一个ageDifference方法， 入参为对象，进行两个对象的年龄差距计算
		//public int ageDifference(final Person other) {
		//	return age - other.age;
		//}
		// 因为要按年龄进行排序，所以我们会比较两个对象的年龄，然后返回比较的结果ageDifference
		// sorted()方法会遍历目标集合的每个元素并调用指定的Comparator，来确定出元素的排序顺序
		// reduce()方法把列表逐步归约出一个结果。而sorted()方法则通过比较的结果来进行排序。
		List<Person> ascendingAge = people.stream()
				.sorted((person1, person2) -> person1.ageDifference(person2))
				.collect(Collectors.toList());
		
		// 对前面lambda的简化
		// 编译器接收到两个person实例的参数，把第一个用作ageDifference()方法的调用目标，而第二个作为方法参数。
		// 我们让编译器去做这个工作，而不是自己直接去写代码。
		// 当使用这种方式的时候，我们必须确定第一个参数就是引用的方法的调用目标，而剩下那个就是方法的入参 ?
		ascendingAge = people.stream()
				.sorted(Person::ageDifference)
				.collect(Collectors.toList());
		
		// 倒序进行排序
		// 我们把要比较的人调了下顺序。结果应该是按他们的年龄由从大到小排列的
		ascendingAge = people.stream()
				.sorted((person2, person1) -> person1.ageDifference(person2))
				.collect(Collectors.toList());
		
		// 构建Comparator对象，  使用reversed进行顺序调整
		Comparator<Person> compareAscending =
				(person1, person2) -> person1.ageDifference(person2);
				
		Comparator<Person> compareDescending = compareAscending.reversed();
		
		ascendingAge = people.stream()
				.sorted(compareAscending)
				.collect(Collectors.toList());
		
		ascendingAge = people.stream()
				.sorted(compareDescending)
				.collect(Collectors.toList());
		
		Comparator<Person> compareByName =
				(person1, person2) -> person1.getName().compareTo(person2.getName());
		
		// 按照姓名进行排序
		ascendingAge = people.stream()
				.sorted(compareByName)
				.collect(Collectors.toList());

		// 选出列表中最年轻的人来  min方法的入参是ageDifference
		// 得到的结果是optional，因为可能有多个
		people.stream()
			.min(Person::ageDifference)
			.ifPresent(youngest -> System.out.println("Youngest: " + youngest));
		
		// 多重比较  先进行年龄的比较，然后按照姓名比较
		// Comparator.comparing  thenComparing 进行比较
		final Function<Person, Integer> byAge = person -> person.getAge();
		final Function<Person, String> byTheirName = person -> person.getName();
		ascendingAge = people.stream()
			.sorted(Comparator.comparing(byAge).thenComparing(byTheirName))
			.collect(Collectors.toList());

		// 打印输出排序结果
		ascendingAge.forEach(System.out::println);
		
		
		// collect http://www.thinksaas.cn/topics/0/417/417381.html
		
		// collect()的第一个参数是一工厂或者生产者，后面的参数是一个用来收集元素的操作 
		List<Person> olderThan20 = people.stream()
					.filter(person -> person.getAge() > 20)
					.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

		// 对前面处理过程的简化
		// 除了toList()方法，还有toSet()方法，可以添加到一个Set中
		//		toMap()方法可以用来收集到一个key-value的集合中
		//		还有joining()方法，可以拼接成一个字符串
		//		我们还可以将mapping(),collectingAndThen()，minBy(), maxBy()和groupingBy()等方法组合起来进行使用
		olderThan20 = people.stream()
				.filter(person -> person.getAge() > 20)
				.collect(Collectors.toList());
		System.out.println("People older than 20: " + olderThan20);
		
		// Person对象按年龄进行分组
		// groupingBy()接受一个lambda表达式或者方法引用——这种叫分类函数——它返回需要分组的对象的某个属性的值
		// 简单的groupingBy()方法使用了分类器进行元素收集。
		//		而通用的groupingBy()收集器，则可以为每一个分组指定一个收集器。也就是说，元素在收集的过程中会途经不同的分类器和集合，下面我们将会看到
		Map<Integer, List<Person>> peopleByAge = people.stream()
					.collect(Collectors.groupingBy(Person::getAge));
		System.out.println("Grouped by age: " + peopleByAge);
		
		// 人们的名字String已经按年龄进行分组了
		// 这个版本的groupingBy()接受两个参数：
		//		第一个是年龄，这是分组的条件，Person::getAge
		//		第二个是一个收集器，它是由mapping()函数返回的结果。 Collectors.mapping(Person::getName, Collectors.toList())
		//			mapping()方法接受两个参数，一个是映射用的属性，Person::getName
		//									一个是对象要收集到的地方，比如说list或者set，Collectors.toList()
		Map<Integer, List<String>> nameOfPeopleByAge =
				people.stream()
				.collect(Collectors.groupingBy(Person::getAge, 
								Collectors.mapping(Person::getName, Collectors.toList())));
		System.out.println("People grouped by age: " + nameOfPeopleByAge);		
		
		// 按名字的首字母进行分组，然后选出每个分组中年纪最大的那位
		// 先是按名字的首字母（person.getName().charAt(0)）进行了排序。
		//		为了实现这个，我们把一个lambda表达式作为groupingBy()的第一个参数传了进去
		// 第二个参数不再是mapping()了，而是执行了一个reduce操作
		//		在每个分组内，它使用maxBy()方法，从所有元素中递推出最年长的那位
		Comparator<Person> byAge1 = Comparator.comparing(Person::getAge);
		Map<Character, Optional<Person>> oldestPersonOfEachLetter =	people.stream()
				.collect(Collectors.groupingBy(person -> person.getName().charAt(0),
							Collectors.reducing(BinaryOperator.maxBy(byAge1))));
		System.out.println("Oldest person of each letter:");
		System.out.println(oldestPersonOfEachLetter);
	}
}
