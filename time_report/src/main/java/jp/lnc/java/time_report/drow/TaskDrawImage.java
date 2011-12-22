package jp.lnc.java.time_report.drow;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import jp.lnc.java.time_report.drow.TaskListGetter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.ServiceException;

public class TaskDrawImage {

	private TaskListGetter taskGetter;
	private DateTime startTime;
	private DateTime endTime;
	private String userName, passwd;
	// The base URL for a user's calendar metafeed (needs a username appended).
	private final String METAFEED_URL_BASE = "https://www.google.com/calendar/feeds/";

	// The string to add to the user's metafeedUrl to access the event feed for
	// their primary calendar.
	private final String EVENT_FEED_URL_SUFFIX = "/private/full";
	private TemplateDraw test;

	public TaskDrawImage() {
		userName = "bols-blue@lnc.jp";
		passwd = "kagura%$%";
		taskGetter = new TaskListGetter(userName, passwd);
		startTime = DateTime.parseDate("2011-09-05");
		endTime = DateTime.parseDate("2011-09-11");
		test = new TemplateDraw("src/test/resources/test2.png");
		test.paintTest();
	}

	public void testDrawSingleDay() {
		try {
			URL url = new URL(
					METAFEED_URL_BASE
							+ "lnc.jp_v9su6d8sm3dsbqea9nffr5eci0%40group.calendar.google.com"
							+ EVENT_FEED_URL_SUFFIX);

			String s = String.format("2011-%02d-%02d", 9, (5));
			DateTime sampleTime = DateTime.parseDate(s);
			s = String.format("2011-%02d-%02d", 9, (5 + 1));
			DateTime sampleEndTime = DateTime.parseDate(s);
			List<CalendarEventEntry> entryList = taskGetter.dateRangeQuery(url,
					sampleTime, sampleEndTime);

			for (int j = 0; j < entryList.size(); j++) {
				CalendarEventEntry entry = entryList.get(j);
				Iterator<When> it = entry.getTimes().iterator();
				String timeString = "\t\t";
				while (it.hasNext()) {
					When ele = it.next();
					Calendar start = Calendar.getInstance();
					start.setTimeInMillis(ele.getStartTime().getValue());

					Calendar stop = Calendar.getInstance();
					stop.setTimeInMillis(ele.getEndTime().getValue());
					test.drowEventTineLine(j, 0,
							start,stop);

				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
			fail("Not yet implemented");
		} catch (ServiceException e) {
			e.printStackTrace();
			fail("Not yet implemented");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

}
