package jp.lnc.java.time_report.gdata.calendar;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jp.lnc.java.time_report.gdata.calendar.EventFeed;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.extensions.Reminder.Method;
import com.google.gdata.util.ServiceException;


public class EventFeedTest {
	// The base URL for a user's calendar metafeed (needs a username appended).
	private final String METAFEED_URL_BASE = "https://www.google.com/calendar/feeds/";

	// The string to add to the user's metafeedUrl to access the event feed for
	// their primary calendar.
	private final String EVENT_FEED_URL_SUFFIX = "/private/full";
	/**
	 * Instantiates a CalendarService object and uses the command line arguments
	 * to authenticate. The CalendarService object is used to demonstrate
	 * interactions with the Calendar data API's event feed.
	 * 
	 * @param args
	 *            Must be length 2 and contain a valid username/password
	 */
	EventFeed ef = new EventFeed();
	public void eventAllTest(String[] args) {
		CalendarService myService = new CalendarService(
				"exampleCo-exampleApp-1");

		// Set username and password from command-line arguments.
		if (args.length != 2) {
			ef.usage();
			return;
		}

		String userName = args[0];
		String userPassword = args[1];

		// Create the necessary URL objects.
		try {
			ef.setMetafeedUrl(new URL(METAFEED_URL_BASE + userName));
			ef.setEventFeedUrl( new URL(METAFEED_URL_BASE + userName
					+ EVENT_FEED_URL_SUFFIX));
		} catch (MalformedURLException e) {
			// Bad URL
			System.err.println("Uh oh - you've got an invalid URL.");
			e.printStackTrace();
			return;
		}
		try {
			myService.setUserCredentials(userName, userPassword);

			// Demonstrate retrieving a list of the user's calendars.
			ef.printUserCalendars(myService);

			// Demonstrate various feed queries.
			System.out.println("Printing all events");
			ef.printAllEvents(myService);
			System.out.println("Full text query");
			ef.fullTextQuery(myService, "Tennis");

			ef.setEventFeedUrl(new URL(
					METAFEED_URL_BASE
							+ "lnc.jp_v9su6d8sm3dsbqea9nffr5eci0%40group.calendar.google.com"+ EVENT_FEED_URL_SUFFIX));

			ef.dateRangeQuery(myService, DateTime.parseDate("2011-09-05"),
					DateTime.parseDate("2011-09-11"));
			ef.setEventFeedUrl(new URL(
					METAFEED_URL_BASE
							+"japanese__ja%40holiday.calendar.google.com/public/basic"));
			
			// Demonstrate creating a single-occurrence event.
			CalendarEventEntry singleEvent = ef.createSingleEvent(myService,
					"Tennis with Mike", "Meet for a quick lesson.");
			System.out.println("Successfully created event "
					+ singleEvent.getTitle().getPlainText());

			// Demonstrate creating a quick add event.
			CalendarEventEntry quickAddEvent = ef.createQuickAddEvent(myService,
					"Tennis with John April 11 3pm-3:30pm");
			System.out.println("Successfully created quick add event "
					+ quickAddEvent.getTitle().getPlainText());

			// Demonstrate creating a web content event.
			CalendarEventEntry webContentEvent = ef.createWebContentEvent(
					myService, "World Cup", "image/gif",
					"http://www.google.com/logos/worldcup06.gif",
					"http://www.google.com/calendar/images/google-holiday.gif",
					"276", "120");
			System.out.println("Successfully created web content event "
					+ webContentEvent.getTitle().getPlainText());

			// Demonstrate creating a recurring event.
			CalendarEventEntry recurringEvent = ef.createRecurringEvent(myService,
					"Tennis with Dan", "Weekly tennis lesson.");
			System.out.println("Successfully created recurring event "
					+ recurringEvent.getTitle().getPlainText());

			// Demonstrate updating the event's text.
			singleEvent = ef.updateTitle(singleEvent, "Important meeting");
			System.out.println("Event's new title is \""
					+ singleEvent.getTitle().getPlainText() + "\".");

			// Demonstrate adding a reminder. Note that this will only work on a
			// primary calendar.
			singleEvent = ef.addReminder(singleEvent, 15, Method.EMAIL);
			System.out.println("Set a "
					+ singleEvent.getReminder().get(0).getMinutes()
					+ " minute " + singleEvent.getReminder().get(0).getMethod()
					+ " reminder for the event.");

			// Demonstrate adding an extended property.
			singleEvent = ef.addExtendedProperty(singleEvent);

			// Demonstrate deleting the entries with a batch request.
			List<CalendarEventEntry> eventsToDelete = new ArrayList<CalendarEventEntry>();
			eventsToDelete.add(singleEvent);
			eventsToDelete.add(quickAddEvent);
			eventsToDelete.add(webContentEvent);
			eventsToDelete.add(recurringEvent);
			ef.deleteEvents(myService, eventsToDelete);

		} catch (IOException e) {
			// Communications error
			System.err
					.println("There was a problem communicating with the service.");
			e.printStackTrace();
		} catch (ServiceException e) {
			// Server side error
			System.err
					.println("The server had a problem handling your request.");
			e.printStackTrace();
		}
	}
}
