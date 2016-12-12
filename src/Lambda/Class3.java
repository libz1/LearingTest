package Lambda;

import java.util.function.BinaryOperator;

public class Class3 {
	
	public static void main(String[] arg){
		BinaryOperator<Long> add = (x,y) -> x +y;
		Long x = (long)1, y=(long) 1;
		//add(x,y);
	}

}
