package jp.lnc.java.time_report.drow;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class TemplateDraw implements TemplateWriter {
	/**
	 * 
	 */
	private static final long serialVersionUID = -280055066590198183L;
	private File imageFile;

	private BufferedImage readImage = null;

	private String mWeekDayName[] = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
			"Sun" };
	private Graphics2D off;
	int camvasSizeWidth = 425;
	int camvasSizeHeight = 550;
	
	public TemplateDraw(String string) {
		imageFile = new File(string);
		try {
			readImage = ImageIO.read(imageFile);
		} catch (IOException e1) {
		}

		readImage = new BufferedImage(camvasSizeWidth, camvasSizeHeight,
				BufferedImage.TYPE_INT_BGR);
		off = readImage.createGraphics();
	}

	public TemplateDraw() {
		imageFile = new File("./sample.png");

		try {
			readImage = ImageIO.read(imageFile);
		} catch (IOException e1) {
		}

		readImage = new BufferedImage(camvasSizeWidth, camvasSizeHeight,
				BufferedImage.TYPE_INT_BGR);
		off = readImage.createGraphics();
	}

	/* (non-Javadoc)
	 * @see jp.lnc.java.time_report.drow.TemplateWriter#paint()
	 */
	public void paint() {

		try {
			readImage = ImageIO.read(imageFile);
		} catch (IOException e1) {
		}

		readImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_BGR);
		Graphics2D off = readImage.createGraphics();

		off.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		BasicStroke wideStroke = new BasicStroke(4.0f);
		off.setStroke(wideStroke);

		off.setPaint(Color.white);
		off.draw(new Ellipse2D.Double(30, 40, 50, 50));

		off.setPaint(Color.blue);
		off.draw(new Ellipse2D.Double(70, 40, 50, 50));

		off.setPaint(Color.red);
		off.draw(new Ellipse2D.Double(110, 40, 50, 50));

		off.draw(new Rectangle2D.Double(130, 40, 50, 50));
		off.setPaint(Color.yellow);
		off.fill(new Arc2D.Double(50, 100, 110, 110, 330, 100, Arc2D.PIE));
		off.setPaint(Color.gray);
		off.draw(new Arc2D.Double(50, 100, 110, 110, 330, 100, Arc2D.PIE));


	}

	/* (non-Javadoc)
	 * @see jp.lnc.java.time_report.drow.TemplateWriter#paintTest()
	 */
	public void paintTest() {

		off.setColor(Color.white);
		off.fillRect(1, 1, camvasSizeWidth - 2, camvasSizeHeight - 2);
		off.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		BasicStroke wideStroke = new BasicStroke(1.0f);
		off.setStroke(wideStroke);

		off.setPaint(Color.BLACK);

		for (int i = 0; i < 7; i++) {
			if (i < 5) {
				drowTineLine(off, i, Color.BLACK);
			} else {
				drowTineLine(off, i, Color.RED);
			}
		}
	}
	
	void drowTineLine(Graphics2D off, int dayNumber, Color weekDayNameColor) {
		String weekDayName = mWeekDayName[dayNumber];
		int basePoint = (75 * dayNumber);
		off.setFont(new Font("Serif", Font.PLAIN, 11));
		int max = 17;
		for (int i = 0; i < max; i++) {
			off.setPaint(Color.BLACK);
			off.drawString("" + (8 + i), 45 + (i * 20), 35 + basePoint);
			off.draw(new Rectangle2D.Double(50 + (i * 20), 40 + basePoint, 20,
					30));
			off.setPaint(new Color(0.875f, 0.875f, 0.875f));
			off.fillRect(50 + (i * 20) + 1, 40 + 1 + basePoint, 20 - 1, 30 - 1);
		}
		off.setPaint(Color.BLACK);
		off.drawString("" + ((8 + max) % 24), 45 + (max * 20), 35 + basePoint);

		off.setPaint(weekDayNameColor);
		off.setFont(new Font("Serif", Font.BOLD, 20));
		off.drawString(weekDayName, 10, 60 + basePoint);

	}

	/* (non-Javadoc)
	 * @see jp.lnc.java.time_report.drow.TemplateWriter#drowTineBox(int, float, float, java.awt.Color, int)
	 */
	public void drowTineBox(int dayNumber, float startTime,
			float endTime, Color BoxColor, int eventNumber) {
		int basePoint = (75 * dayNumber);
		off.setFont(new Font("Serif", Font.PLAIN, 11));
		float start, end;
		start = startTime - 8;
		end = endTime - startTime;
		off.setPaint(Color.BLACK);
		off.draw(new Rectangle2D.Double(50 + (start * 20), 40 + basePoint, 
				(20 * end), 30));
		off.setPaint(BoxColor);
		off.fillRect((int) (50 + (start * 20) + 1), 40 + 1 + basePoint,
				(int) (20 * end) - 1, (30) - 1);
		off.setPaint(Color.BLACK);
		// 数字の描画
		off.drawString("(" + eventNumber + ")",
				(50 + (start * 20) + (20 * end / 2)-6),
				(40 + basePoint + (20)) );

	}

	void drowEventTineLine(int EventNo, int dayNumber, Calendar StartDate,
			Calendar EndDate) {
		StartDate.getTime().getHours();
	}

	/* (non-Javadoc)
	 * @see jp.lnc.java.time_report.drow.TemplateWriter#getImage()
	 */
	public Image getImage() {
		return readImage;
	}

	public void save() {
		try {
			if (!imageFile.exists()) {
				imageFile.createNewFile();
			}
			ImageIO.write(readImage, "PNG", imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}