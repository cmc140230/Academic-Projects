<!--A Design by W3layouts
Author: W3layout
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Supported
License URL: http://creativecommons.org/licenses/by/3.0/
-->

<%
	 session = request.getSession(false);
	if(session.getAttribute("user")!=null && !(null==session))
	{
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SHAS Home</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.0/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://localhost:8080/SHAS/js/timeout-dialog.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="http://localhost:8080/SHAS/css/index.css" type="text/css" media="screen, projection" />
<link rel="stylesheet" href="http://localhost:8080/SHAS/css/timeout-dialog.css" type="text/css" media="screen, projection" />
 <script>
$(document).ready(function() {

	
	 $( "#slider-range-min" ).slider({
	      range: "min",
	      value: 65,
	      min: 0,
	      max: 120,
	      slide: function( event, ui ) {
	        $( "#temp" ).val( ui.value);
	      }
	    });
	    $( "#temp" ).val($( "#slider-range-min" ).slider( "value" ));
	    
	    
	    $( "#up-ac-range-min" ).slider({
		      range: "min",
		      value: 65,
		      min: 0,
		      max: 120,
		      slide: function( event, ui ) {
		        $( "#up-temp" ).val( ui.value);
		      }
		    });
		    $( "#up-temp" ).val($( "#up-ac-range-min" ).slider( "value" ));
	    
		    $( "#down-ac-range-min" ).slider({
			      range: "min",
			      value: 65,
			      min: 0,
			      max: 120,
			      slide: function( event, ui ) {
			        $( "#down-temp" ).val( ui.value);
			      }
			    });
			    $( "#down-temp" ).val($( "#down-ac-range-min" ).slider( "value" ));
			    
			    $( "#smoke-range-min" ).slider({
				      range: "min",
				      value: 0,
				      min: 0,
				      max: 9,
				      slide: function( event, ui ) {
				        $( "#smoke" ).val( ui.value);
				        if(ui.value>=4){
				        	$( "#fireAlarm" ).val("Yes");
				         $.timeoutDialog({timeout: 1, countdown:90,emergency:"fire",department:"fire", logout_redirect_url: "http://localhost:8080/SHAS/jsp/index.jsp", restart_on_yes: false});
				        }
				      },
				    });
				    $( "#smoke" ).val($( "#smoke-range-min" ).slider( "value" ));
				    
				    $( "#co-range-min" ).slider({
					      range: "min",
					      value: 0,
					      min: 0,
					      max: 9,
					      slide: function( event, ui ) {
					        $( "#co" ).val( ui.value);
					        if(ui.value>=4){
					        	$( "#fireAlarm" ).val("Yes");
					         $.timeoutDialog({timeout: 1, countdown:90,emergency:"fire",department:"fire", logout_redirect_url: "http://localhost:8080/SHAS/jsp/index.jsp", restart_on_yes: false});
					        }
					      },
					    });
					    $( "#co" ).val($( "#co-range-min" ).slider( "value" ));
	
    $.get('http://localhost:8080/SHAS/CheckScheduleServlet?temp='+$("#temp").val()+'&rgague='+$("#rgague").val(),function(data,status) {
    	
        $("#sprinklerSchedules").html(data);
      //alert($("#sprinklerStatus").text());
        if($("#isSprinklerOn").val()=="ACTIVE"){
        	$("#sprinkler_status").html("ON");
        	$("#sprinkler_status").css("color","GREEN");
        }else
        	{
        	$("#sprinkler_status").html("OFF");
        	$("#sprinkler_status").css("color","RED");
        	}
        	
    },'html');  
	setInterval(
			function ()
			{
			    //code goes here that will be run every 5 seconds.   
			    $.get('http://localhost:8080/SHAS/CheckScheduleServlet?temp='+$("#temp").val()+'&rgague='+$("#rgague").val(),function(data,status) {
			    	
			        $("#sprinklerSchedules").html(data);
			        //alert($("#sprinklerStatus").text());
			        if($("#isSprinklerOn").val()=="ACTIVE"){
			        	$("#sprinkler_status").html("ON");
			        	$("#sprinkler_status").css("color","GREEN");
			        }else
			        	{
			        	$("#sprinkler_status").html("OFF");
			        	$("#sprinkler_status").css("color","RED");
			        	}
			        
			        if($("#isThermostatOn").val()=="ACTIVE"){
			        	$("#thermostat_status").html("ON");
			        	$("#thermostat_status").css("color","GREEN");
			        }else
			        	{
			        	$("#thermostat_status").html("OFF");
			        	$("#thermostat_status").css("color","RED");
			        	}
			        	
			    },'html');  
			}, 5000);
			
			$( "#currentSprinklerSchedules" ).click(function() {
				  $("#sprinklerSchedules").slideToggle( "slow" );
				  if($("#currentSprinklerSchedules:hidden")){
					  $(this).html("+Current Schedules");
				  }else
					  {$(this).html("-Current Schedules");
					  
					  }
				});
			
	function blinker() {
	    $('.blink').fadeOut(500);
	    $('.blink').fadeIn(500);
	}

	setInterval(blinker, 10);
$("#securityBreach").change(function(e) {
	if($("#securityBreach").val()=="No") {
		document.getElementById("statusDate").innerHTML = "";
		document.getElementById("statusMessage").innerHTML ="";
		return;
	}
		   e.preventDefault();
		   $.timeoutDialog({timeout: 1, countdown:90,emergency:"breach",department:"fire",logout_redirect_url: "http://localhost:8080/SHAS/jsp/index.jsp", restart_on_yes: false});
	
	});
	
	
$("#fireAlarm").change(function(e) {
	if($("#fireAlarm").val()=="No") {
		document.getElementById("statusDate").innerHTML = "";
		document.getElementById("statusMessage").innerHTML ="";
		return;
	}
		   e.preventDefault();
		   $.timeoutDialog({timeout: 1, countdown:90,emergency:"fire",department:"fire", logout_redirect_url: "http://localhost:8080/SHAS/jsp/index.jsp", restart_on_yes: false});
	
	});
/*$("#delete").click(function(){
	$.ajax({
		  url: "http://localhost:8080/SHAS/SprinklerScheduleServlet?start_date="+start_date+"&end_date="+end_date+"&start_time="+start_time+"&end_time="+end_time+"&days="+days+"&device="+device,
		  beforeSend: function( xhr ) {
		    xhr.overrideMimeType( "text/plain; charset=x-user-defined" );
		  }
		})
		  .done(function( data ) {
		    if ( console && console.log ) {
		      console.log( "Sample of data:", data.slice( 0, 100 ) );
		    }
		  });
});*/
	
	$("#setDeviceSchedule").click(function(){
		var start_date=$("#start_date").val();
		var end_date=$("#end_date").val();
		var start_time=$("#start_time").val();
		var end_time=$("#end_time").val();
		var start_day=$("#start_day").val();
		var end_day=$("#end_day").val();
		var duration=$("#duration").val();
		var zone=$("#zone").val();
		var device=$("#device").val();
		var sprinkler_num = $("#sprinkler_num").val();
		var location = $("#location").val();
		
		if(device=="Sprinkler"){
		
		//alert("http://localhost:8080/SHAS/SprinklerScheduleServlet?start_date="+start_date+"&end_date="+end_date+"&start_time="+start_time+"&end_time="+end_time+"&start_day="+start_day+"&end_day="+end_day+"&duration="+duration+"&zone="+zone+"&device="+device+"&sprinkler_num="+sprinkler_num);
		$.ajax({
			  url: "http://localhost:8080/SHAS/SprinklerScheduleServlet?start_date="+start_date+"&end_date="+end_date+"&start_time="+start_time+"&end_time="+end_time+"&start_day="+start_day+"&end_day="+end_day+"&duration="+duration+"&zone="+zone+"&device="+device+"&sprinkler_num="+sprinkler_num,
			  beforeSend: function( xhr ) {
			    xhr.overrideMimeType( "text/plain; charset=x-user-defined" );
			  }
			})
			  .done(function( data ) {
			    if ( console && console.log ) {
			      console.log( "Sample of data:", data.slice( 0, 100 ) );
			    }
			  });
		}
		
		if(device=="Thermostat"){
			
			//alert("http://localhost:8080/SHAS/ThermostatScheduleServlet?start_date="+start_date+"&end_date="+end_date+"&start_time="+start_time+"&end_time="+end_time+"&start_day="+start_day+"&end_day="+end_day+"&duration="+duration+"&zone="+zone+"&device="+device+"&location="+location);
			$.ajax({
				  url: "http://localhost:8080/SHAS/ThermostatScheduleServlet?start_date="+start_date+"&end_date="+end_date+"&start_time="+start_time+"&end_time="+end_time+"&start_day="+start_day+"&end_day="+end_day+"&duration="+duration+"&zone="+zone+"&device="+device+"&location="+location,
				  beforeSend: function( xhr ) {
				    xhr.overrideMimeType( "text/plain; charset=x-user-defined" );
				  }
				})
				  .done(function( data ) {
				    if ( console && console.log ) {
				      console.log( "Sample of data:", data.slice( 0, 100 ) );
				    }
				  });
			}
	});
});
</script>
<style>
body{
font-size:0.8em;
width:85%;
margin: 0 auto;
background-image: url('../imgs/SHAS.jpeg');
background-repeat: no-repeat;

}
.blink {

    clear:right;
    font-size:0.85em;
    text-color:RED;
}
#currentSprinklerSchedules {
cursor:pointer;
text-color: blue;
}

