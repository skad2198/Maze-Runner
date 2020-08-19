package src;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Collections;
import java.util.List;

public class env {
	
	public static Cell[][] convertToCell(int[][] maze) {
		Cell[][] a = new Cell[maze.length][maze.length];
		for(int y = 0; y<maze.length; y++) {
			for(int x = 0; x<maze.length; x++) {
				a[y][x] = new Cell(x,y,maze[y][x]);
			}
		}
		return a;
	}

	private static int[][] generateRandomMaze(int[][] maze, int dim, double pGiven) {
		
		for (int i=0; i<dim; i++) {
			for(int j=0; j<dim; j++) {
				double p =  Math.random();
				//System.out.println(p);
				if(p<pGiven) {
					maze[i][j]=1;
				}
			}
		}
		
		maze[0][0]=0;
		maze[dim-1][dim-1]=0;
		
		for (int i=0; i<dim; i++) {
			for(int j=0; j<dim; j++) {
			   System.out.print(" "+maze[i][j]);
			}
			System.out.println();
		}
		
		return maze;	
	}
    
	public static void main(String[] args) throws IllegalArgumentException {
		
		System.out.println("It would be Mazing if you can tell us the dimensions of the Maze :");
		Scanner in = new Scanner(System.in);
		int dim = in.nextInt();
		double pGiven = 0.1;
		System.out.println("How complex do you want your Maze to be (0.1-0.9):");
		pGiven = in.nextDouble();
		
		int maze[][] = new int[dim][dim];
		Cell[][] a = new Cell[dim][dim];
		Cell[][] b = new Cell[dim][dim];
		
		
		maze = generateRandomMaze(maze, dim, pGiven);
		a = convertToCell(maze);
		b = convertToCell(maze);
		
	     //For DFS
		 System.out.println();
	     System.out.println("DEPTH-FIRST SEARCH");
	     MapManager.printInfo();
	     MapManager.createMap(dim, pGiven, maze);
	     MapManager.depthFirstSearch();
		
	     System.out.println("BFS");
		//for BFS
		Cell[] path = BFS.BFSearch(a, dim);
		if (path != null) {
			a = BFS.pathToMaze(a, path);
			BFS.printMap(a, dim);
		}
		System.out.println("BD-BFS");
		// for BD-BFS
		Cell[] pathForbiBFS = BFS.biBFS(b, dim);
		if (pathForbiBFS != null) {
			a = BFS.pathToMaze(b, pathForbiBFS);
			BFS.printMap(b, dim);
		}		
		
		int [][] manhattanMaze = maze;
		int [][] euclideanMaze = maze;
		
		 Instant s = Instant.now();
		   
		   methodTotime();
		   AstarSearch em = new AstarSearch(euclideanMaze, 0, 0, false, true,0);
	       List<Node> EuclideanPath = em.findPath(dim-1, dim-1);
	    
	    Instant f = Instant.now();
	       
	    long time = Duration.between(s, f).toMillis();  //in millis
	    System.out.println("\nTimeTaken A* euclidean: "+time+"ms");
	       
	    Instant start = Instant.now();
	   
	       methodTotime();
		   AstarSearch mm = new AstarSearch(manhattanMaze, 0, 0, false, false,0);
		   List<Node> manhattanPath = mm.findPath(dim-1, dim-1);
		   
	    Instant finish = Instant.now();
	    	 
	    long timeElapsed = Duration.between(start, finish).toMillis();  //in millis
		System.out.println("TimeTaken A* manhattan: "+timeElapsed+"ms");
		
	   em.print(EuclideanPath, euclideanMaze,true);	
       mm.print(manhattanPath, manhattanMaze,false);
       
       
	}

	private static void methodTotime() {
		// TODO Auto-generated method stub
		try {
		      TimeUnit.SECONDS.sleep(3);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    }
		
	}
}
