package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CellD {

    private int row;
    private int column;
    private int filled;
    private List<CellD> neighbors = new ArrayList<>();

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int isFilled() {
        return filled;
    }

    public void setFilled(int m) {
        this.filled = m;
    }

    public List<CellD> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<CellD> neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CellD cell = (CellD) o;
        return row == cell.row && column == cell.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
