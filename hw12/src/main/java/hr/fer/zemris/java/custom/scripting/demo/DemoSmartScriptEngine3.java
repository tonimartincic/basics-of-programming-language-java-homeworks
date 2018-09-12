package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Program runs the "brojPoziva.smscr" script with {@link SmartScriptEngine}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class DemoSmartScriptEngine3 {

	/**
	 * Method from which program starts.
	 * 
	 * @param args arguments of the command line, unused
	 */
	public static void main(String[] args) {
		
		String documentBody = readFromDisk("brojPoziva.smscr");
		if(documentBody == null) {
			return;
		}
		
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,cookies);
		
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(), rc
		).execute();
		
		System.out.println("Vrijednost u mapi: "+rc.getPersistentParameter("brojPoziva"));
	}

	/**
	 * Method reads file from disk.
	 * 
	 * @param fileName name of the file
	 * @return file content
	 */
	private static String readFromDisk(String fileName) {
		Path path = Paths.get(fileName);
		
		if (!Files.exists(path)) {
			System.out.println("File does not exist");

			return null;
		}
		
		String docBody = "";
		try {
			docBody = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
		} catch (IOException exc) {
			System.out.println("Error while reading from file");

			return null;
		}

		return docBody;
	}

}
