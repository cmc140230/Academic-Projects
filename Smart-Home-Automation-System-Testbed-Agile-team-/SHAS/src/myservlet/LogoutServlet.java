package myservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public LogoutServlet()
    {
    	System.out.println("inside logout servlet constructor");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
        System.out.println("User="+session.getAttribute("user"));
        if(session != null)
        {
            session.invalidate();
        }
        response.sendRedirect(getServletContext().getContextPath()+"/jsp/index.jsp");
	}

}
