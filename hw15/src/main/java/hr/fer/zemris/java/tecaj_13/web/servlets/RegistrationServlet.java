package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.BlogUserForm;

/**
 * Class represents servlet which renders page for the registration.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet("/servleti/register")
public class RegistrationServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUser blogUser = new BlogUser();
		BlogUserForm blogUserForm = new BlogUserForm();
		blogUserForm.fillFromBlogUser(blogUser);
		
		req.setAttribute("record", blogUserForm);
		
		req.getRequestDispatcher("/WEB-INF/pages/RegistrationPage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String method = req.getParameter("method");
		if(!"Register".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

		BlogUserForm f = new BlogUserForm();
		f.fillFromHttpRequest(req);
		f.validate();
		
		if(f.containsErrors()) {
			req.setAttribute("record", f);
			req.getRequestDispatcher("/WEB-INF/pages/RegistrationPage.jsp").forward(req, resp);
			
			return;
		}
		
		BlogUser blogUser = new BlogUser();
		f.fillBlogUser(blogUser);
		
		DAOProvider.getDAO().registerNewBlogUser(blogUser);
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
	
}
