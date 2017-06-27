package com.mc.game;

import java.util.*;
import java.awt.*;
class Path {
	/*comments 
	*/
	
	private Point[] verticies, edges;
	private ArrayList<Integer>[][] path;
	private int[][] dist;
	private int[] dire;
	private int n, BIG;
	private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	
	public Path() {
		verticies = new Point[] {new Point(5,9), new Point(11,9), new Point(9,9), new Point(12,9), new Point(7,7), 
				new Point(11,5), new Point(14,6), new Point(7,4), new Point(8,5), new Point(13,5), new Point(14,4), 
				new Point(4,3), new Point(8,1), new Point(14,2), new Point(17,1), 
				new Point(15,1), new Point(9,5), new Point(6,1),
				new Point(4,9), new Point(7,9), new Point(4,1), new Point(7,1), new Point(7,5), new Point(14,1), new Point(14,5), new Point(14,9)};

		edges = new Point[] {new Point(0,18), new Point(0,19), new Point(19, 2), new Point(19,4), new Point(2,1), new Point(1,3), new Point(3,25), 
				new Point(25,6), new Point(18,11), new Point(11,20), new Point(20,17), new Point(17,21), new Point(4,22), new Point(22,8), new Point(22, 7), 
				new Point(7,21), new Point(8,16), new Point(16,5), new Point(5,9), new Point(9,24), new Point(6,24), new Point(24,10), new Point(10,13), 
				new Point(13,23), new Point(21,12), new Point(12,23), new Point(23,15), new Point(15,14)};
		
		dire = new int[] {1, 2, 2, 3, 2, 2, 2, 3, 3, 3, 2, 2, 3, 2, 3, 3, 2, 2, 2, 2, 3, 3, 3, 3, 2, 2, 2, 2};

		BIG = 999999;
		n = verticies.length;
		path = new ArrayList[n][n];
		dist = new int[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				path[i][j] = new ArrayList<Integer>();
			}
		}
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				dist[i][j] = BIG;
			}
		}

		for (int i = 0; i < edges.length; i++) {
			int d = (int) verticies[edges[i].x].distance(verticies[edges[i].y]);
			dist[edges[i].x][edges[i].y] = d;
			dist[edges[i].y][edges[i].x] = d;
		}
		
		for (int i = 0; i < n; i++) {
			dist[i][i]= 0;
		}
		
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (dist[i][j] > dist[i][k] + dist[k][j]) {
	            		dist[i][j] = dist[i][k] + dist[k][j];
	            		path[i][j] = new ArrayList<Integer>();
	            		path[i][j].addAll(path[i][k]);
	            		path[i][j].add(k);
	            		path[i][j].addAll(path[k][j]);
					}
				}
			}
		}
	}
    
    public void printPath(ArrayList<Integer>[][] path) {
    	System.out.println(Arrays.deepToString(path));
    }
    
    public void printDist(int[][] dist) {
    	for (int[] row1 : dist){
    		System.out.println(Arrays.toString(row1));
		}
    }
    
    public ArrayList<Integer> getPath(int origin, int dest) {
    	ArrayList<Integer> temp = new ArrayList<Integer>();
    	temp.add(origin);
    	temp.addAll(path[origin][dest]);
    	temp.add(dest);
    	temp.addAll(path[dest][origin]);
    	temp.add(origin);
    	System.out.println(temp.toString());
    	return temp;
        
    }
    
    public int getDir(int origin, int dest) {
    	Point temp = new Point(origin, dest);
    	if (Arrays.asList(edges).contains(temp)) {
    		return dire[Arrays.asList(edges).indexOf(temp)];
    	}
    	else {
    		temp = new Point(dest, origin);
    		return 3 - dire[Arrays.asList(edges).indexOf(temp)];
    	}
    }
    
    public int getDist(int origin, int dest) {
    	return (int) (64 * verticies[origin].distance(verticies[dest]));
    }
    
    public Point[] getVerticies() {
    	return verticies;
    }
}
