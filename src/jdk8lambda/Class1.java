package jdk8lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Class1 {

	// http://www.cnblogs.com/GYoungBean/p/4301557.html
	public static void main(String[] args) {
		
		

		// 流操作特别适合对集合进行查询操作。
		// 			假设有一个“音乐库”应用，这个应用里每个库都有一个专辑列表，
		// 			每张专辑都有其名称和音轨列表，每首音轨表都有名称、艺术家和评分。
		// 假设我们需要得到一个按名字排序的专辑列表，
		// 			专辑列表里面的每张专辑都至少包含一首四星及四星以上的音轨，

		// 专辑列表
		List<Album> albums = new ArrayList<>();
		
		Album album1 = new Album("In a Persian Market & Sabre Dance"); 
		albums.add(album1);
		
		Track track = new Track("In a Persian Market(Ketelbey)",5);
		album1.tracks.add(track);
		track = new Track("Sabre Dance(Khachaturian)",5);
		album1.tracks.add(track);
		track = new Track("Ritual Fire Dance(Falla)",4);
		album1.tracks.add(track);
		track = new Track("Hungarian Rhapsody No.2 in C-Sharp Minor(Liszt)",3);
		album1.tracks.add(track);
		
		
		
		Album album2 = new Album("Famous Baroque Melodies"); 
		albums.add(album2);
		track = new Track("Chorale No.4 from the Cantata 'Wachet auf, ruft uns die Stimme', BWV140 (J.S....",4);
		album2.tracks.add(track);
		
		
		// 按名字排序的专辑列表，且每张专辑都至少包含一首四星及四星以上的音轨
		List<Album> favs = new ArrayList<>();

		for (Album a : albums) {
			boolean hasFavorite = false;
			for (Track t : a.tracks) {
				if (t.rating >= 4) {
					hasFavorite = true;
					break;
				}
			}
			if (hasFavorite)
				favs.add(a);
		}
		Collections.sort(favs, new Comparator<Album>() {
			public int compare(Album a1, Album a2) {
				return a1.name.compareTo(a2.name);
			}
		});
		
		// 使用了anyMatch 、sorted、Comparator.comparing
		// anyMatch		Album的track的rating大于等于4的
		// sorted		Comparator.comparing的内容是Album的name
		//		因为list的元素是Album，所以lambda中进行操作的基本元素都是Album，可以进行扩展使用  a.name a.tracks.stream...
		
		System.out.println("-----------------------------------------------------");
		System.out.println("Albums list who have more than one tracks of rating 4+");
		List<Album> sortedFavs =
			    albums.stream()
			          .filter(a -> a.tracks.stream().anyMatch(t -> (t.rating >= 4)))
			          .sorted(Comparator.comparing(a -> a.name))
			          .collect(Collectors.toList());
		sortedFavs.forEach(System.out::println);
		

		System.out.println("-----------------------------------------------------");
		System.out.println("track名称词频分析");
		Pattern pattern = Pattern.compile("\\s+"); // 以非字符进行分隔
		List<Track> tracks = album1.tracks;
		Map<String, Long> wordFreq =
		    tracks.stream()
		    		.peek(System.out::println)
		    		.flatMap(t -> pattern.splitAsStream(t.name))
		    		.collect(Collectors.groupingBy(s -> s.toUpperCase(), Collectors.counting()));
		wordFreq.forEach((k,v)->System.out.println("|Item : " + k + " |Count : " + v));
		
		//sorted(Comparator.comparing(byAge).thenComparing(byTheirName))

		
//		wordFreq.forEach(a-> System.out.println(a));
	}

}
