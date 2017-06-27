package com.mc.game;

import java.util.ArrayList;

public class Truck {
	private int x, y, speedOffset, speed, direction, destX, destY, startX, destNode, startY, id, node1, node2;
	public boolean isAvailable, isVisible;
	private ArrayList<Integer> trPath;
	Path path;
	
	public Truck(int id) {
		this.id=id;
		x = 0;
		y = 0;
		speedOffset = 0;//if bonus***************************
		speed = 2;
		direction = 2;
		destX = 0;
		destY = 0;
		startX = 0;
		startY = 0;
		isAvailable = true;
		node1 = 0;
		node2 = 0;
		trPath = new ArrayList<Integer>();
		path = new Path();
		destNode = 0;
	}
	
	public void setTruckPath(ArrayList<Integer> newPath) {
		trPath = newPath;
		x = path.getVerticies()[newPath.get(0)].x * 64; //start point is same as end point
		y = path.getVerticies()[newPath.get(0)].y * 64; //AKA the starting post office
		destNode = newPath.get((newPath.size() - 1) / 2);
		destX = path.getVerticies()[destNode].x * 64;
		destY = path.getVerticies()[destNode].y * 64;
		startX = path.getVerticies()[newPath.get(0)].x * 64;
		startY = path.getVerticies()[newPath.get(0)].y * 64;
		isAvailable = false;
	}
	
	//updates truck position by increments of set speed in certain directions
	public void updateTruck() {
		node1 = trPath.get(0); //compares first pair of nodes in the set path list
		node2 = trPath.get(1); //each time the method is run
		direction = path.getDir(node1, node2); //to get direction between the nodes
		
		//depending on direction update start point
		if (direction == 0) {
			y += speed;
		}
		else if (direction == 1) {
			x -= speed;
		}
		else if (direction == 2) {
			x += speed;
		}
		else {
			y -= speed;
		}
		if (x == 64*path.getVerticies()[node2].x && y == 64 * path.getVerticies()[node2].y) {
			trPath.remove(0); //removes first node so next time it compares next 2 nodes
		}
		if (x == startX && y == startY) { //if the truck gets back to postOffice (start pos)
			isAvailable = true; //it is now available to take another order
		}
	}
	
	//checks if the truck has reached the destination that the user wants
	public boolean destCheck() {
		if (x == destX && y == destY) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean getIsAvailable() {
		return isAvailable;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDestNode(){
		return destNode;
	}
	
	public int getDir(){
		return direction;
	}
}