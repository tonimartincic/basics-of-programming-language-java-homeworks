package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class tests the functionality of the {@link RequestContext}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class DemoRequestContext {
	
	/**
	 * Main method from which program starts.
	 * 
	 * @param args arguments of the command line, unused
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");
	}

	/**
	 * First demo method.
	 * 
	 * @param filePath file path
	 * @param encoding encoding
	 * @throws IOException
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(
				os, 
				new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}

	/**
	 * Second demo method.
	 * 
	 * @param filePath file path 
	 * @param encoding encoding
	 * @throws IOException
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(
				os, 
				new HashMap<String, String>(), 
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/", "HttpOnly"));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/", "HttpOnly"));
		
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
}
