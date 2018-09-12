package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class writes document to the standard output.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class TreeWriter {

	/**
	 * Method from which program starts.
	 * 
	 * @param args arguments of the command line, argument is file name
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("File name expected as argument.");

			return;
		}

		Path path = Paths.get(args[0]);

		if (!Files.exists(path)) {
			System.out.println("File does not exist");

			return;
		}

		String docBody = "";
		try {
			docBody = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
		} catch (IOException exc) {
			System.out.println("Error while reading from file");

			return;
		}

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException exc) {
			exc.printStackTrace();
			System.out.println("Unable to parse document!");

			return;
		}

		WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor);
	}

	/**
	 * Class implement {@link INodeVisitor} and prints document to the standard output.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			StringBuilder sb = new StringBuilder();

			char[] charArray = node.getText().toCharArray();
			for (int j = 0; j < charArray.length; j++) {
				if (charArray[j] == '\\' || charArray[j] == '{') {
					sb.append("\\");
				}
				sb.append(charArray[j]);
			}

			System.out.print(sb.toString());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			Element[] elements = { 
					node.getVariable(),
					node.getStartExpression(),
					node.getEndExpression(),
					node.getStepExpression() };

			System.out.print("{$ FOR ");
			
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < elements.length; j++) {
				if (elements[j] instanceof ElementString) {
					sb.append(appendString(elements[j]));
					
					continue;
				}

				sb.append(elements[j].asText() + " ");
			}
			
			sb.append("$}");
			
			System.out.print(sb.toString());

			createDocumentForLoop(node);

			System.out.print("{$ END $}");
		}


		@Override
		public void visitEchoNode(EchoNode node) {
			StringBuilder sb = new StringBuilder();

			Element[] elements = node.getElements();

			sb.append("{$= ");
			for (int j = 0; j < elements.length; j++) {
				if (elements[j] instanceof ElementFunction) {
					sb.append('@');
					sb.append(elements[j].asText() + " ");

					continue;
				}

				if (elements[j] instanceof ElementString) {
					sb.append(appendString(elements[j]));

					continue;
				}

				sb.append(elements[j].asText() + " ");
			}
			
			sb.append("$}");

			System.out.print(sb.toString());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, n = node.numberOfChildren(); i < n; i++) {
				node.getChild(i).accept(this);
			}
		}
		
		/**
		 * Method creates document for loop
		 * 
		 * @param node accepted node
		 */
		private void createDocumentForLoop(ForLoopNode node) {
			for(int i = 0, n = node.numberOfChildren(); i < n; i++) {
				node.getChild(i).accept(this);
			}
		}
		
		/**
		 * Method appends string.
		 * 
		 * @param element accepted element
		 * @return string
		 */
		private String appendString(Element element) {
			StringBuilder sb = new StringBuilder();
			
			if (element.asText().equals("\r\n")) {
				sb.append("\r\n");

				return sb.toString();
			}

			sb.append("\"");

			char[] charArray = element.asText().toCharArray();

			for (int k = 0; k < charArray.length; k++) {
				if (charArray[k] == '\\' && charArray[k + 1] == '\"') {
					sb.append("\\" + "\"");

					k++;
				} else if (charArray[k] == '\\' && charArray[k + 1] == '\"') {
					sb.append("\\" + "\\");

					k++;
				} else if (charArray[k] == '\r' && charArray[k + 1] == '\n') {
					sb.append("\r" + "\n");

					k++;
				} else {
					sb.append(charArray[k]);
				}
			}

			sb.append("\"" + " ");


			return sb.toString();
		}

	}
}
