package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbo.DataService;

/**
 * Servlet implementation class checkSprinklerScheduleServlet
 */
@WebServlet("/CheckScheduleServlet")
public class CheckScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		//String device = request.getParameter("device");
		PrintWriter out = response.getWriter();
		
		String deviceStatus="";
		
		String user_id=(String)session.getAttribute("user_id");
		int rgague = Integer.parseInt(request.getParameter("rgague"));
		int temp = Integer.parseInt(request.getParameter("temp"));
		
		String query = "select device,zone_id,start_date,end_date,start_time,end_time,start_day,end_day,duration from schedule where user_id = "+user_id;
		System.out.println(rgague+","+query);
		String result="<table><tr><th>Device</th><th>Zone</th><th>Start Date</th><th>End Date</th><th>Start Time</th><th>End Time</th><th>Start Day</th><th>Duration</th><th>End Day</th><th>Status</th></tr>";
		SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeParser = new SimpleDateFormat("HH:mm");
		Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
		
		boolean sprinklerFlag=false;
		boolean securityFlag=false;
		boolean thermostatFlag=false;
		try 
		{
			ResultSet rs = DataService.getResultSet(query);
			 
			 Date d = new Date();
			while(rs.next()){
				String currentDate = dateParser.format(d);
				String currentTime = timeParser.format(d);
				int currentDayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
				String day="";
			if(currentDate.compareTo(rs.getString("start_date"))>=0 && currentDate.compareTo(rs.getString("end_date"))<=0 && currentTime.compareTo(rs.getString("start_time"))>=0 && currentTime.compareTo(rs.getString("end_time"))<0){
				
				if(rs.getString("duration").equals("to")){
					System.out.println("Inside to duration "+currentDayOfWeek+", "+rs.getInt("start_day")+", "+ rs.getInt("end_day"));
					if(currentDayOfWeek>=rs.getInt("start_day") && currentDayOfWeek<=rs.getInt("end_day"))
					{
						if(rs.getString("device").contains("Sprinkler")){
						deviceStatus="ACTIVE";
						sprinklerFlag=true;
							if(rgague>=7)
								{
								deviceStatus="Enough rain outside.Schedule not activated.";
								sprinklerFlag = false;
								}
						}
						
						if(rs.getString("device").contains("Thermostat")){
							deviceStatus="ACTIVE";
							thermostatFlag=true;
							if(temp<65)
								{
								deviceStatus="It's cold.Schedule not activated.";
								thermostatFlag = false;
								}
						
							}
						
					}
			}
				if(rs.getString("duration").equals("and")){
					System.out.println("Inside to duration "+currentDayOfWeek+", "+rs.getInt("start_day")+", "+ rs.getInt("end_day"));
					
					if(currentDayOfWeek==rs.getInt("start_day") && currentDayOfWeek!=rs.getInt("end_day")+1)
					{
						System.out.println("Inside and duration "+currentDayOfWeek+", "+rs.getInt("start_day")+", "+ rs.getInt("end_day"));
						
						if(rs.getString("device").contains("Sprinkler")){
						deviceStatus="ACTIVE";
						sprinklerFlag=true;
						if(rgague>=7)
							{
							deviceStatus="Enough rain outside.Schedule not activated.";
							sprinklerFlag = false;
							}
							//deviceStatus="";
						//	result = result+"<tr><td>"+rs.getString("device")+"</td><td>"+rs.getString("zone_id")+"</td><td>"+rs.getString("start_date")+"</td>"+"<td>"+rs.getString("end_date")+"</td>"+"<td>"+rs.getString("start_time")+"</td>"+"<td>"+rs.getString("end_time")+"</td>"+"<td>"+rs.getString("start_day")+"</td>"+"<td>"+rs.getString("end_day")+"</td>"+"<td>"+rs.getString("duration")+"</td><td id='deviceStatus'>"+deviceStatus+"</td</tr>";
						
					}
						if(rs.getString("device").contains("Thermostat")){
							deviceStatus="ACTIVE";
							thermostatFlag=true;
							if(temp<65)
								{
								deviceStatus="It's cold.Schedule not activated.";
								thermostatFlag = false;
								}

							//deviceStatus="";
							//result = result+"<tr><td>"+rs.getString("device")+"</td><td>"+rs.getString("zone_id")+"</td><td>"+rs.getString("start_date")+"</td>"+"<td>"+rs.getString("end_date")+"</td>"+"<td>"+rs.getString("start_time")+"</td>"+"<td>"+rs.getString("end_time")+"</td>"+"<td>"+rs.getString("start_day")+"</td>"+"<td>"+rs.getString("end_day")+"</td>"+"<td>"+rs.getString("duration")+"</td><td id='deviceStatus'>"+deviceStatus+"</td></tr>";
						
							}
						
				}
			}
			}
			else
				deviceStatus="";
			
			result = result+"<tr><td>"+rs.getString("device")+"</td><td>"+rs.getString("zone_id")+"</td><td>"+rs.getString("start_date")+"</td>"+"<td>"+rs.getString("end_date")+"</td>"+"<td>"+rs.getString("start_time")+"</td>"+"<td>"+rs.getString("end_time")+"</td>"+"<td>"+rs.getString("start_day")+"</td>"+"<td>"+rs.getString("duration")+"</td>"+"<td>"+rs.getString("end_day")+"</td><td id='deviceStatus'>"+deviceStatus+"</td></tr>";
			
			}
			if(result!=""){
				if(sprinklerFlag)
				result=result+"<input type='hidden' value='ACTIVE' id='isSprinklerOn'/>";
				if(thermostatFlag)
					result=result+"</table><input type='hidden' value='ACTIVE' id='isThermostatOn'/>";
				
				else
					result=result+"</table>";
				
				out.println(result);
					
			}
			
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