#sprinkler_status{
color:RED;
}
#thermostat_status{
color:RED;
}
#header{
width:100%;}
#title{
font-size:2em;
font-weight:bold;
text-align:center;

height:35px;
}
#shas{
float:left;width:65%;background-color:#F0F8FF;min-height:600px;height:auto;
}

#notificationBar{
margin-left:5px;
margin-right:50px;

}
#simulator{
clear:right;width:30%;float:left;background-color:#FFE4B5;margin-left:20px;height:600px;
}
table .outer{
    border-collapse: collapse;
}

table, td, th {
    border: 1px solid white;
}
table td{
font-size:0.9em;
font-weight:bold;
}

</style>
</head>
<body>
<div id="header">
	<div id="title" style="float:left;">SMART HOME AUTOMATION SYSTEM </div>
	<div id="logout" style="clear:right;float:right; color:yellow">Welcome <%=session.getAttribute("user") %>. <a href="http://localhost:8080/SHAS/LogoutServlet" style="color:orange"> Logout</a></div>
</div>
<div id="shas">
<form>
<table class="outer">
<tr>
<td> Security Breach</td>
<td>
<select name="securityBreach" id="securityBreach">
	<option>No</option>
    <option>Yes</option>
    </select>
</td>
<td class="comment">Let's you set the alarm in case of a security breach. The alarm goes on for 90 seconds and then the police is notified. You may cancel to call the police within those 90 seconds.<br/> P.S. : Choose 'No' to shut the alarm off.</td>
</tr>
<tr>
<td>Fire Alarm</td>
<td>
<select name="fireAlarm" id="fireAlarm">
	<option>No</option>
    <option>Yes</option>
    </select>
