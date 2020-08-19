package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import src.CellD;

public class MapManager {

    private static CellD[][] map;
    private static int steps = 0;

    private MapManager() {
    }
    
    public static void printInfo() {
        System.out.println(" _ empty");
        System.out.println(" # filled");
        System.out.println(" * path");
    }

    public static void printMap() {
        printMap(Collections.emptyList());
    }

    public static void printMap(List<CellD> path) {
        StringBuilder builder = new StringBuilder();
        for (CellD[] cells : map) {
            builder.append('\n');
            for (CellD cell : cells) {
                if (path.contains(cell)) {
                    builder.append(" * ");
                } else if (cell.isFilled()==0) {
                    builder.append(" _ ");
                } else {
                    builder.append(" # ");
                }
            }
        }
        System.out.println(builder);
    }

    public static void createMap(int dimension, double probability, int[][] maze) {
        
    	int m[][] = maze;
    	map = new CellD[dimension][dimension];
    	
        for (int row = 0; row < dimension; row++) {
            for (int column = 0; column < dimension; column++) {
            	
                map[row][column] = new CellD();
                map[row][column].setRow(row);
                map[row][column].setColumn(column);

                boolean start = (row == 0 && column == 0);
                boolean goal = (row == (dimension - 1) && column == (dimension - 1));
                if (!start && !goal) {
                	
                    map[row][column].setFilled(m[row][column]);
                }
            }
        }
        addNeighbors();
        printMap();
    }

    private static void addNeighbors() {
        for (CellD[] cells : map) {
            for (CellD cell : cells) {
                if (cell.isFilled() == 1) {
                    continue;
                }
                if (cell.getRow() < (map.length - 1)) {
                    addNeighbor(cell, map[(cell.getRow() + 1)][cell.getColumn()]);
                }
                if (cell.getColumn() < (map.length - 1)) {
                    addNeighbor(cell, map[cell.getRow()][(cell.getColumn() + 1)]);
                }
                if (cell.getRow() > 0) {
                    addNeighbor(cell, map[(cell.getRow() - 1)][cell.getColumn()]);
                }
                if (cell.getColumn() > 0) {
                    addNeighbor(cell, map[cell.getRow()][(cell.getColumn() - 1)]);
                }   
            }   
        }
    }

    private static void addNeighbor(CellD cell, CellD neighbor) {
        if (neighbor.isFilled()==0) {
            cell.getNeighbors().add(neighbor);
        }
    }

    public static void depthFirstSearch() {
        List<CellD> path = depthFirstSearch(map[0][0], new ArrayList<>(), new ArrayList<>());
        if (path.isEmpty()) {System.out.println("\nNO PATH FOUND");}
        else {
        	System.out.println("\nPATH FOUND");
        	printMap(path);
        	
        }
    }

    private static List<CellD> depthFirstSearch(CellD cell, List<CellD> explored, List<CellD> subpath) {
    	explored.add(cell);
        if (cell.getRow() == (map.length - 1) && cell.getColumn() == (map.length - 1)) {
            subpath.add(cell);
            return subpath;
        }
        for (CellD neighbor : cell.getNeighbors()) {
            if (!explored.contains(neighbor)) {
                subpath.add(cell);
                List<CellD> path = depthFirstSearch(neighbor, explored, subpath);
                if (!path.isEmpty()) {
                    return path;
                } else {
                    subpath.remove(cell);
                }
            }
        }
        return Collections.emptyList();
    }

   
}
