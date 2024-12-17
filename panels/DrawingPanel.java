package panels;

import shapes.*;
import java.awt.Color;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import java.awt.Graphics;

public class DrawingPanel extends JPanel{
	
	final static int CIRCLE =1;
	final static int SQUARE =2;
	private Color whichColor = Color.black; //기본 컬러색 지정은 검은색으로 두되;
	
	private int selectedShapeType;
	private ArrayList<Shape> shapes;
	
	public DrawingPanel(){
		this.selectedShapeType = 0;
		//이 패널은 그 그려지는 패널 자체를 가짐
		//리스트 만들기
		shapes = new ArrayList<Shape>();
		
		//익명 내부 클래스 설정 (예제 4번)
		//어댑터는 리스너로 파생되었기 떄문에. 리스너로 받음 -> 조금 더 추상적/유연한 처리
		MouseListener eventHandler = new MouseAdapter() {
			//마우스가 클릭되면 해당 도형 그리기
			@Override
			public void mousePressed(MouseEvent e) {
				//각 좌표를 가져와서...
				int x = e.getX(); int y = e.getY();
				
				//만약 원이 선택된 상태이면..
				if(selectedShapeType ==1){
					Circle c1 = new Circle(x,y,whichColor,50);
					shapes.add(c1);
				}
				//정사각형 이라면..
				else if(selectedShapeType ==2){
					Square s1 = new Square(x,y,whichColor,100);
					shapes.add(s1);
				}
				//이후 모든걸 그리기
				repaint();
			}	
		};
		//패널에 리스너 객체 연결
		this.addMouseListener(eventHandler);
	}
	//그림 그져지기...
	//기본적으로 그래픽 객체는 paint 관련 메서드에서만 제공된다 .
	//draw는 내가 만든 함수 --> 따라서 오버라이딩 메서드 안에서 재정의하고 그냥 나중에
	//다 호출하는 repaint를 쓰는 것이 맞다.	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if(shapes.isEmpty() != true)
		{
			for(Shape i : shapes) {
				i.draw(g);
			}
		}
	}
	public void setSelctedShapeType(int type) {
		this.selectedShapeType = type;
	}
	//색을 받는 함수
	public void setColor(Color c){
		this.whichColor = c;
	}
	
	public void clear() {
		//리스트를 몽땅 지워버리면
		shapes.clear();
		//다음에 그릴때 정보가 없으니깐 안그려짐
		repaint();
	}
	//파일 저장/읽기를 위한 섹션
	//직렬화 오브렉트형식 입출력
	public void saveToFile(String str) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		try {
			fos = new FileOutputStream(str);
			oos = new ObjectOutputStream(fos);
			Iterator<Shape> itr = shapes.iterator();
			
			while (itr.hasNext()){
				oos.writeObject(itr.next());
			}
			
			System.out.println("저장 완료");
		}
		//이 경우는 필수적인 예외처리 단계
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(oos !=null) oos.close();
				if(fos !=null) fos.close();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	public void loadFromFile(String str) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(str);
			ois = new ObjectInputStream(fis);
			//기존 리스트의 값을 지우고
			clear();
			Object temp = ois.readObject();
			while(true) {
				try {
					shapes.add((Shape)temp);
					temp = ois.readObject();
					
				}
				catch(EOFException e){
					break;
				}
			}
			System.out.println("로드 완료");
		}
		//이 경우는 필수적인 예외처리 단계
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(ois !=null) ois.close();
				if(fis !=null) fis.close();
				repaint();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
	}

	//문자 단위의 입출력
	public void saveToTextFile(String str) {
		FileWriter fws = null;
		
		try {
			fws = new FileWriter(str);
			Iterator<Shape> itr = shapes.iterator();
			//있으면 계속 하는데...
			while(itr.hasNext()){
				fws.write(itr.next().toString());
			}
			System.out.println("저장 완료");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(fws!=null) fws.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void loadFromTextFile(String str) {
		FileReader frs = null;
		BufferedReader br = null;
		//읽을 때는 버퍼로 하면 -> 문자열
		try {
			frs = new FileReader(str);
			br = new BufferedReader(frs);
			
			String line;
			Integer i1, i2;
			int r,g,b;
			Shape temp = null;
			line = br.readLine();
			while (line !=null)
			{
				//문자열 예시 (도형 형태) R G B X Y (반지름/변);
				String[] infos = line.split(" ");
				r = Integer.valueOf(infos[1]);
				g = Integer.valueOf(infos[2]);
				b = Integer.valueOf(infos[3]);
				
				i1 = Integer.valueOf(infos[4]);
				i2 = Integer.valueOf(infos[5]);
				
				if(infos[0].equals("Circle")) {
					temp = new Circle(i1,i2,new Color(r,g,b),50);
					shapes.add(temp);
				}
				else if(infos[0].equals("Square")) {
					temp = new Square(i1,i2,new Color(r,g,b),100);
					shapes.add(temp);
				}
				line = br.readLine();
			}
			}
			catch (FileNotFoundException e) {
			e.printStackTrace();
			}
			catch (IOException e) {
			e.printStackTrace();
			}
	}
}
