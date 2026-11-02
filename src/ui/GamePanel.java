package ui;

import model.Direction;
import model.Food;
import model.Segment;
import model.Snake;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.*;
import java.util.Random;


public class GamePanel  extends JPanel {
    private final Snake snake = new Snake();
    private InputMap inputMap;
    private ActionMap actionMap;
    private Timer timer;
    private Random rand = new Random();
    private Food food;
    public Segment candidate;
    public GamePanel() {
        this.inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        this.actionMap = getActionMap();
        setPreferredSize(new Dimension(825, 625));
        setBackground(Color.BLACK);
        KeyStroke keyStroke = KeyStroke.getKeyStroke("UP");

        inputMap.put(keyStroke, "MOVE_UP");
        actionMap.put("MOVE_UP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                snake.setDirection(Direction.UP);
            }
        });

        keyStroke = KeyStroke.getKeyStroke("DOWN");
        inputMap.put(keyStroke, "MOVE_DOWN");
        actionMap.put("MOVE_DOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                snake.setDirection(Direction.DOWN);
            }
        });

        keyStroke = KeyStroke.getKeyStroke("LEFT");
        inputMap.put(keyStroke,  "MOVE_LEFT");
        actionMap.put("MOVE_LEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                snake.setDirection(Direction.LEFT);
            }
        });

        keyStroke = KeyStroke.getKeyStroke("RIGHT");
        inputMap.put(keyStroke,   "MOVE_RIGHT");
        actionMap.put("MOVE_RIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                snake.setDirection(Direction.RIGHT);
            }
        });

        this.timer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                Segment head = snake.getSegments().get(0);
                Segment foodPosition = food.getPosition();

                int nextRow = head.getRow();
                int nextCol = head.getCol();

                switch (snake.getDirection()) {
                    case UP -> nextRow--;
                    case DOWN -> nextRow++;
                    case LEFT -> nextCol--;
                    case RIGHT -> nextCol++;
                }

                if(nextRow < 0 || nextCol < 0 || nextRow > 24 || nextCol > 32) {
                    timer.stop();
                }

                List<Segment> segments = snake.getSegments();
                for(int i = 1;i < segments.size();i++) {
                    Segment cur = segments.get(i);
                    if(cur.getRow() == nextRow && cur.getCol() == nextCol) {
                        timer.stop();
                    }

                }
                boolean ateFood = nextRow == foodPosition.getRow()
                        && nextCol == foodPosition.getCol();
                snake.move(ateFood);

                if (ateFood) {
                    generateFood();
                    food = new Food(candidate.getRow(), candidate.getCol());
                }

                repaint();
                for (Segment segment : snake.getSegments()) {
                    System.out.println(segment.getRow() + ", " + segment.getCol());
                }
                System.out.println("-----");
                repaint();
            }
        });

        generateFood();

        this.food = new Food(candidate.getRow(), candidate.getCol());


        timer.start();

    }





    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(2));

        //horizontal
        int gridSize = 25;
        int width = getWidth();
        int height = getHeight();
        g2d.setColor(Color.BLUE);
        for(int y = gridSize;y < height;y += gridSize) {
            g2d.drawLine(0,y,width,y);

        }

        for(int x = gridSize;x < width;x += gridSize) {
            g2d.drawLine(x,0,x,height);
        }


        List<Segment> segments = snake.getSegments();
        if(segments.size() == 1){
            Segment head = segments.get(0);
            int row  = head.getRow() * gridSize;
            int col = head.getCol()  * gridSize;

            g2d.setColor(Color.RED);
            g2d.fillRect(col, row, gridSize, gridSize);
        }else{
            Segment head = segments.get(0);
            int row  = head.getRow() * gridSize;
            int col = head.getCol()  * gridSize;

            g2d.setColor(Color.RED);
            g2d.fillRect(col, row, gridSize, gridSize);

            Segment tail = segments.get(segments.size() - 1);
            int row1 = tail.getRow() * gridSize;
            int col1 = tail.getCol()  * gridSize;
            g2d.setColor(Color.WHITE);
            g2d.fillRect(col1, row1, gridSize, gridSize);

        }

        for(int i = 1;i < segments.size()-1;i++){
            Segment seg = segments.get(i);
            int row = seg.getRow() * gridSize;
            int col = seg.getCol() * gridSize;
            g2d.setColor(Color.BLUE);
            g2d.fillRect(col,row,gridSize,gridSize);
        }

        g2d.setColor(Color.GREEN);
        Segment curFood = food.getPosition();
        g2d.fillRect( curFood.getCol()* gridSize,
                curFood.getRow() * gridSize,
                gridSize,
                gridSize);
    }

    private void generateFood(){

        List<Segment> segments = snake.getSegments();

        while(true){
            int x = rand.nextInt(25);
            int y = rand.nextInt(33);
            boolean isValid = true;
            for(Segment segment : segments){
                if(segment.getRow() == x && segment.getCol() == y){
                    isValid = false;
                    break;
                }
            }

            if(isValid){
                candidate = new  Segment(x, y);
                break;
            }
        }

    }

    public void resetGame() {
        timer.stop();

        snake.reset();

        generateFood();
        food = new Food(candidate.getRow(), candidate.getCol());

        // Reset score here when you add scoring.

        repaint();
        timer.start();
    }
}
