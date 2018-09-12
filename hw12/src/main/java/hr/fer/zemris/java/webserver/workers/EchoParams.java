package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class implements {@link IWebWorker} and represents worker which creates html table of accepted 
 * arguments
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		
		try {
			context.write("<html><body>");
			context.write(" <table  border=\"5\">");
			
			for(String parameterName : context.getParameterNames()) {
				context.write("  <tr>");
				 
				context.write("   <td>" + parameterName + "<td>");
				context.write("   <td>" + context.getParameter(parameterName) + "<td>");
				
				context.write("  <tr>");
			}
			
			context.write(" <table>");
			context.write("<html><body>");
		} catch (IOException exc) {
			System.out.println("Erorr: " + exc.getMessage());
		}	
	}
	
}
