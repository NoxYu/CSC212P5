package edu.smith.cs.csc212.p5;
import java.util.*;

public class Item {

	private String id;
	private String description;
	
	/*
	 * getter methods for id and description
	 */
	public String getID() {
		return id; 
	}
	public String getDescription() {
		return description;
	}
	
	public Item() {
		
	}
	public Item(String id, String description) {
		this.id = id;
		this.description = description;
	}
}
