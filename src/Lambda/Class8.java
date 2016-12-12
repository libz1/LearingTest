package Lambda;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Class8 {
	
	public static void main(String[] args ){
		try {
			
			//  filter 过滤保留目录类型数据
			Files.list(Paths.get(".\\src\\Lambda"))
				.filter(Files::isDirectory)
			 	.forEach(System.out::println);
			
			// path -> path.toString().endsWith(".java") 保留.java结束的内容
			Files.newDirectoryStream(
				      Paths.get(".\\src\\Lambda"), path -> path.toString().endsWith(".java"))
				     .forEach(System.out::println);
			
			final File[] files = new File(".").listFiles(file -> file.isHidden());
			new File(".").listFiles(File::isHidden);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
