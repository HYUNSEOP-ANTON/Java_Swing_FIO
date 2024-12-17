package shapes;

import java.awt.*;
import java.io.Serializable;

abstract public class Shape implements Serializable {
	protected int x,y;
	protected Color c;
	
	public Shape(int x , int y, Color c) {
		this.x = x;
		this.y = y;
		this.c = c;
	}
	
	abstract public void draw(Graphics g);
	@Override
	public String toString() {
		//RGB 보내고, 좌표 찍기
		return String.format("%d %d %d %d %d",c.getRed(), c.getBlue(), c.getGreen(), x,y);
	}
}
