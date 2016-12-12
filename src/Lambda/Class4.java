package Lambda;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Class4 {

	// Optional http://www.thinksaas.cn/topics/0/417/417385.html
	public static void main(String arg[]){
		
		// 选出第一个匹配的元素，并且能安全的处理不存在这样一个元素的情况
		List<String> names = Arrays.asList("Brian", "Jackie", "John", "Mike");;
		String startingLetter = "J";
		
		String foundName = null;
		for(String name : names) {
			if(name.startsWith(startingLetter)) {
				foundName = name;
				break;
			}
		}
		
		System.out.println(foundName);
		
		// findFirst返回stream的首个元素
		
		// Optional 不用管结果是不是存在，它使得我们免受空指针异常的烦恼，并且更明确的指明了没有结果也是一种可能的结果。
		// 通过isPresent()方法我们可以知道结果是不是存在，想获取结果值的话可以使用get()方法。
		// 我们还可以使用orElse方法给它设置一个默认值，就像下面代码里的那样
		final Optional<String> opt_foundName = names.stream()
				.filter(name ->name.startsWith(startingLetter))
				.findFirst();

		// 没有找到时刻的返回
		System.out.println(String.format("A name starting with %s: %s",
				startingLetter, opt_foundName.orElse("No name found")));
		// 找到后，可以添加一段代码进行处理
		opt_foundName.ifPresent(name -> System.out.println("Hello " + name));
		
	}
}
