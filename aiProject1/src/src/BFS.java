package src;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
	
	public static Cell[] BFSearch(Cell[][] a, int dim) {
		Queue<Cell> fringe = new LinkedList<Cell>();
		HashSet<Cell> closed = new HashSet<Cell>();
		
		Cell state = null;
		Cell child = null;
		Cell goal = a[dim-1][dim-1];
		Cell start = a[0][0];
		
		fringe.add(start);
		
		//keeps track of the shortest path
		Cell visited[] = new Cell[dim*dim];     //keeps track of cells visited in order
		int backtracker[] = new int[dim*dim];	//assigns the index of the parent state to each child
		visited[0] = a[0][0];
		
		int i = 1; // visited index, "1" because visited already contains start
		int j = 0; // backtracker index
		
		while(!fringe.isEmpty()) {
			state = fringe.poll();
			
			int x = state.x;
			int y = state.y;
			
			// once the goal is found
			if(state.equals(goal) || fringe.contains(goal)) {
				
				//System.out.println("GOAL FOUND");
				
				/*
				for (int k = 0; k<visited.length; k++) {
					System.out.print(visited[k] + "  ");
				}
				System.out.println();
				for (int k = 0; k<visited.length; k++) {
					System.out.print(" " + backtracker[k] + "       ");
				}
				System.out.println();
				*/
				
				for (int k = 0; k<visited.length; k++) {
					if (visited[k] == goal) i = k;
				}
				
				int pathCellCount = 1;
				//counts number of cells traveled for array
				int temp = i;
				while (temp > 0) {
					temp = backtracker[temp];
					//keeps track of # cells of final path from start to goal
					pathCellCount++;
				}
				//puts the specific cells traveled into an array
				Cell[] finalPath = new Cell[pathCellCount];
				finalPath[pathCellCount-1] = visited[i];
				int index = finalPath.length-2;
				while (i > 0) {
					finalPath[index] = visited[backtracker[i]];
					i = backtracker[i];
					index--;
				}
				
				for (int p = 0; p<finalPath.length; p++) {
					System.out.print(finalPath[p]+" ");
				}
				
				return finalPath;
			}
			//generate children of state
			else if (!closed.contains(state)) {
				// find the index of the current state in the 'visited' array
				for (int k = 0; k<visited.length; k++) {
					if (state == visited[k]) {
						j = k;
					}
				}
				//one Cell down
				if (y<dim-1) {
					child = a[y+1][x];
					if (child.status != 1 && !closed.contains(child) && !fringe.contains(child)) {
						fringe.add(child);
						visited[i] = child;
						backtracker[i] = j;
						i++;						
					}
				}
				//one Cell up
				if (y>0) {
					child = a[y-1][x];
					if (child.status != 1 && !closed.contains(child) && !fringe.contains(child)) {
						fringe.add(child);
						visited[i] = child;
						backtracker[i] = j;
						i++;					
					}
				}
				//one Cell right
				if (x<dim-1) {
					child = a[y][x+1];
					if (child.status != 1 && !closed.contains(child) && !fringe.contains(child)) {
						fringe.add(child);
						visited[i] = child;
						backtracker[i] = j;
						i++;		
					}
				}
				//one Cell left
				if (x>0) {
					child = a[y][x-1];
					if (child.status != 1 && !closed.contains(child) && !fringe.contains(child)) {
						fringe.add(child);
						visited[i] = child;
						backtracker[i] = j;
						i++;
					}
				}
			closed.add(state);
			}
		}
		//System.out.println("GOAL NOT FOUND");
		return null;
	}

	public static Cell[][] pathToMaze(Cell[][] a, Cell[] path) {
		for(int i = 0; i<path.length; i++) {
			a[path[i].y][path[i].x].status = 3;
		}
		
		return a;
	}
	
	public static boolean isIntersecting(Cell[] visitedS, Cell[] visitedG) {
		// returns true or false if the two fringes are intersecting
		HashSet<Cell> temp = new HashSet<Cell>();
		for(int i = 0; visitedS[i] != null; i++) {
			temp.add(visitedS[i]);
		}
		for(int i = 0; visitedG[i] != null; i++) {
			if (temp.contains(visitedG[i])) {
				return true;
			}
		}
		return false;
	}
	
	public static Cell intersectingCell(Cell[] visitedS, Cell[] visitedG) {
		// returns the first common element between two fringes
		HashSet<Cell> temp = new HashSet<Cell>();
		for(int i = 0; visitedS[i] != null; i++) {
			temp.add(visitedS[i]);
		}
		for(int i = 0; visitedG[i] != null; i++) {
			if (temp.contains(visitedG[i])) {
				return visitedG[i];
			}
		}
		return null;
	}
	
	public static Cell[] biBFS(Cell[][] a, int dim) {
		Queue<Cell> fringeS = new LinkedList<Cell>();
		Queue<Cell> fringeG = new LinkedList<Cell>();
		HashSet<Cell> closed = new HashSet<Cell>();
		
		Cell stateS = null;
		Cell stateG = null;
		Cell goal = a[dim-1][dim-1];
		Cell start = a[0][0];

		fringeS.add(start);
		fringeG.add(goal);
		
		//keeps track of the shortest path
		Cell visitedS[] = new Cell[dim*dim];     //keeps track of cells visited in order
		Cell visitedG[] = new Cell[dim*dim]; 
		int backtrackerS[] = new int[dim*dim];	//assigns the index of the parent state to each child
		int backtrackerG[] = new int[dim*dim];
		visitedS[0] = start;
		visitedG[0] = goal;
		
		int iS = 1; // visited index
		int iG = 1;
		int jS = 0; // backtracker index
		int jG = 0;
		
		Cell child = null;
		
		if (start.status == 1 || goal.status == 1) {
			System.out.println("No path found. Start or Goal is Obstructed.");
			return null;
		}
		//while fringe is empty
		while(!fringeS.isEmpty() && !fringeG.isEmpty()) {
			//state = remove from fringe
			stateS = fringeS.poll();
			stateG = fringeG.poll();
			
			int xS = stateS.x;
			int xG = stateG.x;
			
			int yS = stateS.y;
			int yG = stateG.y;
			
			
			if(isIntersecting(visitedS, visitedG)) {
				System.out.println("GOAL FOUND");
				
				// finds the index of the intersectingCell in their respective visited arrays
				for (int i = 0; i<visitedS.length; i++) {
					if (visitedS[i] == intersectingCell(visitedS, visitedG)) iS = i;
				}
				for (int i = 0; i<visitedS.length; i++) {
					if (visitedG[i] == intersectingCell(visitedS, visitedG)) iG = i;
				}
				
				// calculates total space needed for finalPath array
				int pathCellCountS = 0;
				int pathCellCountG = 0; 
				int temp = iS;
				while (temp > 0) {
					temp = backtrackerS[temp];
					pathCellCountS++;
				}
				temp = iG;
				while (temp > 0) {
					temp = backtrackerG[temp];
					pathCellCountG++;
				}
				
				// fits both paths into a final path array
				Cell[] finalPath = new Cell[pathCellCountS + pathCellCountG + 1];
				System.out.println();
				System.out.println("From start: ");
				System.out.println(visitedS[iS]);
				finalPath[pathCellCountS] = intersectingCell(visitedS, visitedG);
				int index = pathCellCountS-1;
				while (iS > 0) {
					System.out.println(visitedS[backtrackerS[iS]]);
					finalPath[index] = visitedS[backtrackerS[iS]];
					index--;
					iS = backtrackerS[iS];
				}
				System.out.println("From goal: ");
				System.out.println(visitedG[iG]);
				index = pathCellCountS + 1;
				while (iG > 0) {
					System.out.println(visitedG[backtrackerG[iG]]);
					finalPath[index] = visitedG[backtrackerG[iG]];
					index++;
					iG = backtrackerG[iG];
				}
				return finalPath;
			}
			
			//generate children from start state
			if (!closed.contains(stateS)) {
				// find the index of the current state in the visited array
				for (int k = 0; k<visitedS.length; k++) {
					if (stateS == visitedS[k]) {
						jS = k;
					}
				}
				//one Cell down
				if (yS<dim-1) {
					child = a[yS+1][xS];
					if (child.status != 1 && !closed.contains(child) && !fringeS.contains(child)) {
						fringeS.add(child);
						if(isIntersecting(visitedS, visitedG)) continue;
						visitedS[iS] = child;
						backtrackerS[iS] = jS;
						iS++;
					}
				}
				//one Cell up
				if (yS>0) {
					child = a[yS-1][xS];
					if (child.status != 1 && !closed.contains(child) && !fringeS.contains(child)) {
						fringeS.add(child);
						if(isIntersecting(visitedS, visitedG)) continue;
						visitedS[iS] = child;
						backtrackerS[iS] = jS;
						iS++;
					}
				}
				//one Cell right
				if (xS<dim-1) {
					child = a[yS][xS+1];
					if (child.status != 1 && !closed.contains(child) && !fringeS.contains(child)) {
						fringeS.add(child);
						if(isIntersecting(visitedS, visitedG)) continue;
						visitedS[iS] = child;
						backtrackerS[iS] = jS;
						iS++;
					}
				}
				//one Cell left
				if (xS>0) {
					child = a[yS][xS-1];
					if (child.status != 1 && !closed.contains(child) && !fringeS.contains(child)) {
						fringeS.add(child);
						if(isIntersecting(visitedS, visitedG)) continue;
						visitedS[iS] = child;
						backtrackerS[iS] = jS;
						iS++;
					}
				}
			closed.add(stateS);
			}
			
			//children from goal state
			if (!closed.contains(stateG)) {
				// find the index of the current state in the visited array
				for (int k = 0; k<visitedG.length; k++) {
					if (stateG == visitedG[k]) {
						jG = k;
					}
				}
				//one Cell down
				if (yG<dim-1) {
					child = a[yG+1][xG];
					if (child.status != 1 && !closed.contains(child) && !fringeG.contains(child)) {
						fringeG.add(child);
						if(isIntersecting(visitedS, visitedG)) continue;
						visitedG[iG] = child;
						backtrackerG[iG] = jG;
						iG++;
					}
				}
				//one Cell up
				if (yG>0) {
					child = a[yG-1][xG];
					if (child.status != 1 && !closed.contains(child) && !fringeG.contains(child)) {
						fringeG.add(child);
						if(isIntersecting(visitedS, visitedG)) continue;
						visitedG[iG] = child;
						backtrackerG[iG] = jG;
						iG++;
					}
				}
				//one Cell right
				if (xG<dim-1) {
					child = a[yG][xG+1];
					if (child.status != 1 && !closed.contains(child) && !fringeG.contains(child)) {
						fringeG.add(child);
						if(isIntersecting(visitedS, visitedG)) continue;
						visitedG[iG] = child;
						backtrackerG[iG] = jG;
						iG++;
					}
				}
				//one Cell left
				if (xG>0) {
					child = a[yG][xG-1];
					if (child.status != 1 && !closed.contains(child) && !fringeG.contains(child)) {
						fringeG.add(child);
						if(isIntersecting(visitedS, visitedG)) continue;
						visitedG[iG] = child;
						backtrackerG[iG] = jG;
						iG++;
					}
				}
			closed.add(stateG);
			}
		}
		System.out.println("GOAL NOT FOUND");
		return null;
	}
	
	public static void printMap(Cell[][] a, int dim) {
		System.out.println();
		for(int y = 0; y<dim; y++) {
			for(int x = 0; x<dim; x++) {
				if (a[y][x].status == 0) System.out.print(" _ ");
				else if (a[y][x].status == 1) System.out.print(" # ");
				else if (a[y][x].status == 3) System.out.print(" * ");
				
			}
			System.out.println();
		}
	}

}

