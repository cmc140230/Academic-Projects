
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPSampleServer 
{
	private ServerSocket serverSock;
	static Reader ReadTop;
    static ArrayList<String> neighbors = new ArrayList<String>();
	public TCPSampleServer(int port, int NodeID) throws IOException, NumberFormatException, InterruptedException
	{
		ReadTop = new Reader("./config.txt");
		ReadTop.readFile();
		neighbors.clear();
        project1 nodeLocal = new project1();

		try
		{
			
			//Create a server socket at port 5000
			serverSock = new ServerSocket(port);
			
			//serverSock.bind(new InetSocketAddress(port));
			serverSock.setReuseAddress(true);
			//serverSock.setReuseAddress(true);
			
			//Server goes into a permanent loop accepting connections from clients			
			while(true)
			{
        
				//Listens for a connection to be made to this socket and accepts it
				//The method blocks until a connection is made
				Socket sock = serverSock.accept();
      
				
                BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				
				String messageC = reader.readLine();
                String msgType = messageC.substring(0, 1);
        
                if(msgType.equals("1"))
                {
                    String portC = messageC.substring(9);
				    String hostC = messageC.substring(2, 6);
				    String nodeC = messageC.substring(7, 8);
					
					project1.treeParent = Integer.parseInt(nodeC);

				    for(int i = 0; i < ReadTop.ControlReadIndex; i++)
				    {
					    if(hostC.equals(ReadTop.ControlReadTemp[i][1]) && portC.equals(ReadTop.ControlReadTemp[i][2]))
					    {
						    nodeC = ReadTop.ControlReadTemp[i][0];
				  	    }
				    }
        
		  		    String message = null;
                    PrintWriter writer = new PrintWriter(sock.getOutputStream());
          
                    if(project1.joined == false)
                    {
                        int temp = (Integer.parseInt(nodeC) + 1);
                        message = String.valueOf(NodeID);
                        project1.joined = true;
                        neighbors.add(String.valueOf(temp));
              
                        project1.tableNeighbors.add(String.valueOf(temp));
              
                    }         
                    else
                    {
                        message = "999";
                    }

					writer.println(message);
			  		writer.close();
            
            
					if(project1.count2 == 0)
					{
                        Thread clientT = new project1.clientT();
						clientT.start();
                        project1.count2 = 1;
					}
                }
                else if(msgType.equals("2"))
				{
				  	String nodeC = messageC.substring(2, 3);
				  	String msgBroadcast = messageC.substring(4);
                    project1.parentNode = Integer.parseInt(nodeC);
					
				  	System.out.println("Receiving message " + "from " + nodeC + ": " + msgBroadcast);
				  	project1.dealBroadcast(Integer.parseInt(nodeC), msgBroadcast, msgType);
			  	}

                else if(msgType.equals("3"))
				{
					String nodeC = messageC.substring(2, 3);
					if(project1.isBroadcasting == true)
					{
						System.out.println("Broadcast is finished.");
						project1.isBroadcasting = false;
					}
					else
					{
						project1.dealBroadcast(Integer.parseInt(nodeC), "", msgType);
					}	
				}
			}
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			serverSock.close();
		}
	}
	public static void main(String args[])
	{

	}

}