package shapes;

import java.awt.*;
import java.io.Serializable;

abstract public class Shape implements Serializable {
	protected int x,y;
	
	public Shape(int x , int y ) {
		this.x = x;
		this.y = y;
	}
	
	abstract public void draw(Graphics g);
	@Override
	public String toString() {
		return String.format("%d %d", x,y);
	}
}
