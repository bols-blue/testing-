package jp.lnc.java.time_report.gdata.calendar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import jp.lnc.java.time_report.drow.TemplateDraw;
import jp.lnc.java.time_report.drow.TemplateWriter;

import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.extensions.When;

public class DrawDataContainer {
	private Color mBoxColor[] = { Color.BLACK, Color.YELLOW, Color.ORANGE,
			Color.PINK, Color.CYAN };
	ArrayList<DrawData> mDataList = new ArrayList<DrawData>();

	public DrawDataContainer(List<CalendarEventEntry> dateRangeQuery) {
		for (int j = 0; j < dateRangeQuery.size(); j++) {
			CalendarEventEntry entry = dateRangeQuery.get(j);
			Iterator<When> it = entry.getTimes().iterator();
			while (it.hasNext()) {
				mDataList.add(new DrawData(entry.getTitle().getPlainText(), it
						.next()));
			}
		}
	}

	void sortList() {
		Collections.sort(mDataList, new MyComparator());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mDataList.toString();
	}

	public void drowContener(TemplateWriter test) {
		int drawDay = 0;
		int eventNumber = 1;
		Iterator<DrawData> it = mDataList.iterator();
		while (it.hasNext()) {

			DrawData data = it.next();
			if (drawDay == data.day) {
				eventNumber = eventNumber + 1;
			} else {
				eventNumber = 1;
			}
			drawDay = data.day;
			drowContener(test,data,eventNumber);
		}

	}

	public void drowContener(TemplateWriter test,DrawData data,int eventNumber) {

		test.drowTineBox(data.weekDayNumber, data.startTime, data.endTime,
				mBoxColor[eventNumber], eventNumber);
	}
	
	public void drowContener(TemplateTextWriter test,DrawData data,int eventNumber) {
		// TODO Auto-generated method stub
		
	}
}

class MyComparator implements Comparator<DrawData> {

	public int compare(DrawData o1, DrawData o2) {
		if (o1.getRawData() > o2.getRawData()) {
			return 1;
		} else if (o1.getRawData() < o2.getRawData()) {
			return -1;
		} else {
			return 0;
		}
	}

}