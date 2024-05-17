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
	private int ascoordY = 0;
	private int ascoordX = 0;
	private int dx = 0;
	private int dy = 0;
	private Image image;
	private long lastAsteroidSpawnTime = 0; 
	
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
				repaint();
			}
            try {
                Thread.sleep(16);
            } catch (InterruptedException  e) {
                e.printStackTrace();
            }
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
	}
	
	public void charmove() {
		coordX += dx;
		coordY += dy;
		dx = 0;
		dy = 0;
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