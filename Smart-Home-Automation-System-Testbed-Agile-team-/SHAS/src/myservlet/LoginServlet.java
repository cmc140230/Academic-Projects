package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbo.DataService;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginServlet() {
        
    	System.out.println("inside login servlet constructor");
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String path=request.getContextPath();
		System.out.println(path);
		
		response.setContentType("text/html");
		
		String loginQuery = "select user_id,password from user where username='"+username+"'";
		boolean empty=true;
		System.out.println(loginQuery);
		try 
		{
			ResultSet rs = DataService.getResultSet(loginQuery);
			
			while(rs.next())
			{
				empty=false;
				String user_id = rs.getString("user_id");
				String fetchedPassword = rs.getString("password");
				if(fetchedPassword.equals(password))
				{
					System.out.println("successful Login");
					HttpSession session = request.getSession();
					session.setAttribute("user", username);
					session.setAttribute("user_id", user_id);
					
					String query="select shas_id,device from shas";
					ResultSet deviceList = DataService.getResultSet(query);
					
					while(deviceList.next())
					{
					 
						String shas_id=((Integer)deviceList.getInt("shas_id")).toString();
						String device=deviceList.getString("device");
						session.setAttribute(device,shas_id);
					 
					}
				
					response.sendRedirect(getServletContext().getContextPath()+"/jsp/index.jsp");
				}
				else
				{
					request.setAttribute("error","Invalid Username/Password");
					RequestDispatcher rdInvalidValidUser = request.getRequestDispatcher("/jsp/login.jsp?error=invalidUser");
					rdInvalidValidUser.include(request,response);
				}
			}
			if(empty)
			{
			
				request.setAttribute("error","Invalid Username/Password");
				RequestDispatcher rdInvalidValidUser = request.getRequestDispatcher("/jsp/login.jsp?error=invalidUser");
				rdInvalidValidUser.include(request,response);
			}
			
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
