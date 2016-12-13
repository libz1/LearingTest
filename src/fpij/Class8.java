package fpij;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Class8 {

	public static void main(String[] args) {
		try {

			// filter 过滤保留目录类型数据
			Files.list(Paths.get(".\\src\\Lambda")).filter(Files::isDirectory).forEach(System.out::println);

			// path -> path.toString().endsWith(".java") 保留.java结束的内容
			Files.newDirectoryStream(Paths.get(".\\src\\Lambda"), path -> path.toString().endsWith(".java"))
					.forEach(System.out::println);

//			 final File[] files = new File(".").listFiles(file ->
//			 file.isHidden());
			// new File(".").listFiles(File::isHidden);

			// 590946688045

			
			// 遍历指定目录下的子目录
			// The flatMap() returns a flattened map of a collection of all the children of the current directory’s subdirectories
			
			// listFiles是File[] 
			List<File> files = Stream.of(new File(".").listFiles())
					.flatMap(file -> file.listFiles() == null ? Stream.of(file) : Stream.of(file.listFiles()))
					.collect(Collectors.toList());
			System.out.println("example of flatMap:");
			files.forEach(System.out::println);
			
			// 将数组转为list 使用Stream.of函数+ .collect(Collectors.toList())组合
			String[] testArray = {"a","b","c"};
			List<String> list = Stream.of(testArray)
					.collect(Collectors.toList());
			
			list.forEach(System.out::println);
//			System.out.println("Count: " + files.size());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