</td>
<td class="comment">Let's you set the alarm in case of a fire. The alarm goes on for 90 seconds and then the fire department is notified. You may cancel to call the fire department within those 90 seconds.<br/> P.S. : Choose 'No' to shut the alarm off.</td>
</tr>
<tr>
<td>Sprinkler</td>
<td>
<select name="zone" id="zone">

	<option>1-Back yard</option>
    <option>2-Front yard</option>
    </select>

<select name="sprinkler_num" id="sprinkler_num">
	
	<option>1</option>
    <option>2</option>
    <option>3</option>
    <option>4</option>
    </select>
</td>
<td class="comment">Choose a zone and sprinkler number.</td>
</tr>
<tr>

<tr>
<td>A/C Locations</td>
<td>
<select name="location" id="location">

	<option>Upstairs</option>
    <option>Downstairs</option>
    </select>

</td>
<td class="comment">Let's you set the choose the air conditioning location.</td>
</tr>
<tr>
<td>A/C Controls</td>
<td>
<form>
<p>
  Up:<input type="text" id="up-temp" readonly style="border:0; color:#f6931f; font-weight:bold;">
</p>
 
<div id="up-ac-range-min"></div>

<p>
  Down:<input type="text" id="down-temp" readonly style="border:0; color:#f6931f; font-weight:bold;">
</p>
 
<div id="down-ac-range-min"></div>

  </form>

</td>
<td class="comment">Let's you control the air conditioning temprature.</td>
</tr>

