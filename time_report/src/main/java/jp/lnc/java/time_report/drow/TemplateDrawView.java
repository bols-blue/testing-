package jp.lnc.java.time_report.drow;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class TemplateDrawView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -280055066590198183L;
	public void paint(Graphics g,String filePath) {
		Graphics2D g2 = (Graphics2D) g;

		TemplateDraw tmp = new TemplateDraw();
		tmp.paint();
		if (tmp.getImage() != null) {
			g2.drawImage(tmp.getImage(), 0, 0, this);
		}
		setBounds(0, 0, 200, 200);
		setVisible(true);
	}
	public static void main(String[] args) {
		TemplateDrawView test = new TemplateDrawView();

		test.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		test.setBounds(0, 0, 200, 200);
		test.setVisible(true);
//		fail("Not yet implemented");
	}
}