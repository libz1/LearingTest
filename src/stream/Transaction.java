package stream;

public class Transaction {

	public static final String GROCERY = "100";
	public static final String GROCERY1 = "101";
	
	String type;
	Integer value;
	int id;
	
	public Transaction(int id,String type,Integer value){
		this.id = id;
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}
	
	public int getId() {
		return id;
	}

	public Integer getValue() {
		return value;
	}
	
	public String toString(){
		return id+"_"+type+"_"+value;
	}
	

}
