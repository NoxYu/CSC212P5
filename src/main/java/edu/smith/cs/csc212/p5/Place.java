package edu.smith.cs.csc212.p5;

import java.util.*;

public class Place {
	/**
	 * a place knows what other places it is connected to 
	 */
	List<Place> next = new ArrayList<>();
	List<Item> items = new ArrayList<>();
	
	public Place() {
	}
	
	public Place(List<Place> next){
		this.next = next;
	}
}
