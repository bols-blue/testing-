import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

public class Main {

	public static void main(String[] args) {
		try {
			// Create the client resource
			ClientResource resource = new ClientResource(
					"http://www.restlet.org");

			// Customize the referrer property
			resource.setReferrerRef("http://www.mysite.org");

			// Write the response entity on the console
			Representation tmp = resource.get(MediaType.TEXT_HTML);
			System.out.println(tmp.getMediaType());
			
			tmp.write(System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
