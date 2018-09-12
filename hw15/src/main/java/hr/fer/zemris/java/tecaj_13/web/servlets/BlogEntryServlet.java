package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogCommentForm;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class represents servlet which renders page for getting new {@link BlogEntry}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet("/servleti/blogEntry")
public class BlogEntryServlet extends HttpServlet {

	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sID = req.getParameter("id");
		Long id = null;
		try {
			id = Long.valueOf(sID);
		} catch(Exception ignorable) {
		}
		
		if(id != null) {
			BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
			if(blogEntry != null) {
				req.setAttribute("blogEntry", blogEntry);
				
				String nick = blogEntry.getCreator().getNick();
				boolean creator = nick.equals(req.getSession().getAttribute("currentUserNick"));
				
				req.setAttribute("nick", nick);
				req.setAttribute("creator", creator);
				
				BlogCommentForm blogCommentForm = new BlogCommentForm();
				req.setAttribute("record", blogCommentForm);
			}
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/BlogEntryPage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		Long id = Long.parseLong(req.getParameter("id"));
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
		
		req.setAttribute("blogEntry", blogEntry);
		
		String nick = blogEntry.getCreator().getNick();
		boolean creator = nick.equals(req.getSession().getAttribute("currentUserNick"));
		
		req.setAttribute("nick", nick);
		req.setAttribute("creator", creator);
		
		BlogCommentForm blogCommentForm = new BlogCommentForm();
		blogCommentForm.fillFromHttpRequest(req);
		blogCommentForm.validate();
		
		if(blogCommentForm.containsErrors()) {
			req.setAttribute("record", blogCommentForm);
			
			req.getRequestDispatcher("/WEB-INF/pages/BlogEntryPage.jsp").forward(req, resp);
			
			return;
		}
		
		BlogComment blogComment = new BlogComment();
		
		blogCommentForm.fillBlogComment(blogComment);
		blogComment.setBlogEntry(blogEntry);
		blogComment.setPostedOn(new Date());
		blogComment.setUsersEMail((String) req.getSession().getAttribute("currentUserEmail"));
		
		DAOProvider.getDAO().addNewBlogComment(blogComment);

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/blogEntry?id=" + id);
	}	
}