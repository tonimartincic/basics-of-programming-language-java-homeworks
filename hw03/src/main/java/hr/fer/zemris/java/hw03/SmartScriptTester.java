package hr.fer.zemris.java.hw03;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;


import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class tests functionality of the SmartScriptParser.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */


public class SmartScriptTester {
	
	/**
	 * The method that starts the execution of this program.
	 * 
	 * @param args command line arguments, unused
	 */
	public static void main(String[] args) {
		
		String filepath = "examples/doc2.txt";
		
		String docBody = "";
		try {
			docBody = new String(
					 Files.readAllBytes(Paths.get(filepath)),
					 StandardCharsets.UTF_8
					);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		SmartScriptParser parser = null;
		try {
		 parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
		 System.out.println("Unable to parse document!");
		 System.exit(-1);
		} catch(Exception e) {
		 System.out.println("If this line ever executes, you have failed this class!");
		 System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = DocumentNode.createOriginalDocumentBody(document);
		
		System.out.println(docBody);
		
		System.out.println("-----------------------------------");
		
		System.out.println(originalDocumentBody); // should write something like original content of docBody
	}

	

}
