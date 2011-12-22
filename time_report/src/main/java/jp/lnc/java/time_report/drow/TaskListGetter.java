package jp.lnc.java.time_report.drow;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class TaskListGetter {
	// The base URL for a user's calendar metafeed (needs a username appended).
	private final String METAFEED_URL_BASE = "https://www.google.com/calendar/feeds/";

	// The string to add to the user's metafeedUrl to access the event feed for
	// their primary calendar.
	private final String EVENT_FEED_URL_SUFFIX = "/private/full";
	// The URL for the metafeed of the specified user.
	// (e.g. http://www.google.com/feeds/calendar/jdoe@gmail.com)
	private URL metafeedUrl = null;

	private CalendarService myService;

	/**
	 * Prints a list of all the user's calendars.
	 * 
	 * @throws ServiceException
	 *             If the service is unable to handle the request.
	 * @throws IOException
	 *             Error communicating with the server
	 */
	public void printUserCalendars() {
		// Send the request and receive the response:
		CalendarFeed resultFeed;
		try {
			resultFeed = myService.getFeed(metafeedUrl, CalendarFeed.class);

			System.out.println("Your calendars:");
			System.out.println();
			for (int i = 0; i < resultFeed.getEntries().size(); i++) {
				CalendarEntry entry = resultFeed.getEntries().get(i);

				System.out.println("\t" + entry.getTitle().getPlainText());
			}
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public TaskListGetter(String userName, String userPassword) {
		myService = new CalendarService("exampleCo-exampleApp-1");

		// Create the necessary URL objects.
		setUserMetafeedUrl(userName);

		try {
			myService.setUserCredentials(userName, userPassword);
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Prints the titles of all events in a specified date/time range.
	 * 
	 * @param service
	 *            An authenticated CalendarService object.
	 * @param startTime
	 *            Start time (inclusive) of events to print.
	 * @param endTime
	 *            End time (exclusive) of events to print.
	 * @throws ServiceException
	 *             If the service is unable to handle the request.
	 * @throws IOException
	 *             Error communicating with the server.
	 */
	public void printDateRangeQuery(URL feedUrl, DateTime startTime,
			DateTime endTime) throws ServiceException, IOException {
		CalendarQuery myQuery = new CalendarQuery(feedUrl);
		myQuery.setMinimumStartTime(startTime);
		myQuery.setMaximumStartTime(endTime);

		// Send the request and receive the response:
		CalendarEventFeed resultFeed = myService.query(myQuery,
				CalendarEventFeed.class);

		System.out.println("Events from " + startTime.toString() + " to "
				+ endTime.toString() + ":");
		System.out.println();
		for (int i = 0; i < resultFeed.getEntries().size(); i++) {
			CalendarEventEntry entry = resultFeed.getEntries().get(i);
			Iterator<When> it = entry.getTimes().iterator();
			String timeString = "\t\t";
			while (it.hasNext()) {
				When ele = it.next();
				timeString += "\t" + ele.getStartTime();
				timeString += "\t" + ele.getEndTime();
			}
			System.out.println("\t" + entry.getTitle().getPlainText()
					+ timeString);
		}
		System.out.println();
	}

	public List<CalendarEventEntry> dateRangeQuery(URL feedUrl,
			DateTime startTime, DateTime endTime) throws ServiceException,
			IOException {
		CalendarQuery myQuery = new CalendarQuery(feedUrl);
		myQuery.setMinimumStartTime(startTime);
		myQuery.setMaximumStartTime(endTime);

		// Send the request and receive the response:
		CalendarEventFeed resultFeed = myService.query(myQuery,
				CalendarEventFeed.class);
		return resultFeed.getEntries();
	}

	/**
	 * Prints the titles of all events in a specified date/time range.
	 * 
	 * @param service
	 *            An authenticated CalendarService object.
	 * @param startTime
	 *            Start time (inclusive) of events to print.
	 * @param endTime
	 *            End time (exclusive) of events to print.
	 * @throws ServiceException
	 *             If the service is unable to handle the request.
	 * @throws IOException
	 *             Error communicating with the server.
	 */
	public void dateRangeQueryPlusHoriday(URL eventFeedUrl, DateTime startTime,
			DateTime endTime) throws ServiceException, IOException {
		//
		printDateRangeQuery(eventFeedUrl, startTime, endTime);
		printDateRangeQuery(new URL(METAFEED_URL_BASE
				+ "japanese__ja@holiday.calendar.google.com/public/basic"),
				startTime, endTime);
	}

	public List<CalendarEventEntry> getHolidayEntry(DateTime startTime,
			DateTime endTime) {
		// インスタンス化
		Calendar now = Calendar.getInstance();

		now.setTimeInMillis(startTime.getValue());
		String[] week = new String[7];
		week[0] = "日";
		week[1] = "月";
		week[2] = "火";
		week[3] = "水";
		week[4] = "木";
		week[5] = "金";
		week[6] = "土";

		int week_int = now.get(Calendar.DAY_OF_WEEK);// 曜日を数値で取得

		// 曜日を表示
		System.out.println(now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1) +"-"+ (now.get(Calendar.DAY_OF_MONTH)) + ":" + week[week_int - 1] + "曜日");
		try {
			return dateRangeQuery(new URL(METAFEED_URL_BASE
					+ "japanese__ja@holiday.calendar.google.com/public/basic"),
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
		return null;
	}

	private void setUserMetafeedUrl(String userName) {
		try {
			metafeedUrl = new URL(METAFEED_URL_BASE + userName);
		} catch (MalformedURLException e) {
			System.err.println("Uh oh - you've got an invalid URL.");
			e.printStackTrace();
			return;
		}
	}
}