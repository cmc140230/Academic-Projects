package myservlet;

import java.io.IOException;
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

/**
 * Servlet implementation class SprinklerScheduleServlet
 */
@WebServlet("/ThermostatScheduleServlet")
public class ThermostatScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThermostatScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String start_date = request.getParameter("start_date");
		String end_date = request.getParameter("end_date");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		String user_id=(String)session.getAttribute("user_id");
		String device = request.getParameter("device")+"-"+ request.getParameter("location");
		String zone = "-1";
		String start_day = request.getParameter("start_day");
		String end_day = request.getParameter("end_day");
		String duration = request.getParameter("duration");
		
		/*if(start_day =="Sun"){
			sDay=1;
		
		}
		if(start_day =="Mon" ){
			sDay=2;
			
		}
		if(start_day =="Tue" ){
			sDay=3;
			
		}
		if(start_day =="Wed" ){
			sDay=4;
			
		}
		if(start_day =="Thu" ){
			sDay=5;
			
		}
		if(start_day =="Fri" ){
			sDay=6;
			
		}
		if(start_day =="Sat"){
			sDay=7;
			
		}
		
		if(end_day =="Sun"){
			eDay=1;
		
		}
		if(end_day =="Mon" ){
			eDay=2;
			
		}
		if(end_day =="Tue" ){
			eDay=3;
			
		}
		if(end_day =="Wed" ){
			eDay=4;
			
		}
		if(end_day =="Thu" ){
			eDay=5;
			
		}
		if(end_day =="Fri" ){
			eDay=6;
			
		}
		if(end_day =="Sat"){
			eDay=7;
			
		}*/
		
	//if(start_date!=""&&end_date!=""&&start_time!=""&&end_time!=""&&start_day!=""&&end_day!=""&&duration!=""&&device!=""){ return;}
				String updateScheduleQuery = "insert into schedule(start_date,end_date,start_time,end_time,start_day,end_day,duration,device,zone_id,user_id) values ('"+start_date+"','"+end_date+"','"+start_time+"','"+end_time+"',"+Integer.parseInt(start_day)+","+Integer.parseInt(end_day)+",'"+duration+"','"+device+"','"+"-1"+"',"+user_id+")";
				System.out.println(updateScheduleQuery);
				try{
				DataService.runQuery(updateScheduleQuery);
				}
			
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
