import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.lwjgl.LWJGLException;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	

	public static boolean Start;
	
	private int width = Toolkit.getDefaultToolkit().getScreenSize().width - 20;
	private int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	private static final long serialVersionUID = 1L;
	private int[] snakexlength = new int[750];
	private int[] snakeylength = new int[750];
	
	
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	private ImageIcon rightmouth;
	private ImageIcon leftmouth;
	private ImageIcon upmouth;
	private ImageIcon downmouth;
	
	private int score = 0;
	static int bestscore = 0;
	private int lengthofsnake = 3;
	private int level = 1;
	private boolean timestart = false;
	private boolean gameoverflag = false;
	static boolean getkey = false;
	
	long startTime = 0;
	long elapsedSeconds = 0;
	long elapsedTime = 0;
	long secondsDisplay = 0;
	long elapsedMinutes = 0;
	long secondsDisplays = 0;
	long tim = 110;
	long bodysnake = 0;
	
	
	
	private Timer timer;
	private int delay = 180;
	private ImageIcon snakeimage;
	
	private int []  enemyxpos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350,
								 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675,
								 700, 725, 750, 775, 800, 825, 850, 875, 900, 925, 950, 975, 1000, 1025, 1050,
								 1075, 1100, 1125, 1150, 1175, 1200, 1225, 1250, 1275, 1300, 1325, 1350, 1375,
								 1400, 1425, 1450, 1475, 1500, 1525, 1550, 1575, 1600, 1625, 1650, 1675, 1700,
								 1725, 1750, 1775, 1800, 1825};
	
	private int []  enemyypos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350,
								 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675,
								 700, 725, 750, 775, 800, 825, 850, 875, 900, 925, 950, 975, 1000};
	
	private ImageIcon enemyimage;
	
	private Random random = new Random();
	
	private int xpos = random.nextInt(72);
	private int ypos = random.nextInt(37);

	private int moves = 0;
	
	private ImageIcon titleImage;
	
	public Gameplay() throws FileNotFoundException, LWJGLException
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		
		useController.initController();
		
		ScoreFile.readFromFile();
		
		timer = new Timer(delay, this);
		timer.start();
		startTime = System.currentTimeMillis();		

	}
	
	public void paint(Graphics g)
	{

		if(moves == 0)
		{
			snakexlength[2] = 50;
			snakexlength[1] = 75;
			snakexlength[0] = 100;
			
			snakeylength[2] = 100;
			snakeylength[1] = 100;
			snakeylength[0] = 100;
		}
		
		//draw title image
		titleImage =  new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, g, 8, 11);
		
		
		//draw speed
		g.setColor(Color.BLACK);
		g.setFont(new Font("arial", Font.PLAIN, 24));
		g.drawString("Current speed: "+(23-delay/10), 1300, 40);
		
		
		//draw press z or c
		g.setFont(new Font("arial", Font.BOLD, 19));
		g.drawString("Press 'C' to slow down", 400, 30);
		g.drawString("Press 'Z' to speed up", 400, 60);
		
		//draw background for the gameplay
		g.setFont(new Font("arial", Font.PLAIN, 24));
		g.setColor(Color.black);
		g.fillRect(25, 75, width-50, height-125);
		
		//draw scores
		g.setColor(Color.BLACK);
		g.setFont(new Font("arial", Font.PLAIN, 24));
		g.drawString("Scores: "+score, 1700, 40);
		
		//draw length
		g.setColor(Color.BLACK);
		g.setFont(new Font("arial", Font.PLAIN, 24));
		g.drawString("Level: "+level, 1570, 40);
		
		//draw bestscore
		g.setColor(Color.ORANGE);
		g.setFont(new Font("arial", Font.PLAIN, 22));
		g.drawString("Best score: "+bestscore, 50, 60);
		
		//draw time
		g.setColor(Color.ORANGE);
		g.setFont(new Font("arial", Font.PLAIN, 22));
		
	
		
		
		if(timestart == true)
		{
			if(secondsDisplay<=9)
			{
				g.drawString("Time: "+elapsedMinutes+":0"+secondsDisplay, 50, 30);
			}
			else
				g.drawString("Time: "+elapsedMinutes+":"+secondsDisplay, 50, 30);
		}
		else
			g.drawString("Time: 0:00", 50, 30);
		
		
		rightmouth =  new ImageIcon("rightmouth.png");
		rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		
		for(int a = 0; a < lengthofsnake; a++)
		{
			elapsedTime = System.currentTimeMillis() - startTime;
			elapsedSeconds = elapsedTime / 1000;
			secondsDisplay = elapsedSeconds % 60;
			secondsDisplays = secondsDisplay %10;
			elapsedMinutes = elapsedSeconds / 60;
			
			if(a==0 && right)
			{
				rightmouth =  new ImageIcon("rightmouth.png");
				rightmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			if(a==0 && left)
			{
				leftmouth =  new ImageIcon("leftmouth.png");
				leftmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			if(a==0 && down)
			{
				downmouth =  new ImageIcon("downmouth.png");
				downmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			if(a==0 && up)
			{
				upmouth =  new ImageIcon("upmouth.png");
				upmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			if(a!=0)
			{
				if(a!=0)
				{
					snakeimage =  new ImageIcon("snakeimage.png");
					snakeimage.paintIcon(this, g, snakexlength[a], snakeylength[a]);
				}
			}
		}

		enemyimage = new ImageIcon("enemy.png");
		new ImageIcon("bonenemy.png");
		
		if((enemyxpos[xpos] == snakexlength[0]) && enemyypos[ypos] == snakeylength[0])
		{	
			lengthofsnake++;
			bodysnake = System.currentTimeMillis();	
			tim = 110;
			score += 2;
			
			if((lengthofsnake-3)%9 == 0)
			{
				level += 1;
				score += level*50 - (secondsDisplay + elapsedMinutes*60) + level*3*lengthofsnake;
				if(delay>50)
				{
					delay -= 10;
					timer.setDelay(delay);
				}
			}
			
			xpos = random.nextInt(72);
			ypos = random.nextInt(37);
		}
			if((lengthofsnake)%8 == 7)
			{
				if(tim>=0)
				{
					tim--;;
					g.setColor(Color.WHITE);
					g.setFont(new Font("times new roman", Font.BOLD, 30));
					g.drawString("Hurry Up!! You can catch BONUS: "+((tim%110)/10), 750, 50);
					
					g.fillOval(enemyxpos[xpos], enemyypos[ypos], 25, 25);
				}
				else
				{	
					if(score>150)
					{
						score -= 150;
						
					}
						else
						{
						score = 0;
						
						}
					lengthofsnake++;
					xpos = random.nextInt(72);
					ypos = random.nextInt(37);
				}
			}
			else
			enemyimage.paintIcon(this, g, enemyxpos[xpos], enemyypos[ypos]);
			

		for(int b= 1; b< lengthofsnake; b++)
		{
			if(snakexlength[b] == snakexlength[0] && snakeylength[b] == snakeylength[0])
			{
				right = false;
				left = false;
				up = false;
				down = false;
				
				gameoverflag = true;
				
				g.setColor(Color.white);
				g.setFont(new Font("arial", Font.BOLD, 50));
				g.drawString("GAME OVER", width/2-170, height/2-100);
				
				g.setFont(new Font("arial", Font.BOLD, 20));
				g.drawString("Space to restart", width/2-100, height/2-50);
				
			}
		}
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent g) {
		
		timer.start();
	
		useController.controller.poll();
		
		if(Start = useController.controller.isButtonPressed(5))
		{
			if(delay>30)
			{
				delay -= 10;
				timer.setDelay(delay);
			}
		}
		
		if(Start = useController.controller.isButtonPressed(4))
		{
			if(delay<220)
			{
				delay += 10;
				timer.setDelay(delay);		
			}
			
		}

		if(getkey==false)
		{
		 if(Start = useController.controller.isButtonPressed(1))
		{
			
			if(timestart == false)
			{
				startTime = System.currentTimeMillis();
				timestart = true;
			}
			if(gameoverflag == false)
			{
				
				moves++;
				if(!left)
				{
					right = true;
				}
				else
				{
					right = false;
					left = true;
				}
				
				up = false;
				down = false;
				getkey=true;
				
			}
		}
		
		
		else if(Start = useController.controller.isButtonPressed(2) )
		{
			
			if(timestart == false)
			{
				left = false;
			}
			else {
			if(gameoverflag == false)
			{
				moves++;
				if(!right)
				{
					left = true;
				}
				else
				{
					left = false;
					right = true;
				}
				
				up = false;
				down = false;
				getkey = true;

			}
			}
		}	
		
		else if(Start = useController.controller.isButtonPressed(3))
		{
			
			if(timestart == false)
			{
				startTime = System.currentTimeMillis();
				timestart = true;
			}
			if(gameoverflag == false)
			{	
				moves++;
				if(!down)
				{
					up = true;
				}
				else
				{
					up = false;
					down = true;
				}
				left = false;
				right = false;
				getkey = true;
			}
		}
		
		else if(Start = useController.controller.isButtonPressed(0))
		{
			
			if(timestart == false)
			{
				startTime = System.currentTimeMillis();
				timestart = true;
			}
			if(gameoverflag == false)
			{
				
				moves++;
				if(!up)
				{
					down = true;
				}
				else
				{
					down = false;
					up = true;
				}
				left = false;
				right = false;
				getkey=true;
			}
		}
		}
		
	
		if(Start = useController.controller.isButtonPressed(7))
		{
			if(score > bestscore)
			{
				bestscore = score;
			}
			
			moves = 0;
			score = 0;
			level = 1;
			lengthofsnake = 3;
			gameoverflag = false;
			elapsedSeconds = 0;
			secondsDisplay = 0;
			elapsedMinutes = 0;
			delay = 180;
			timer.setDelay(delay);
			timestart = false;
			
			repaint();
		}
	
		
		if(right)
		{
			for(int r = lengthofsnake-1; r>=0; r--)
			{
				snakeylength[r+1] = snakeylength[r];
			}
			for(int r = lengthofsnake-1; r>=0; r--)
			{
				if(r==0)
				{
					snakexlength[r] = snakexlength[r] + 25;
				}
				else
				{
					snakexlength[r] = snakexlength[r-1];
				}
				if(snakexlength[r] > width-50)
				{
					snakexlength[r] = 25;
				}
			}
		
			repaint();
			getkey = false;
			
		}
		if(left)
		{
			for(int r = lengthofsnake-1; r>=0; r--)
			{
				snakeylength[r+1] = snakeylength[r];
			}
			for(int r = lengthofsnake-1; r>=0; r--)
			{
				if(r==0)
				{
					snakexlength[r] = snakexlength[r] - 25;
				}
				else
				{
					snakexlength[r] = snakexlength[r-1];
				}
				if(snakexlength[r] < 25)
				{
					snakexlength[r] = width-50;
				}
			}
		
			repaint();
			getkey = false;
		}
		if(down)
		{
			for(int r = lengthofsnake-1; r>=0; r--)
			{
				snakexlength[r+1] = snakexlength[r];
			}
			for(int r = lengthofsnake-1; r>=0; r--)
			{
				if(r==0)
				{
					snakeylength[r] = snakeylength[r] + 25;
				}
				else
				{
					snakeylength[r] = snakeylength[r-1];
				}
				if(snakeylength[r] > height-80)
				{
					snakeylength[r] = 75;
				}
			}
		
			repaint();
			getkey = false;
		}
		if(up)
		{
			for(int r = lengthofsnake-1; r>=0; r--)
			{
				snakexlength[r+1] = snakexlength[r];
			}
			for(int r = lengthofsnake-1; r>=0; r--)
			{
				if(r==0)
				{
					snakeylength[r] = snakeylength[r] - 25;
				}
				else
				{
					snakeylength[r] = snakeylength[r-1];
				}
				if(snakeylength[r] < 75)
				{
					snakeylength[r] = height-80;
				}
			}
		
			repaint();
			getkey = false;
			
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	
		if(getkey == false)
		{
		
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			if(score > bestscore)
			{
				bestscore = score;
				try {
					ScoreFile.savetoFile();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
			moves = 0;
			score = 0;
			level = 1;
			lengthofsnake = 3;
			gameoverflag = false;
			elapsedSeconds = 0;
			secondsDisplay = 0;
			elapsedMinutes = 0;
			delay = 180;
			timer.setDelay(delay);
			timestart = false;
			
			repaint();
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{	
			if(timestart == false)
			{
				startTime = System.currentTimeMillis();
				timestart = true;
			}
			if(gameoverflag == false)
			{
				moves++;
				if(!left)
				{
					right = true;
				}
				else
				{
					right = false;
					left = true;
				}
				
				up = false;
				down = false;
				getkey = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if(timestart == false)
			{
				left = false;
			}
			else {
			if(gameoverflag == false)
			{
				moves++;
				if(!right)
				{
					left = true;
				}
				else
				{
					left = false;
					right = true;
				}
				
				up = false;
				down = false;
				getkey = true;
			}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			if(timestart == false)
			{
				startTime = System.currentTimeMillis();
				timestart = true;
			}
			if(gameoverflag == false)
			{	
				moves++;
				if(!down)
				{
					up = true;
				}
				else
				{
					up = false;
					down = true;
				}
				left = false;
				right = false;
				getkey = true;
			}
		} 
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			if(timestart == false)
			{
				startTime = System.currentTimeMillis();
				timestart = true;
			}
			if(gameoverflag == false)
			{
				moves++;
				if(!up)
				{
					down = true;
				}
				else
				{
					down = false;
					up = true;
				}
				left = false;
				right = false;
				getkey = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_Z)
		{
			if(delay>30)
			{
				delay -= 10;
				timer.setDelay(delay);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_C)
		{
			if(delay<220)
			{
				delay += 10;
				timer.setDelay(delay);
		
			}
		}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
