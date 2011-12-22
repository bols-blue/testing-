package jp.lnc.java.time_report.drow;

import java.awt.Color;
import java.awt.Image;

public interface TemplateWriter {

	public abstract void paint();

	public abstract void paintTest();

	/**
	 * 
	 * @param dayNumber
	 * @param startTime
	 * @param endTime
	 * @param weekDayNameColor
	 */

	public abstract void save();

	public abstract void drowTineBox(int weekDayNumber, float startTime,
			float endTime, Color color, int eventNumber);
}