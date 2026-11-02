package model;

public class Food {
    private Segment position;

    public Food(int x, int y){
        this.position = new Segment(x,y);
    }

    public Segment getPosition() {
        return position;
    }

    public void setPosition(int  x, int y) {
        position.setPosition(x, y);
    }
}
