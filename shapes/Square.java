package shapes;

import java.awt.Graphics;
import java.awt.Color;

public class Square extends Shape{
	private int side; //한 변의 길이
	
	public Square(int x, int y, Color c,int side) {
		super(x,y,c);
		this.side = side;
	}
	public int getSide() {
		return side;
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.drawRect(x-side/2, y-side/2, side, side);
	}
	@Override
	public String toString() {
		return String.format("Square %s %d\n", super.toString(),side);
	}

}
