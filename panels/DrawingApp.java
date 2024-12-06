package panels;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DrawingApp extends JFrame {

	private DrawingPanel drawingPanel;

	public DrawingApp() {
		this.setTitle("Drawing Application");

//		this.setSize(500,500);
//		this.setLocation(300,200);

		this.setBounds(300, 200, 500, 500);
		buildGUI();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("x 눌림");
				// Object src = e.getSource();

				int n = JOptionPane.showConfirmDialog(DrawingApp.this, "종료할까요?", "확인", JOptionPane.YES_NO_OPTION);

				switch (n) {
				case JOptionPane.YES_OPTION:
					System.exit(-1);
				case JOptionPane.NO_OPTION:
					JOptionPane.showMessageDialog(DrawingApp.this, "종료 취소", "알림", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	private void buildGUI() {
		// 레이아웃 지정
		this.setLayout(new GridLayout());
		// 그리기 패널 붙이기
		drawingPanel = new DrawingPanel();
		this.add(drawingPanel);
		// 메뉴바 붙이기
		createMenuBar();

	}

	private void createMenuBar() {
		// 메뉴 바 만들기
		JMenuBar mb = new JMenuBar();
		// 붙이기
		mb.add(createFileMenu());
		mb.add(createShapeMenu());
		// 메뉴바 설정하기
		this.setJMenuBar(mb);
	}

	// 메뉴 아이템을 만들기
	private JMenu createFileMenu() {
		JMenuItem menuItem1 = new JMenuItem("New");
		JMenuItem menuItem2 = new JMenuItem("Load");
		JMenuItem menuItem3 = new JMenuItem("Save");
		JMenu newMenu = new JMenu("File");

		// 파일 선택기
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Proper file only","txt","java","dat");
		chooser.setFileFilter(filter);

		// 하나의 리스너가 다 모든 컴포넌트를 연결 하기 위해서 4번 방법
		// 익명 내부 클래스를 사용
		ActionListener eventTaker = new ActionListener() {
			String fillPatch, whatStr;
			int ret;

			@Override
			public void actionPerformed(ActionEvent e) {
//				String fillPatch, whatStr;
//				int ret;

				Object obj = e.getSource();
				// 만약 클릭된게 JMenu라면
				if (obj != null && obj instanceof JMenuItem) {
					whatStr = ((JMenuItem) obj).getText();

					if (whatStr.equals("New"))
						drawingPanel.clear();
					else if (whatStr.equals("Load")) {
						ret = chooser.showOpenDialog(drawingPanel);
						// 만약 파일이 선택 안되면
						if (ret != JFileChooser.APPROVE_OPTION) {
							JOptionPane.showMessageDialog(chooser, "파일 선택되지않음", "경고", JOptionPane.WARNING_MESSAGE);
							return;
						}
						fillPatch = chooser.getSelectedFile().getPath();
						drawingPanel.loadFromFile(fillPatch);
					} else if (whatStr.equals("Save")) {
						ret = chooser.showSaveDialog(drawingPanel);

						if (ret != JFileChooser.APPROVE_OPTION) {
							JOptionPane.showMessageDialog(chooser, "파일 선택되지 않음", "경고", JOptionPane.WARNING_MESSAGE);
							return;
						}
						fillPatch = chooser.getSelectedFile().getPath();
						drawingPanel.saveToFile(fillPatch);
					}
				}
			}
		};
		menuItem1.addActionListener(eventTaker);
		menuItem2.addActionListener(eventTaker);
		menuItem3.addActionListener(eventTaker);

		newMenu.add(menuItem1);
		newMenu.addSeparator();
		newMenu.add(menuItem2);
		newMenu.add(menuItem3);

		return newMenu;
	}

	private JMenu createShapeMenu() {
		JMenu newMenu = new JMenu("Shapes");
		JCheckBoxMenuItem checkBox1 = new JCheckBoxMenuItem("Circle");
		JCheckBoxMenuItem checkBox2 = new JCheckBoxMenuItem("Square");

		//
		ItemListener eventTaker = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// 이벤트가 발생된 체크되거나 해제된 객체를 반환 하는데...
				Object obj = e.getItem();
				// 그게 일단 맞는 친구 인지 확인하고
				if (obj != null && obj instanceof JCheckBoxMenuItem) {
					
					//그게 선택되고, 만약 그게 circle이라면
					if (e.getStateChange() == ItemEvent.SELECTED &&
							((JCheckBoxMenuItem)obj).getText().equals("Circle")) {
						drawingPanel.setSelctedShapeType(DrawingPanel.CIRCLE);
						checkBox2.setSelected(false);
					}
					else if (e.getStateChange() == ItemEvent.SELECTED && 
							((JCheckBoxMenuItem)obj).getText().equals("Square")) {
						drawingPanel.setSelctedShapeType(DrawingPanel.SQUARE);
						checkBox1.setSelected(false);
					}
				}
			}
		};

		checkBox1.addItemListener(eventTaker);
		checkBox2.addItemListener(eventTaker);

		newMenu.add(checkBox1);
		newMenu.add(checkBox2);
		return newMenu;
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		new DrawingApp();

	}

}
