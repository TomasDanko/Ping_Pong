package Ping_Pong;

import java.awt.Color;
import java.awt.Graphics;

public class Ball
{
	// FIELDS
	
	public static final int SIZE = 17;
	private int x, y;
	private int xVel, yVel;
	private int speed = 6;
	
	
// CONSTRUCTOR
	
public Ball() 
{
	reset();
}


// METHODS

private void reset()
{
	// pozicia
	x = Game.WIDTH / 2 - SIZE / 2;
	y = Game.HEIGHT / 2 - SIZE / 2;
	
	// rychlost
	
	xVel = Game.sign(Math.random() * 2.0 - 1);
	yVel = Game.sign(Math.random() * 2.0 - 1);
	
	
}

public void changeYDir() 
{
	yVel *= -1;
}

public void changeXDir() 
{
	xVel *= -1;
}


public void draw(Graphics graphics)
{
	graphics.setColor(Color.WHITE);
	graphics.fillRect(x, y, SIZE, SIZE);
	
}


public void update(Paddles p1, Paddles p2)
{
	// movement
	x += xVel * speed;
	y += yVel * speed;
	
	// collisions
	if(y + SIZE >= Game.HEIGHT || y <= 0 ) 
	changeYDir();
		
	
	// walls
	if(x + SIZE >= Game.WIDTH) 
	{
		p1.addPoint();
		reset();
	}
	
	if(x <= 0) 
	{
		p2.addPoint();
		reset();
	}
}


public int getX()
{
	return x;
}


public int getY()
{
	return y;
}


}
