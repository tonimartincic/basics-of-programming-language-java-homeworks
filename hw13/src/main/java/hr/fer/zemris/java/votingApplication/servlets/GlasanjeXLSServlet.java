package hr.fer.zemris.java.votingApplication.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Class extends {@link HttpServlet} and represents servlet
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="gxls", urlPatterns={"/glasanje-xls"})
public class GlasanjeXLSServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String, String> idName = (Map<String, String>) req.getSession().getAttribute("idName");
		
		@SuppressWarnings("unchecked")
		Map<String, Integer> idValue = (Map<String, Integer>) req.getSession().getAttribute("idNumOfVotesSorted");
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		HSSFSheet sheet =  hwb.createSheet("Rezultati");
		HSSFRow rowhead = sheet.createRow((short) 0);
		
		rowhead.createCell((short) 0).setCellValue("Id");
		rowhead.createCell((short) 0).setCellValue("Ime benda");
		rowhead.createCell((short) 0).setCellValue("Broj glasova");
		
		int rowNumber = 0;
		for(Map.Entry<String, Integer> entry : idValue.entrySet()) {
			HSSFRow row = sheet.createRow((short) rowNumber);
			
			row.createCell((short) 0).setCellValue(entry.getKey());
			row.createCell((short) 1).setCellValue(idName.get(entry.getKey()));
			row.createCell((short) 2).setCellValue(entry.getValue());
			
			rowNumber++;
		}
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=rezultati.xls");
		
		OutputStream outputStream = resp.getOutputStream();
		hwb.write(outputStream);
		hwb.close();
	}
}
