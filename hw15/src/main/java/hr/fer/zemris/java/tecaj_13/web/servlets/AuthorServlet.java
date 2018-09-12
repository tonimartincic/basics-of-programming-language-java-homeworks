package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogEntryForm;

/**
 * Class represents servlet which renders page for adding new entries and editing existing entries.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		String[] parts = pathInfo.split("/");
		if(parts.length < 2) {
			req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
		}
		
		String nick = parts[1];
		
		boolean creator = nick.equals(req.getSession().getAttribute("currentUserNick"));
		req.setAttribute("creator", creator);
		
		if(parts.length == 2) {
			req.setAttribute("nick", nick);
			
			List<BlogEntry> blogEntries = DAOProvider.getDAO().getBlogEntriesByNick(nick);
			req.setAttribute("blogEntries", blogEntries);
			
			req.getRequestDispatcher("/WEB-INF/pages/BlogEntriesListPage.jsp").forward(req, resp);
		}
		
		if(parts.length == 3) {
			if(parts[2].equals("new")) {
				BlogEntryForm blogEntryForm = new BlogEntryForm();
				req.setAttribute("record", blogEntryForm);
				
				req.getRequestDispatcher("/WEB-INF/pages/NewEditBlogEntryPage.jsp").forward(req, resp);
			} else if(parts[2].equals("edit")) {
				Long id = Long.parseLong(req.getParameter("id"));
				BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
				
				BlogEntryForm blogEntryForm = new BlogEntryForm();
				blogEntryForm.fillFromBlogEntry(blogEntry);
				req.setAttribute("record", blogEntryForm);
				
				req.getRequestDispatcher("/WEB-INF/pages/NewEditBlogEntryPage.jsp").forward(req, resp);
			} else {
				Long eid = null;
				try {
					eid = Long.parseLong(parts[2]);
				} catch(NumberFormatException exc) {
					req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
				}
				
				BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(eid);
				req.setAttribute("blogEntry", blogEntry);
				
				req.getRequestDispatcher("/WEB-INF/pages/BlogEntryPage.jsp").forward(req, resp);
			}
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String pathInfo = req.getPathInfo();
		String[] parts = pathInfo.split("/");
		if(parts.length < 2) {
			req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
		}
		
		String nick = parts[1];
		
		req.setAttribute("nick", nick);
		
		boolean creator = nick.equals(req.getSession().getAttribute("currentUserNick"));
		req.setAttribute("creator", creator);
		
		String method = req.getParameter("method");
		if(!"Submit".equals(method)) {
			List<BlogEntry> blogEntries = DAOProvider.getDAO().getBlogEntriesByNick(nick);
			req.setAttribute("blogEntries", blogEntries);
			
			req.getRequestDispatcher("/WEB-INF/pages/BlogEntriesListPage.jsp").forward(req, resp);
			
			return;
		}
		
		BlogEntryForm blogEntryForm = new BlogEntryForm();
		blogEntryForm.fillFromHttpRequest(req);
		blogEntryForm.validate();
		
		if(blogEntryForm.containsErrors()) {
			req.setAttribute("record", blogEntryForm);
			
			req.getRequestDispatcher("/WEB-INF/pages/NewEditBlogEntryPage.jsp").forward(req, resp);
			
			return;
		}
		
		//adding new entry
		if(req.getParameter("id").isEmpty()) {
			BlogEntry blogEntry = new BlogEntry();
			blogEntryForm.fillBlogEntry(blogEntry);
			
			blogEntry.setCreatedAt(new Date());
			blogEntry.setCreator(DAOProvider.getDAO().getBlogUserByNick((String) req.getSession().getAttribute("currentUserNick")));
			
			DAOProvider.getDAO().addNewBlogEntry(blogEntry);
		//editing entry
		} else {
			BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(req.getParameter("id")));
			
			blogEntry.setTitle(blogEntryForm.getTitle());
			blogEntry.setText(blogEntryForm.getText());
			blogEntry.setLastModifiedAt(new Date());
		}
		
		List<BlogEntry> blogEntries = DAOProvider.getDAO().getBlogEntriesByNick(nick);
		req.setAttribute("blogEntries", blogEntries);
		
		req.getRequestDispatcher("/WEB-INF/pages/BlogEntriesListPage.jsp").forward(req, resp);
	}
}
