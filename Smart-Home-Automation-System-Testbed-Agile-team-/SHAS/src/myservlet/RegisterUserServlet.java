package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbo.DataService;


public class RegisterUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fName = request.getParameter("fname");
		String lName = request.getParameter("lname"); 
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		
		PrintWriter out = response.getWriter();
		//RequestDispatcher rd = request.getRequestDispatcher("/web/jsp/success.jsp");
		response.setContentType("text/html");
		String registerUserQuery = "insert into user (username,password,firstname,lastname,email,phone) values ('"+username+"','"+password+"','"+fName+"','"+lName+"','"+email+"','"+phone+"')";
		
		//DataService ds = new DataService();
		try 
		{
			DataService.runQuery(registerUserQuery);
			//request.setAttribute("message","you have been successfully registered");
			//rd.forward(request, response);
			out.println("You have been successfully registered. <a href='http://localhost:8080/SHAS/'>Login</a> now.");
			out.flush();
			return;
		}
		catch (SQLException e) 
		{
		
			e.printStackTrace();
		}
	}

}