<tr>
	
	<tr><td>Choose device to schedule</td><td><select name="device" id="device">
	<option>Security</option>
    <option>Sprinkler</option>
    <option>Thermostat</option>
    </select>
    </td></tr>
    <td>Schedule Devices: </td>
	<td>
	<form>
	<table>
	<tr><td>Start Date:</td><td><input type="date" id="start_date" name="start_date"></td></tr>
	<tr><td>End Date:</td><td><input type="date" id="end_date" name="end_date"></td></tr>
	<tr><td>Start Time:</td><td><input type="time" id="start_time" name="start_time"></td></tr>
	<tr><td>End Time:</td><td><input type="time" id="end_time" name="end_time"></td></tr>
    <tr><td>Day(s):</td><td><select name="start_day" id="start_day">
    
    <option value="1">Sun</option>
	<option value="2">Mon</option>
    <option value="3">Tue</option>
    <option value="4">Wed</option>
    <option value="5">Thu</option>
    <option value="6">Fri</option>
    <option value="7">Sat</option>
    
    </select>
<select name="duration" id="duration">
	<option>to</option>
    <option>and</option>
    </select>
<select name="end_day" id="end_day">
   <option value="1">Sun</option>
	<option value="2">Mon</option>
    <option value="3">Tue</option>
    <option value="4">Wed</option>
    <option value="5">Thu</option>
    <option value="6">Fri</option>
    <option value="7">Sat</option>
    
    </select>
</td>
</tr>
	
	<tr><td colspan=2><input type="button" id="setDeviceSchedule" name="setSchedule" value="Set Device Schedule"></td></tr>
	</table>
	</form>
	</td>
	<td >
	   	 Let's you you set the schedule for the smart devices. The devices turn on after the scheduled time depending on the various environmental conditions.
	
	</td>
	
</tr>
</table>
</form>
<div id="currentSprinklerSchedules">+Current Schedules</div>
<div id="sprinklerSchedules"></div>

<fieldset style="width: 400px;width:90%">
	<legend>Notifications</legend>
<div id="notificationBar" >
<div id="statusDate" style="float:left;font-size:0.8em;font-weight:bold">

</div>
<%if(request.getParameter("action")!=null){
%>
<div id="statusMessage" class="blink" > 

</div>
<script>
 var d = new Date();
	document.getElementById("statusDate").innerHTML = d.toUTCString()+" : ";
	document.getElementById("statusMessage").innerHTML = "There was a security breach. An Alarm went off. Department call accepted.";
	$("#securityBreach").val("Yes");
	</script>

<%} %>
</div>
</div>
</fieldset>


<div id="simulator">
<table >
<tr>
<td> Sprinkler :</td>
<td>
<div id="sprinkler_status">  </div>
</td>
</tr>
<tr>
<td> Smoke Detector :</td>
<td>
<div id="detector_status" style="color:GREEN"> ON </div>
</td>
</tr>
<tr>
<td> Smoke Level :</td>
<td>
<form>
<p>
  <input type="text" id="smoke" readonly style="border:0; color:#f6931f; font-weight:bold;">
</p>
 
<div id="smoke-range-min"></div>
  </form>
</td>
</tr>
<tr>
<td> CO Level :</td>
<td>
<form>
<p>
  <input type="text" id="co" readonly style="border:0; color:#f6931f; font-weight:bold;">
</p>
 
<div id="co-range-min"></div>
  </form>
</td>
</tr>
<tr>
<td> A/C :</td>
<td>
<div id="thermostat_status">  </div>
</td>
</tr>
<tr>
<td> RainGague Reading :</td>
<td>
<form>
<select id="rgague">
    <option value="0" selected="selected">0</option>
    <option value="1">1</option>
    <option value="2">2</option>
    <option value="3">3</option>
    <option value="4">4</option>
    <option value="5">5</option>
    <option value="6">6</option>
    <option value="7">7</option>
    <option value="8">8</option>
    <option value="9">9</option>
    
  </select>
  </form>
</td>
</tr>
<tr>
<td> Room Temperature(F) :</td>
<td>
<form>
<p>
  <input type="text" id="temp" readonly style="border:0; color:#f6931f; font-weight:bold;">
</p>
 
<div id="slider-range-min"></div>
  </form>
</td>
</tr>
</table>
</div>
</body>
</html>

<%} else
{
	response.sendRedirect(getServletContext().getContextPath()+"/jsp/login.jsp");
}
%>

