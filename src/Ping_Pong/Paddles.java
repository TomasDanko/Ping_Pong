package Ping_Pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Paddles
{
	
	// FIELDS
	
	
	private int x, y;
	private int vel = 0;
	private int speed = 10;
	private int width = 22, height = 85;
	private int score  = 0;
	private Color color;
	private boolean left;
	
	
	
// CONSTRUCTORS
	
	
public Paddles(Color c, boolean left) 
{
	color = c;
	this.left = left;
	
	// pozicia rakiet
	
	if(left) 
	
		x = 0;
	
	else 
	
		x = Game.WIDTH - width;
	
	
	y = Game.HEIGHT / 2 - height / 2;
}

// SKORE

public void addPoint() 
{
	score++;
}

public void draw(Graphics graphics)
{
	// rakety
	graphics.setColor(color);
	graphics.fillRect(x, y, width, height);
	
	
	// skore
	
	int sx;
	String scoreText = Integer.toString(score);
	Font font = new Font("Roboto", Font.PLAIN, 50);
	
	int strWidth = graphics.getFontMetrics(font).stringWidth(scoreText) + 1;
	int padding = 25;
	
	if(left) 
	
		sx = Game.WIDTH / 2 - padding - strWidth;
		
	
	else 
	{
		sx = Game.WIDTH / 2 + padding;
	}
	
	graphics.setFont(font);
	graphics.drawString(scoreText, sx, 50);
	
}

public void update(Ball b)
{
	// pozicia
	y = Game.ensureRange(y += vel, 0, Game.HEIGHT - height);
	
	int ballX = b.getX();
	int ballY = b.getY();
	
	// collisions with ball
	
	if(left) 
	{
		if(ballX <= width && ballY + Ball.SIZE >= y && ballY <= y + height)
		   b.changeXDir();
	}else 
	{
	if(ballX + Ball.SIZE >= Game.WIDTH - width && ballY + Ball.SIZE >= y && ballY <= y + height)
		b.changeXDir();
	}
	
}

public void switchDirection(int direction)
{
	vel = speed * direction;
	
}

public void stop() 
{
	vel = 0;
}

}
