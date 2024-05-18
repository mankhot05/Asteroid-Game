import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JComponent implements KeyListener {

	private int coordX = 390;
	private int coordY = 500;
	private int dx = 0;
	private int dy = 0;
	private Image image;
	private long lastAsteroidSpawnTime = 0;
	private double time = 0.01; 
	private LinkedList<Asteroid> asteroids = new LinkedList<>();
	private LinkedList<Shot> shots = new LinkedList<>();
    private int numberofiterations = 0;
	private int points = 0;
	
	public Game() {
		setPreferredSize(new Dimension(800, 640));
        setFocusable(true); // Allows the panel to receive keyboard input
        addKeyListener(this); // Register the panel as a listener for key events


        // Game thread
        Thread modelThread = new Thread(new GameLogic());
        modelThread.start();
	}
	
	public void paintComponent(Graphics g) {
		/* 
		loadImage();
		*/
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		/* 
		Graphics2D graphics = (Graphics2D) g;
		graphics.drawImage(image, coordX, coordY, this);
		*/
		
		g.setColor(Color.WHITE);
		g.fillRect(coordX, coordY, 20, 20); 

		for (Asteroid asteroid: asteroids) {
			g.setColor(Color.WHITE);
			g.fillOval(asteroid.getXcoord(), asteroid.getYcoord(), asteroid.getWidth(), asteroid.getHeight());
		}

		for (Shot shot: shots) {
			g.setColor(Color.WHITE);
			g.fillRect(shot.getXCoordShot(), shot.getYCoordShot(), 5, 10);
		}
	}

	/* 
	public void loadImage() {
		ImageIcon imageicon = new ImageIcon("C:\\Users\\mvenk\\OneDrive\\Desktop\\Visual Studio Code\\Asteroid Game\\resources\\spaceship image.png");
		image = imageicon.getImage();
	}
	*/
	
	public class GameLogic implements Runnable {

		private boolean alive = true;
		
		@Override
		public void run() {
			while (alive) {
				charmove();
				shotsmove();
                createAsteroid();
                for (Asteroid asteroid: asteroids) {
                    asteroid.moveAsteroid();
                }
				for (Asteroid asteroid: asteroids) {
					for (Shot shot: shots) {
						collision(asteroid, shot);
					}
				}

				Iterator<Asteroid> iterator = asteroids.iterator();
				Asteroid asteroid;
				while(iterator.hasNext()) {
					asteroid = iterator.next();
					if(asteroid.isMarked()) {
						iterator.remove();
						points += 100;
					}
				}

				Iterator<Shot> iteratorshot = shots.iterator();
				Shot shot;
				while(iteratorshot.hasNext()) {
					shot = iteratorshot.next();
					if (shot.isMarked()) {
						iteratorshot.remove();
					}
				}

				repaint();


				try {
					Thread.sleep(16);
				} catch (InterruptedException  e) {
					e.printStackTrace();
				}
                numberofiterations += 1;
			}
		}
	}

	public class Asteroid {
		private int xcoord = 0;
		private int ycoord;
		private int width;
		private int height;
		private boolean remove;

		public Asteroid() {
			boolean numberTaken = false;
			Random random = new Random();
			this.xcoord = random.nextInt(750);
			this.ycoord = 0;
			this.width = random.nextInt(65) + 25;
			this.height = width;
		}

		public void moveAsteroid() {
			ycoord += 1;
		}

		public int getXcoord() {
			return this.xcoord;
		}

		public int getYcoord() {
			return this.ycoord;
		}

		public int getWidth() {
			return this.width;
		}

		public int getHeight() {
			return this.height;
		}

		public void setMarked(boolean mark) {
			remove = mark;
		}

		public boolean isMarked() {
			return remove;
		}
	}

	public class Shot {

		private int xcoord;
		private int ycoord = coordY;
		private boolean remove;

		public Shot(int xcoord) {
			this.xcoord = xcoord;
			repaint();
		}

		public int getXCoordShot() {
			return xcoord;
		}

		public void setYCoordShot(int y) {
			ycoord = y;
		}

		public int getYCoordShot() {
			return ycoord;
		}

		public void setMarked(boolean mark) {
			remove = mark;
		}

		public boolean isMarked() {
			return remove;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			dx -= 15;
		}
		else if (key == KeyEvent.VK_RIGHT) {
			dx += 15;
		}
		else if (key == KeyEvent.VK_UP) {
			dy -= 15;
		}
		else if (key == KeyEvent.VK_DOWN) {
			dy += 15;
		}
		else if (key == KeyEvent.VK_SPACE) {
			shots.add(new Shot(coordX));
		}
	}
	
	public void charmove() {
		coordX += dx;
		coordY += dy;
		dx = 0;
		dy = 0;
	}

	public void shotsmove() {
		for (Shot shot: shots) {
			shot.setYCoordShot(shot.getYCoordShot() - 5);
		}
	}

	public int randomxcoord() {
		Random random = new Random();
		return random.nextInt(800);
	}

	public void createAsteroid() {
		long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastAsteroidSpawnTime;
        
        int spawnDelay = 2000; 
        System.out.println(time);

        if (elapsedTime > spawnDelay) {
            asteroids.add(new Asteroid());
            lastAsteroidSpawnTime = currentTime;
        }
	}

	public void collision(Asteroid asteroid, Shot shot) {
		int asteroidLeft = asteroid.getXcoord();
		int asteroidRight = asteroid.getXcoord() + asteroid.getWidth();
		int asteroidTop = asteroid.getYcoord();
		int asteroidBottom = asteroid.getYcoord() + asteroid.getHeight();

		int shotLeft = shot.getXCoordShot();
		int shotRight = shot.getXCoordShot() + 5; 
		int shotTop = shot.getYCoordShot();
		int shotBottom = shot.getYCoordShot() + 10; 

		boolean collisionX = shotRight >= asteroidLeft && shotLeft <= asteroidRight;
		boolean collisionY = shotBottom >= asteroidTop && shotTop <= asteroidBottom;

		if (collisionX && collisionY) {
			asteroid.setMarked(true);
			shot.setMarked(true);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Asteroid");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new Game());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
		});
	}
}