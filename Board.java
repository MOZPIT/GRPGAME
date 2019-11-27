
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import javax.swing.Timer;


public class Board extends JPanel {

    
	private static final long serialVersionUID = 1L;
	private Timer timer;
    private boolean paused;

    private Bird bird;
    private List<List<Pipe>> pipes;

    private int score;
    private boolean gameOver;
    private boolean started;
    
    private Image bckImg;

    int count;
    
    

    public Board() {

        initBoard();
    }

	private void initBoard() {

        initGame();
        

        setPreferredSize(new Dimension(GameConstant.WINDOW_WIDTH,
                GameConstant.WINDOW_HEIGHT));
        loadBackgroundImage();

        setFocusable(true);
        addKeyListener(new TAdapter());
    }

    

	private void loadBackgroundImage() {

        var path = "back-2.gif";
        bckImg = new ImageIcon(path).getImage();
    }

    private void initGame() {

        paused = false;
        started = false;
        gameOver = false;

        score = 0;
        count = 0;

        bird = new Bird();

        initPipes();
        startTimer();
    }

    private void initPipes() {

        pipes = new ArrayList<>();
        var pair1 = new ArrayList<Pipe>();

        var pipe1 = new Pipe(Orientation.SOUTH); var pipe2 = new Pipe(Orientation.NORTH);

        pair1.add(pipe1);
        pair1.add(pipe2);

        pipes.add(pair1);

        var pipe3 = new Pipe(Orientation.SOUTH); var pipe4 = new Pipe(Orientation.NORTH);

        var pair2 = new ArrayList<Pipe>();

        pair2.add(pipe3);
        pair2.add(pipe4);

        pipes.add(pair2);

        var pipe5 = new Pipe(Orientation.SOUTH);       var pipe6 = new Pipe(Orientation.NORTH);

        var pair3 = new ArrayList<Pipe>();

        pair3.add(pipe5);
        pair3.add(pipe6);
        pipes.add(pair3);

        generatePipePositions(pair1);
        generatePipePositions(pair2);
        generatePipePositions(pair3);
    }

    private void startTimer() {

        int fps = 60;
        int period = 1000 / fps;

        timer = new Timer(period, new GameCycle());
        timer.start();
    }

    private void generatePipePositions(List<Pipe> pair) {

        var sr = new SplittableRandom();

        int south_pipe_y = -(sr.nextInt(120)) -
                GameConstant.PIPE_HEIGHT / 2;
        int north_pipe_y = south_pipe_y + GameConstant.PIPE_HEIGHT +
                GameConstant.VGAP_BETWEEN_PIPES;

        int random_gap = sr.nextInt(0, 50);
        int FIXED_GAP = 70;

        int pipe_x = GameConstant.WINDOW_WIDTH +
                count * (GameConstant.PIPE_WIDTH + 20) +
                count * FIXED_GAP + random_gap;

        count++;

        if (count % 3 == 0) {

            count = 0;
        }

        for (Pipe pipe : pair) {

            if (pipe.getOrientation() == Orientation.SOUTH) {

                pipe.setX(pipe_x);
                pipe.setY(south_pipe_y);
            } else {

                pipe.setX(pipe_x);
                pipe.setY(north_pipe_y);
            }
        }
    }

    private void doGameCycle() {

        update();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;

        var rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        if (!isGameOver()) {

            drawBackground(g2d);
            drawPipes(g2d);
            drawBird(g2d);
        }

        g2d.setColor(Color.BLACK);

        if (!isStarted()) {

            g2d.setFont(new Font("Helvetica", Font.BOLD, 20));
            g2d.drawString("Press SPACE to start", 150, 240);
        } else {

            g2d.setFont(new Font("Helvetica", Font.BOLD, 24));
            g2d.drawString(Integer.toString(getScore()), 10, 465);
        }

        if (isGameOver()) {

            g2d.setColor(Color.gray);
            g2d.fillRect(0, 0, 500, 500);

            g2d.setColor(Color.black);
            g2d.setFont(new Font("Helvetica", Font.BOLD, 20));

            var score_msg = String.format("Score: %d", score);
            g2d.drawString(score_msg, 150, 200);
            g2d.drawString("Press R to restart", 150, 240);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void update() {

        if (gameOver || !started || paused) {

            return;
        }

        bird.update();

        movePipes();
        checkCollisions();
    }

    private void drawBackground(Graphics2D g2d) {

        g2d.drawImage(bckImg, 0, 0, null);
    }

    private void drawPipes(Graphics2D g2d) {

        for (List<Pipe> pair : pipes) {

            for (Pipe pipe : pair) {

                g2d.drawImage(pipe.getImage(), pipe.getX(),
                        pipe.getY(), null);
            }
        }
    }

    private void drawBird(Graphics2D g2d) {

        g2d.drawImage(bird.getImage(), bird.getTransform(), null);
    }

    private void movePipes() {

        for (List<Pipe> pair : pipes) {

            for (Pipe pipe : pair) {

                if (pipe.getOrientation() == Orientation.NORTH
                        && pipe.getX() + 3 * pipe.getWidth() < 0) {

                    generatePipePositions(pair);
                } else {

                    pipe.update();
                }
            }
        }
    }

    private void checkCollisions() {

        // pipe collision
        for (List<Pipe> pair : pipes) {

            for (Pipe pipe : pair) {

                int dist = pipe.getX() + pipe.getWidth() - bird.getX();

                if (pipe.collides(bird.getX(), bird.getY(),
                        Bird.getWidth(), bird.getHeight())) {

                    gameOver = true;
                    timer.stop();
                } else if (dist > -pipe.getSpeed() && dist <= 0
                        && pipe.getOrientation() == Orientation.SOUTH) {

                    score++;
                }
            }
        }

        // ground collision
        if (bird.getY() + bird.getHeight() > GameConstant.WINDOW_HEIGHT) {

            gameOver = true;
            timer.stop();
        }
    }

    private boolean isGameOver() {

        return gameOver;
    }

    private int getScore() {

        return score;
    }

    private boolean isPaused() {

        return paused;
    }

    private void setPaused(boolean paused) {

        this.paused = paused;
    }

    private boolean isStarted() {

        return started;
    }

    private void setStarted(boolean started) {

        this.started = started;
    }

    public class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (!isStarted() && key == KeyEvent.VK_SPACE) {

                setStarted(true);
               
            }

            if (key == KeyEvent.VK_SPACE) {

                bird.setVelocity(GameConstant.BIRD_Y_VELOCITY);
                
            }

            if (key == KeyEvent.VK_P) {

                setPaused(!isPaused());
            }

            if (key == KeyEvent.VK_R) {

                initGame();
            }
        }
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }
  
}


