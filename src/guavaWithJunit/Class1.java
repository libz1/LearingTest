package guavaWithJunit;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Ordering;


public class Class1 {
	
	// JDK8与gauva的关系 http://www.tuicool.com/articles/INBzqeb
	
	// Guava是一个很好的库，但是随着Java自身的改进，Guava的很多功能已经变得多余。
	
	// guava ==> com.google.common.base
	
	
	// （JUnit）1、添加@Test注释
	@Test
	public void joinerGuava() {
		List<String> strList = Arrays.asList("one", "two", null, "three");
		String csv = Joiner.on(",")
				.skipNulls()
				.join(strList);
		// （JUnit）2、添加assertEquals语句，对预期的值和实际的值进行判断比较
		assertEquals("one,two,three", csv);
	}
	
	// joinerGuava 和 joinerJava8实现了相同的功能
	
	// （JUnit）1、添加@Test注释
	@Test
	public void joinerJava8() {
		List<String> strList = Arrays.asList("one", "two", null, "three");
		// Objects 是java.util.Objects;
		String csv = strList.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.joining(","));
		// （JUnit）2、添加assertEquals语句，对预期的值和实际的值进行判断比较
		assertEquals("one,two,three", csv);
	}
	
	class Player {
        private String name;
        public String getName() {
            return name;
        }
    }
	
	public void orderingGuava() {
		// public abstract class Ordering<T> implements Comparator<T>
		Ordering<Player> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<Player, String>() {
			@Override
			public String apply(Player foo) {
				return foo.getName();
			}
		});
	}
	
	public void comparingJava8() {
        Comparator<Player> cmp = Comparator.comparing(Player::getName,
                Comparator.nullsFirst(Comparator.naturalOrder()));
    }	
	
	public static void main(String arg[]){
		
		//Joiner
		
		//Ordering
		
	}

}
