package jp.lnc.java.time_report.gdata.calendar;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import jp.lnc.java.time_report.drow.TaskListGetter;
import jp.lnc.java.time_report.drow.TemplateDraw;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.ServiceException;

public class TaskListGetterTest {

	private TaskListGetter taskGetter;
	private DateTime startTime;
	private DateTime endTime;
	private String userName, passwd;
	// The base URL for a user's calendar metafeed (needs a username appended).
	private final String METAFEED_URL_BASE = "https://www.google.com/calendar/feeds/";

	// The string to add to the user's metafeedUrl to access the event feed for
	// their primary calendar.
	private final String EVENT_FEED_URL_SUFFIX = "/private/full";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		userName = "bols-blue@lnc.jp";
		passwd = "kagura%$%";
		taskGetter = new TaskListGetter(userName, passwd);
		startTime = DateTime.parseDate("2011-09-05");
		endTime = DateTime.parseDate("2011-09-11");
	}

	@Test
	public void testPrintUserCalendars() {
		taskGetter.printUserCalendars();
	}

	@Test
	public void testTaskListGetter() {
		taskGetter = new TaskListGetter("bols-blue@lnc.jp", "kagura%$%");
	}

	@Test
	public void testDateRangeQuery() {
		try {
			taskGetter
					.printDateRangeQuery(
							new URL(
									METAFEED_URL_BASE
											+ "lnc.jp_v9su6d8sm3dsbqea9nffr5eci0%40group.calendar.google.com"
											+ EVENT_FEED_URL_SUFFIX),
							startTime, endTime);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDateRangeQueryPlusHoriday() {
		try {
			taskGetter
					.dateRangeQueryPlusHoriday(
							new URL(
									METAFEED_URL_BASE
											+ "lnc.jp_v9su6d8sm3dsbqea9nffr5eci0%40group.calendar.google.com"
											+ EVENT_FEED_URL_SUFFIX),
							startTime, endTime);
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

	@Test
	public void testDateRangeQuerySingleDay() {
		try {
			URL url = new URL(
					METAFEED_URL_BASE
							+ "lnc.jp_v9su6d8sm3dsbqea9nffr5eci0%40group.calendar.google.com"
							+ EVENT_FEED_URL_SUFFIX);

			for (int i = 0; i < 7; i++) {
				String s = String.format("2011-%02d-%02d", 9, (5 + i));
				DateTime sampleTime = DateTime.parseDate(s);
				s = String.format("2011-%02d-%02d", 9, (5 + i + 1));
				DateTime sampleEndTime = DateTime.parseDate(s);
				List<CalendarEventEntry> entryList = taskGetter.dateRangeQuery(
						url, sampleTime, sampleEndTime);

				for (int j = 0; j < entryList.size(); j++) {
					CalendarEventEntry entry = entryList.get(j);
					Iterator<When> it = entry.getTimes().iterator();
					String timeString = "\t\t";
					while (it.hasNext()) {
						When ele = it.next();
						Calendar now = Calendar.getInstance();
						now.setTimeInMillis(ele.getStartTime().getValue());
						timeString += "\t" + now.get(Calendar.YEAR) + "-"
								+ now.get(Calendar.MONTH) + "-"
								+ (now.get(Calendar.DAY_OF_MONTH)) + "\t"
								+ (now.get(Calendar.HOUR_OF_DAY)) + ":"
								+ (now.get(Calendar.MINUTE));
						now.setTimeInMillis(ele.getEndTime().getValue());
						timeString += "\t" + now.get(Calendar.YEAR) + "-"
								+ now.get(Calendar.MONTH) + "-"
								+ (now.get(Calendar.DAY_OF_MONTH) ) + "\t"
								+ (now.get(Calendar.HOUR_OF_DAY)) + ":"
								+ (now.get(Calendar.MINUTE));

					}
					System.out.println("\t" + entry.getTitle().getPlainText()
							+ timeString);
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

	@Test
	public void testCreateDrawdData() {
		try {
			URL url = new URL(
					METAFEED_URL_BASE
							+ "lnc.jp_v9su6d8sm3dsbqea9nffr5eci0%40group.calendar.google.com"
							+ EVENT_FEED_URL_SUFFIX);

			String s = String.format("2011-%02d-%02d", 9, (5));
			DateTime sampleTime = DateTime.parseDate(s);
			s = String.format("2011-%02d-%02d", 9, 11);
			DateTime sampleEndTime = DateTime.parseDate(s);

			DrawDataContainer container = new DrawDataContainer(taskGetter.dateRangeQuery(url, sampleTime,
					sampleEndTime));
			System.out.println(container.toString());
			container.sortList();
			System.out.println(container.toString());
		} catch (ServiceException e) {
			e.printStackTrace();
			fail("Not yet implemented");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}
	@Test
	public void testDrawData() {
		try {
			URL url = new URL(
					METAFEED_URL_BASE
							+ "lnc.jp_v9su6d8sm3dsbqea9nffr5eci0%40group.calendar.google.com"
							+ EVENT_FEED_URL_SUFFIX);

			String s = String.format("2011-%02d-%02d", 9, (5));
			DateTime sampleTime = DateTime.parseDate(s);
			s = String.format("2011-%02d-%02d", 9, 11);
			DateTime sampleEndTime = DateTime.parseDate(s);

			DrawDataContainer container = new DrawDataContainer(taskGetter.dateRangeQuery(url, sampleTime,
					sampleEndTime));
			System.out.println(container.toString());
			container.sortList();
			System.out.println(container.toString());
			TemplateDraw test = new TemplateDraw("src/test/resources/testDrawData.png");
			test.paintTest();
			container.drowContener(test);
			test.save();
		} catch (ServiceException e) {
			e.printStackTrace();
			fail("Not yet implemented");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}
	@Test
	public void testWriteData() {
		try {
			URL url = new URL(
					METAFEED_URL_BASE
							+ "lnc.jp_v9su6d8sm3dsbqea9nffr5eci0%40group.calendar.google.com"
							+ EVENT_FEED_URL_SUFFIX);

			String s = String.format("2011-%02d-%02d", 9, (5));
			DateTime sampleTime = DateTime.parseDate(s);
			s = String.format("2011-%02d-%02d", 9, 11);
			DateTime sampleEndTime = DateTime.parseDate(s);

			DrawDataContainer container = new DrawDataContainer(taskGetter.dateRangeQuery(url, sampleTime,
					sampleEndTime));
//			System.out.println(container.toString());
			container.sortList();
//			System.out.println(container.toString());
			TemplateTextWriter test = new TemplateTextWriter("src/test/resources/testDrawData.txt");
			container.drowContener(test);
			test.save();
		} catch (ServiceException e) {
			e.printStackTrace();
			fail("Not yet implemented");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}
	@Test
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

					Calendar now = Calendar.getInstance();
					now.setTimeInMillis(ele.getStartTime().getValue());
					timeString += "\t" + now.get(Calendar.YEAR) + "-"
							+ now.get(Calendar.MONTH) + "-"
							+ (now.get(Calendar.DAY_OF_MONTH) + 1) + "\t"
							+ (now.get(Calendar.HOUR_OF_DAY)) + ":"
							+ (now.get(Calendar.MINUTE));
					now.setTimeInMillis(ele.getEndTime().getValue());
					timeString += "\t" + now.get(Calendar.YEAR) + "-"
							+ now.get(Calendar.MONTH) + "-"
							+ (now.get(Calendar.DAY_OF_MONTH) + 1) + "\t"
							+ (now.get(Calendar.HOUR_OF_DAY)) + ":"
							+ (now.get(Calendar.MINUTE));

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

	@Test
	public void testGetHolidayEntry() {
		taskGetter.getHolidayEntry(startTime, endTime);
	}
}
