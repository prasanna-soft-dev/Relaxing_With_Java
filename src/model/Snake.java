package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Snake {
    private List<Segment> segments;
    private Direction direction;

    public Snake(){
        segments = new ArrayList<>();
        this.direction = Direction.RIGHT;

        addSegment(12, 15);
        addSegment(12, 14);
        addSegment(12, 13);
        addSegment(12, 12);
    }

    public void addSegment(int row, int col){
        segments.add(new Segment(row,col));
    }

    public List<Segment> getSegments(){
        return Collections.unmodifiableList(segments);
    }

    public Direction getDirection(){
        return direction;
    }

    public void setDirection(Direction direction){
        if(this.direction == Direction.RIGHT && direction == Direction.LEFT){
            return;
        }

        if(this.direction == Direction.UP && direction == Direction.DOWN){
            return;
        }

        if(this.direction == Direction.LEFT && direction == Direction.RIGHT){
            return;
        }

        if(this.direction == Direction.DOWN && direction == Direction.UP){
            return;
        }
        this.direction = direction;
    }


    public void move(boolean grow) {

        int headRow = segments.get(0).getRow();
        int headCol = segments.get(0).getCol();

        // Save the tail's old position
        Segment tail = segments.get(segments.size() - 1);
        int tailRow = tail.getRow();
        int tailCol = tail.getCol();

        // Shift every segment normally
        for (int i = segments.size() - 1; i > 0; i--) {
            segments.get(i).setPosition(
                    segments.get(i - 1).getRow(),
                    segments.get(i - 1).getCol()
            );
        }

        // Move the head
        Direction nextDirection = getDirection();

        Segment head = segments.get(0);

        if (nextDirection == Direction.UP) {
            head.setPosition(headRow - 1, headCol);
        } else if (nextDirection == Direction.DOWN) {
            head.setPosition(headRow + 1, headCol);
        } else if (nextDirection == Direction.LEFT) {
            head.setPosition(headRow, headCol - 1);
        } else {
            head.setPosition(headRow, headCol + 1);
        }

        // Add a new segment at the old tail position
        if (grow) {
            segments.add(new Segment(tailRow, tailCol));
        }
    }

    public void reset() {
        segments.clear();

        direction = Direction.RIGHT;

        addSegment(12, 15);
        addSegment(12, 14);
        addSegment(12, 13);
        addSegment(12, 12);
    }

}
