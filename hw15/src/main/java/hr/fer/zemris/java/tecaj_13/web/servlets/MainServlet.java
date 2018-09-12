package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.NickAndPasswordForm;

/**
 * Class represents main servlet which renders main page of the application.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NickAndPasswordForm nickAndPasswordForm = new NickAndPasswordForm();
		req.setAttribute("record", nickAndPasswordForm);
		
		List<String> allNicks = DAOProvider.getDAO().getAllNicks();
		req.setAttribute("allNicks", allNicks);
		
		req.getRequestDispatcher("/WEB-INF/pages/MainPage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		NickAndPasswordForm nickAndPasswordForm = new NickAndPasswordForm();
		nickAndPasswordForm.fillFromHttpRequest(req);
		nickAndPasswordForm.validate();
		
		if(nickAndPasswordForm.containsErrors()) {
			List<String> allNicks = DAOProvider.getDAO().getAllNicks();
			req.setAttribute("allNicks", allNicks);
			
			req.setAttribute("record", nickAndPasswordForm);
			
			req.getRequestDispatcher("/WEB-INF/pages/MainPage.jsp").forward(req, resp);
			
			return;
		}
		
		BlogUser blogUser = DAOProvider.getDAO().getBlogUserByNick(nickAndPasswordForm.getNick());
		
		req.getSession().setAttribute("currentUserId", blogUser.getId());
		req.getSession().setAttribute("currentUserFn", blogUser.getFirstName());
		req.getSession().setAttribute("currentUserLn", blogUser.getLastName());
		req.getSession().setAttribute("currentUserNick", blogUser.getNick());	
		req.getSession().setAttribute("currentUserEmail", blogUser.getEmail());	
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
	
}
