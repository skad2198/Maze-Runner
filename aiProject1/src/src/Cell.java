package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell {
	int x;
	int y;
	int status;
	
	public Cell(int x, int y, int status) {
		this.x = x;
		this.y = y;
		this.status = status;
	}

	public String toString() {
		return "(" + y + ", " + x + ")" + status;
	}
	
	public String toStringStatus() {
		return status + "";
	}
  
}
