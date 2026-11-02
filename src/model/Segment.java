package model;

public class Segment{
    private int row;
    private int col;

    public Segment(int row, int col){
        this.row = row;
        this.col = col;
    }

    public void setPosition(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
}
