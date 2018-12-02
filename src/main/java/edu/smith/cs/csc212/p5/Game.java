package edu.smith.cs.csc212.p5;

import java.util.*;


public class Game {
	
	World world;
	List<Item> inventory = new ArrayList<>();
	
	public Game(int w, int h) {
		world = new World(w,h);
		
		/*
		 * initialize Places  
		 */
		Place busStop = new Place(); 
		Place wood = new Place();
		Place tower = new Place();
		Place village = new Place();
		Place hut = new Place();
		Place hunterHouse = new Place();
		Place attic = new Place();
		Place basement = new Place();
		Place tunnel = new Place();
		Place cemetery = new Place();
		Place church = new Place();
		Place storageRoom = new Place();
		/*
		 * build connections among places
		 */
		busStop.next.addAll(Arrays.asList(wood, village, tower));
		wood.next.addAll(Arrays.asList(hut, hunterHouse));
		tower.next.addAll(Arrays.asList(attic, basement));
		basement.next.add(tunnel);
		village.next.addAll(Arrays.asList(cemetery, church, storageRoom));
		/*
		 * set the starting place of this game 
		 */
		world.startingPlace = busStop;
		
		
	}
}
