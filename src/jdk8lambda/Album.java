package jdk8lambda;

import java.util.ArrayList;
import java.util.List;

public class Album {

	public List<Track> tracks;
	protected String name;

	public Album(String name){
		this.name = name;
		tracks = new ArrayList<>();
	}
	
	public List<Track> getTracks() {
		return tracks;
	}
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}



	public String toString(){
		return name;
	}
}
