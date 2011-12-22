package jp.lnc.java.time_report.gdata.calendar;

import static org.junit.Assert.*;

import java.awt.Color;

import jp.lnc.java.time_report.drow.TemplateDraw;
import jp.lnc.java.time_report.drow.TemplateDrawView;

import org.junit.Test;

public class TemplateDrawTest {
	@Test
	public void testPaintGraphics() {
		TemplateDraw test = new TemplateDraw("src/test/resources/test.png");
		test.paint();
		test.save();
	}
	@Test
	public void testPaintTestGraphics() {
		TemplateDraw test = new TemplateDraw("src/test/resources/testPaintTestGraphics.png");
		test.paintTest();
		test.save();
	}
	@Test
	public void testPaintBox() {
		TemplateDraw test = new TemplateDraw("src/test/resources/testPaintBox.png");
		test.paintTest();
		test.drowTineBox(1, 9, 12, new Color(0.875f, 0f, 0f), 1);

		test.drowTineBox(2, 9, 12, new Color(0f, 0f, 0.875f), 1);
		test.drowTineBox(2, 13, 15, new Color(0f, 0.875f, 0f), 2);
		test.save();
	}
	@Test
	public void testTemplateDrawView() {
		TemplateDrawView test = new TemplateDrawView();
		
	}

}
