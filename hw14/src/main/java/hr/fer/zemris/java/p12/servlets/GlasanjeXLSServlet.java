package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Class extends {@link HttpServlet} and represents servlet which gets results of the poll as xls file.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="gxls", urlPatterns={"/servleti/glasanje-xls"})
public class GlasanjeXLSServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		List<PollOption> pollOptions = (List<PollOption>) req.getSession().getAttribute("pollOptions");
	
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		HSSFSheet sheet =  hwb.createSheet("Rezultati");
		HSSFRow rowhead = sheet.createRow((short) 0);
		
		rowhead.createCell((short) 0).setCellValue("Id");
		rowhead.createCell((short) 1).setCellValue("Opcija");
		rowhead.createCell((short) 2).setCellValue("Broj glasova");
		
		for(int i = 0, n = pollOptions.size(); i < n; i++) {
			HSSFRow row = sheet.createRow((short) i + 1);
			
			row.createCell((short) 0).setCellValue(pollOptions.get(i).getId());
			row.createCell((short) 1).setCellValue(pollOptions.get(i).getOptionTitle());
			row.createCell((short) 2).setCellValue(pollOptions.get(i).getVotesCount());
		}
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=rezultati.xls");
		
		OutputStream outputStream = resp.getOutputStream();
		hwb.write(outputStream);
		hwb.close();
	}
}
