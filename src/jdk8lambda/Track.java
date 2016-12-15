package jdk8lambda;

public class Track {

	public Track(String name, int rating) {
		this.name = name;
		this.rating = rating;
	}
	protected String name;
	public int rating;

	
	public String toString(){
		return name;
	}
}
