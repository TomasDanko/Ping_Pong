package Ping_Pong;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable
{

	private static final long serialVersionUID = -5694941847711185555L;
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = WIDTH * 9/16;
	
	public boolean running = false;
	private Thread gameThread;
	
	private Ball ball;
	private Paddles paddle1;
	private Paddles paddle2;
	

public Game() 
{ 
	
	canvasSetup();
	initialize();
	
	new Window("Ping_Pong", this);
	
	this.addKeyListener(new Input(paddle1,paddle2));
	this.setFocusable(true);
}

private void initialize()
{
	// inicializacia lopticky a rakiet
	
	ball = new Ball();
	paddle1 = new Paddles(Color.BLUE, true); // true- lava raketa
	paddle2 = new Paddles(Color.GREEN, false);  // false - prava raketa
	
	
	
	
}

private void canvasSetup()
{
	this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
	this.setMaximumSize(new Dimension(WIDTH,HEIGHT));
	this.setMinimumSize(new Dimension(WIDTH,HEIGHT));
	
}

@Override
public void run()
{
	
	this.requestFocus();
	
	long lastTime = System.nanoTime();
	double amountOfThicks = 60.0;
	double ns = 1000000000 / amountOfThicks;
	double delta = 0;
	long timer = System.currentTimeMillis();
	int frames = 0;
	while (running) 
	{
		long now = System.nanoTime();
		delta += (now - lastTime) / ns;
		lastTime = now;
		while(delta >= 1) 
		{
			update();
			delta--;
			}
		if(running) draw();
		frames++;
		
		if(System.currentTimeMillis() - timer > 1000) 
		{
			timer += 1000;
			System.out.println("FPS: " + frames);
			frames = 0;
			
		}

		}
	
	stop();
	
}

private void draw()
{
	// Graficke komponenty
	
	BufferStrategy buffer = this.getBufferStrategy();
	
	if(buffer == null) 
	{
		this.createBufferStrategy(3);
		return;
	}
	
	Graphics graphics = buffer.getDrawGraphics();
	
	drawBackround(graphics);
	
	// Lopticka
	
	ball.draw(graphics);
	
	// rakety a skore
	
	paddle1.draw(graphics);
	paddle2.draw(graphics);
	
	
	
	
	
	graphics.dispose();
	buffer.show();
	
	
	
	
}

private void drawBackround(Graphics graphics)
{
	graphics.setColor(Color.BLACK);
	graphics.fillRect(0, 0, WIDTH, HEIGHT);
	graphics.setColor(Color.WHITE);
	Graphics2D g2d = (Graphics2D) graphics;
	Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,0, new float[] {10 }, 0);
	g2d.setStroke(dashed);
	g2d.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
	
	
	
}

private void update()
{
	ball.update(paddle1, paddle2);
	paddle1.update(ball);
	paddle2.update(ball);
	
}

public void start()
{
	gameThread = new Thread(this);
	gameThread.start();
	running = true;
	
}

public void stop() 
{
	
	try
	{
		gameThread.join();
		running = false;
	} catch (InterruptedException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

public static int sign(double d) 
{
if(d <= 0)
	return - 1;

return 1;
}

public static void main(String[] args) 
{
	new Game();
}

public static int ensureRange(int val, int min, int max)
{
	
	return Math.min(Math.max(val, min), max);
}
}