package shapes;

import java.awt.Graphics;
import java.awt.Color;

public class Circle extends Shape {
	private int radius;
	
	public Circle(int x, int y, Color c,int radius) {
		super(x,y,c);
		this.radius = radius;
	}
	public int getRadius() {
		return radius;
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.drawOval(x-radius, y-radius, 2* radius, 2*radius);
		
	}
	@Override
	public String toString() {
		return String.format("Circle %s %d\n", super.toString(),radius);
	}
	
}
