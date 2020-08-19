package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AstarSearch {
    
	List<Node> open;
    List<Node> closed;
    List<Node> path;
    
    int[][] maze;
    Node current;
    
    int xstart, ystart;
    int xgoal, ygoal;
   
    boolean diag;
    private static boolean flip;
    int max;

	public AstarSearch(int[][] maze, int xstart, int ystart, boolean diag, boolean flip, int max) {
	
		this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.path = new ArrayList<>();
        
        this.maze = maze;
        
        this.current = new Node(null, xstart, ystart, 0, 0);
        
        this.xstart = xstart;
        this.ystart = ystart;
        
        this.diag = diag;
        this.flip = flip;
        this.max = max;
        
	}
	
	/*
	    ** Finds path to xgoal/ygoal or returns null
	    ** @return (List<Node> | null) the path
	    */
	    public List<Node> findPath(int xgoal, int ygoal) {
	        
	    	this.xgoal = xgoal;
	        this.ygoal = ygoal;
	        
	        
	        this.closed.add(this.current); //Remove start point from fringe.
	        
	        addNeigborsToOpenList(); //Add neighbors to the fringe.
	        
	        int a = 0;
	        
	        while (this.current.x != this.xgoal || this.current.y != this.ygoal) {
	            
	        	if (this.open.isEmpty()) { // Nothing to examine
	                return null;
	            }
	            
	            this.current = this.open.get(0); // get (lowest f score)
	            
	            this.open.remove(0); // remove it
	            this.closed.add(this.current); // and add to the closed
	            
	            //System.out.println(closed.size());
	            addNeigborsToOpenList();
	        }
	        
	        this.path.add(0, this.current);
	        while (this.current.x != this.xstart || this.current.y != this.ystart) {
	            this.current = this.current.parent;
	            this.path.add(0, this.current);
	        }
	        
//	        if(flip==false) {
//
//	        }
//	        else {
//	        	System.out.println("Number of nodes explored (A* Euclidean):"+closed.size());
//	        	System.out.println("Maximum fringe size: "+ max);
//	        }
//	        	
	        
	        return this.path;
	    }
	
	    /*
	     ** Looks in a given List<> for a node
	     **
	     ** @return (bool) NeightborInListFound
	     */
	     private static boolean findNeighbor(List<Node> array, Node node) {
	         return array.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
	     }
	     
	     /*
	      ** Calulate distance between this.current and xgoal/ygoal
	      **
	      ** @return (int) distance
	      */
	      private double manhattanDistance(int dx, int dy) {
	      		int x = Math.abs(this.current.x + dx - this.xgoal);
	      		int y = Math.abs(this.current.y + dy - this.ygoal);
	      		//System.out.println(x+y);
	              return x+y; // else return "Manhattan distance"
	      }
	      
	      
		   /*
		   ** Calulate euclidean distance between this.current and xgoal/ygoal
		   ** @return (int) distance
		   */
	      private double euclideanDistance(int dx, int dy) {
	    		double x = Math.pow((this.current.x + dx - this.xgoal),2);
	    		double y = Math.pow((this.current.y + dy - this.ygoal),2);
	    		double dist = Math.sqrt(x+y);
	    		//System.out.print(dist+" ");
	            return dist;  //Euclidean distance
	    }
	      
	      //add neighbors to openList if they are reachable and not an obstacle...
	      private void addNeigborsToOpenList() {
	          Node node;
	          
	          for (int x = -1; x <= 1; x++) {
	          	
	              for (int y = -1; y <= 1; y++) {
	                  
	              	if (!this.diag && x != 0 && y != 0) {
	                      continue; // skip if diagonal movement is not allowed
	                  }
	              	
	              	if(flip == false) {
	              		node = new Node(this.current, this.current.x + x, this.current.y + y, this.current.g, this.manhattanDistance(x, y));
	              	}else {
	              		node = new Node(this.current, this.current.x + x, this.current.y + y, this.current.g, this.euclideanDistance(x, y));
	              	}
	                  //System.out.println("manhattan");
	                  if ((x != 0 || y != 0) // not this.current
	                      && this.current.x + x >= 0 && this.current.x + x < this.maze[0].length // check maze boundaries
	                      && this.current.y + y >= 0 && this.current.y + y < this.maze.length
	                      && this.maze[this.current.y + y][this.current.x + x] != 1 // check if square is walkable
	                      && !findNeighbor(this.open, node) && !findNeighbor(this.closed, node)) { // if not already done
	                	  
	                          node.g = node.parent.g + 1.0; // Horizontal/vertical cost = 1.0
	                          node.g += maze[this.current.y + y][this.current.x + x]; // add movement cost for this square
	                          int a = open.size();
	                          this.open.add(node);
	                          this.max = Math.max(a, open.size());
	                          
	                  }
	              }
	          }
	          
	          Collections.sort(this.open);
	      }
	      
	      public void print(List<Node> Path, int[][] manhattanMaze, boolean flip) {
	      	
	          if(Path != null) {
	          	  
       
	              
	              //------------------------------------------------------------------------//
	              if(flip==false) {
	              
	             
	              System.out.printf("\nTotal cost Manhattan Distance: %.02f\n", Path.get(Path.size() - 1).g);
	              Path.forEach((n) -> {System.out.print("[" + n.x + ", " + n.y + "] ");
	              manhattanMaze[n.y][n.x] = -1;}); 
	              System.out.println();
		          System.out.println("Number of nodes explored (A* Manhattan):"+closed.size());
		          System.out.println("Maximum fringe size: "+ max);
	              }
	              else {
	              System.out.printf("\nTotal cost Euclidean Distance: %.02f\n", Path.get(Path.size() - 1).g);
	              Path.forEach((n) -> {System.out.print("[" + n.x + ", " + n.y + "] ");
	              manhattanMaze[n.y][n.x] = -1;}); 
	              System.out.println();
			      System.out.println("Number of nodes explored (A* Euclidean):"+closed.size());
			      System.out.println("Maximum fringe size: "+ max);
	              }
	              //-----------------------------------------------------------------------//
	   
	              for (int[] maze_row : manhattanMaze) {
	                  for (int maze_entry : maze_row) {
	                      switch (maze_entry) {
	                          case 0:
	                              System.out.print(" _ ");
	                              break;
	                          case -1:
	                              System.out.print(" * ");
	                              break;
	                          default:
	                              System.out.print(" # ");
	                      }
	                  }
	                  System.out.println();
	              }
	  		//Okay so i have the maze, dimesions, startpoint and endpoint;
	         }else {
		          	System.out.println("NO PATH FOUND!");
	         }
	      }

	
	

}
