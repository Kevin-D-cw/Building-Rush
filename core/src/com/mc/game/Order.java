package com.mc.game;

import java.util.*;
import java.awt.*;
import com.badlogic.gdx.math.Rectangle;

class Order {
	/*makes sure there are always five orders randomly chosen from customers at any given time
	deletes finished orders from current order list and (updates money everytime it is finished--in main??)
	checks the state of a given customer's order (eg. how many orders out of x are completed and on hold)
	gives a random number from one to seven for each currentOrder customer indicating how many post deliveries they need
	updates time limit value on each currend order*/
	
	private int received, requested, timeLim, price, materialCost;
	private boolean isFinished;
	private Random rand = new Random();
	private Point location;
	private Rectangle area;
	
	public Order(Point location) {
		this.location = location;
		received = 0;
		requested = rand.nextInt(6) + 1;
		timeLim = 30;
		price = rand.nextInt(50) + 50;
		materialCost = 20;
		isFinished = false;
		area = new Rectangle(64*location.x, 64*location.y - 20, 80, 100);
	}
	
	public void updateTime() {
		if (timeLim == 0) {
			isFinished = true;
		}
		else {
			timeLim -= 1;
		}
	}
	
	public void checkCompletion(){
		if (received==requested){
			isFinished = true;
		}
	}
	
	public Point getLocation() {
		return location;
	}
	
	public Rectangle getArea() {
		return area;
	}
	
	public int getRequested() {
		return requested;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getMaterialCost() {
		return materialCost;
	}
	
	public int getReceived() {
		return received;
	}
	
	public void addRecieved(){
		received += 1;
	}
	
	public int getTimeLim() {
		return timeLim;
	}
	
	public void addTime(){
		timeLim+=2;
	}
	
	public boolean getIsFinished() {
		return isFinished;
	}
}