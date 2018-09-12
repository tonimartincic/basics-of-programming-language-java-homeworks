package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Class extends {@link HttpServlet} and represents servlet which generates excel table with
 * numbers and it powers.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="p", urlPatterns={"/powers"})
public class Powers extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Error message.
	 */
	private static String errorMessage;
	
	static {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Invalid arguments.<br />");
		sb.append("Three arguments expected;<br />");
		sb.append("Integer 'a' which value is in [-100, 100]<br />");
		sb.append("Integer 'b' which value is in [-100, 100]<br />");
		sb.append("Integer 'n' which value is in [1, 5]<br />");
		
		errorMessage = sb.toString();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a = null;
		Integer b = null;
		Integer n = null;
		
		try {
			a = Integer.valueOf(req.getParameter("a"));
			b = Integer.valueOf(req.getParameter("b"));
			n = Integer.valueOf(req.getParameter("n"));
		} catch(NumberFormatException ignorable) {
		}
		
		if(a == null || b == null || n == null) {
			req.getSession().setAttribute("errorMessage", errorMessage);
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
		boolean valid = areArgumentsValid(a, b, n);
		if(!valid) {
			req.getSession().setAttribute("errorMessage", errorMessage);
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
		if(a > b) {
			Integer temp = a;
			a = b;
			b = temp;
		}
		
		HSSFWorkbook hwb = getHSSFWorkbook(a, b, n);

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=powers.xls");
		
		OutputStream outputStream = resp.getOutputStream();
		hwb.write(outputStream);
		outputStream.flush();
		outputStream.close();
		
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

	/**
	 * Method creates and returnes instance of {@link HSSFWorkbook}. HSSFWorkbook contains numbers
	 * between accepted arguments a and b and their powers.
	 * 
	 * @param a first number
	 * @param b last number
	 * @param n number of powers
	 * @return instance of {@link HSSFWorkbook}
	 */
	private HSSFWorkbook getHSSFWorkbook(Integer a, Integer b, Integer n) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		for(int i = 1; i <= n; i++) {
			HSSFSheet sheet =  hwb.createSheet("x^" + i);
			
			HSSFRow rowhead = sheet.createRow((short) 0);
			
			rowhead.createCell((short) 0).setCellValue("x");
			rowhead.createCell((short) 1).setCellValue("x^" + i);
			
			for(int j = a, rowNumber = 1; j <= b; j++, rowNumber++) {
				HSSFRow row = sheet.createRow((short) rowNumber);
				
				row.createCell((short) 0).setCellValue(j);
				row.createCell((short) 1).setCellValue(Math.pow(j, i));
			}
		}
		
		return hwb;
	}

	/**
	 * Method checks if arguments are valid.
	 * 
	 * @param a first number
	 * @param b last number
	 * @param n number of powers
	 * @return true if arguments are valid, false otherwise
	 */
	private boolean areArgumentsValid(Integer a, Integer b, Integer n) {
		return a >= -100 && a <= 100 &&
			   b >= -100 && b <= 100 &&
			   n >= 1 && n <= 5;
	}
}
