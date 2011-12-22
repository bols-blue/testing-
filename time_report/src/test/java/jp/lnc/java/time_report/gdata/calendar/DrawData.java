package jp.lnc.java.time_report.gdata.calendar;

import java.util.Calendar;

import com.google.gdata.data.extensions.When;

public class DrawData {
	final float startTime, endTime;
	final String title;
	final int day, month;
	final int weekDayNumber;
	private long rawData;

	public long getRawData() {
		return rawData;
	}

	public DrawData(String string, When ele) {
		Calendar now = Calendar.getInstance();
		rawData = ele.getStartTime().getValue();
		title = string;
		now.setTimeInMillis(ele.getStartTime().getValue());
		month = now.get(Calendar.MONTH) + 1;
		day = now.get(Calendar.DAY_OF_MONTH);
		weekDayNumber = getWeekDayNumber(now.get(Calendar.DAY_OF_WEEK));
		startTime = (now.get(Calendar.HOUR_OF_DAY))
				+ (((float) now.get(Calendar.MINUTE)) / 60);
		now.setTimeInMillis(ele.getEndTime().getValue());
		endTime = (now.get(Calendar.HOUR_OF_DAY))
				+ (((float) now.get(Calendar.MINUTE)) / 60);
	}

	private int getWeekDayNumber(int i) {
		switch (i) {
		case Calendar.SATURDAY:
			return 6;
		case Calendar.SUNDAY:
			return 7;
		}
		return i - 2;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return title + " " + month + "-" + day + ":" + weekDayNumber
				+ " start:" + startTime + " end:" + endTime + "\n";
	}
	
}
