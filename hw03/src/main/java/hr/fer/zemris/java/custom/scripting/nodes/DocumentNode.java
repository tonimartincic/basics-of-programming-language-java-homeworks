package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * A node representing an entire document. It inherits from Node class.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class DocumentNode extends Node {
	
	/**
	 * Method creates String which is orginal document body from document model.
	 * 
	 * @param document DocumentNode of document model which document body is created
	 * @return String which is orginal document body
	 */
	public static String createOriginalDocumentBody(DocumentNode document) {
		String orginalDocumentBody = "";
		
		for(int i = 0; i < document.numberOfChildren(); i++) {
			if(document.getChild(i) instanceof TextNode) {
				TextNode textNode = (TextNode) document.getChild(i);
				
				char[] charArray = textNode.getText().toCharArray();
				for(int j = 0; j < charArray.length; j++) {
					if(charArray[j] == '\\' || charArray[j] == '{') {
						orginalDocumentBody += "\\";
					}
					orginalDocumentBody += charArray[j];
				}
				
				continue;
			}
			
			if(document.getChild(i) instanceof EchoNode) {
				EchoNode echoNode = (EchoNode) document.getChild(i);
				Element[] elements = echoNode.getElements();
				
				orginalDocumentBody += "{$= ";
				for(Element element : elements) {
					orginalDocumentBody += element.asText() + " ";
				}
				orginalDocumentBody += "$}";
				
				continue;
			}
			
			if(document.getChild(i) instanceof ForLoopNode) {
				ForLoopNode forLoopNode = (ForLoopNode) document.getChild(i);
				
				Element[] elements = { forLoopNode.getVariable(),
									   forLoopNode.getStartExpression(),
									   forLoopNode.getEndExpression(),
									   forLoopNode.getStepExpression()
				};
				
				orginalDocumentBody += "{$ FOR ";
				for(Element element : elements) {
					orginalDocumentBody += element.asText() + " ";
				}
				orginalDocumentBody += "$}";
				
				orginalDocumentBody += createDocumentForLoop(forLoopNode);
				
				orginalDocumentBody += "{$ END $}";
			}
		}
		
		return orginalDocumentBody;
	}
	
	@SuppressWarnings("javadoc")
	private static String createDocumentForLoop(ForLoopNode forLoopNode) {
		String orginalDocumentBody = "";
		
		for(int i = 0; i < forLoopNode.numberOfChildren(); i++) {
			if(forLoopNode.getChild(i) instanceof TextNode) {
				TextNode textNode = (TextNode) forLoopNode.getChild(i);
				
				char[] charArray = textNode.getText().toCharArray();
				for(int j = 0; j < charArray.length; j++) {
					if(charArray[j] == '\\' || charArray[j] == '{') {
						orginalDocumentBody += "\\";
					}
					orginalDocumentBody += charArray[j];
				}
				
				continue;
			}
			
			if(forLoopNode.getChild(i) instanceof EchoNode) {
				EchoNode echoNode = (EchoNode) forLoopNode.getChild(i);
				Element[] elements = echoNode.getElements();
				
				orginalDocumentBody += "{$= ";
				for(int j = 0; j < elements.length; j++) {
					if(elements[j] instanceof ElementFunction) {
						orginalDocumentBody += '@';
						orginalDocumentBody += elements[j].asText() + " ";
						continue;
					}
					
					if(elements[j] instanceof ElementString) {
						orginalDocumentBody += '"';
						orginalDocumentBody += elements[j].asText();
						orginalDocumentBody += '"' + " ";
						continue;
					}
					
					orginalDocumentBody += elements[j].asText() + " ";
				}
				orginalDocumentBody += "$}";
				
				continue;
			}
			
			if(forLoopNode.getChild(i) instanceof ForLoopNode) {
				ForLoopNode forLoopNode2 = (ForLoopNode) forLoopNode.getChild(i);
				
				Element[] elements = { forLoopNode2.getVariable(),
						   forLoopNode2.getStartExpression(),
						   forLoopNode2.getEndExpression(),
						   forLoopNode2.getStepExpression()
				};
							
				orginalDocumentBody += "{$ FOR ";
				for(int j = 0; j < elements.length; j++) {
					if(elements[j] instanceof ElementString) {
						orginalDocumentBody += '"';
						orginalDocumentBody += elements[j].asText();
						orginalDocumentBody += '"' + " ";
						continue;
					}
					
					orginalDocumentBody += elements[j].asText() + " ";
				}
				orginalDocumentBody += "$}";
				
				orginalDocumentBody += createDocumentForLoop(forLoopNode2);
				
				orginalDocumentBody += "{$ END $}";
			}
		}
		
		return orginalDocumentBody;
	}

}
