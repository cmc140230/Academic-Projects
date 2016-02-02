
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class project1
{
	static String host;
	static String NodeID;
	static Reader ReadTop;
	static boolean joined = false;
	static boolean isRunning = true;
    static String portLocal = "";
    static ArrayList<String> tableNeighbors = new ArrayList<String>();  
    static ArrayList<String> myNeighbors = new ArrayList<String>();
    static String msgBroadcast;
    static boolean isBroadcasting = false;
    static int parentNode;
    static String goTree;
    static int treeParent = 99;
    static int count = 0;
    static int count2 = 0;
  
	public static void main(String args[]) throws IOException, NumberFormatException, InterruptedException
    {                      
        myNeighbors.clear();
		NodeID = args[0];

		//read the topology
		ReadTop = new Reader("./config.txt");
		ReadTop.readFile();
		
		boolean[][] connected = new boolean [ReadTop.ControlReadIndex][ReadTop.ControlReadIndex];
		
		//set up link based on config.txt
		for (int i = 0; i < ReadTop.ControlReadIndex; i++) 
		{
			for (int j = 0; j < ReadTop.ControlReadIndex; j++) 
			{
				connected[i][j] = false;
			}
		}
		
		try 
		{
			host = InetAddress.getLocalHost().getHostName().substring(0, 4);
		} 
		catch (UnknownHostException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for(int i = 0; i < ReadTop.ControlReadIndex; i++)
		{

			if(Integer.parseInt(ReadTop.ControlReadTemp[i][0]) == (Integer.parseInt(NodeID) - 1))
			{
				portLocal = ReadTop.ControlReadTemp[i][2];
			}
		}

		Thread serverT = new ThreadServer();
        Thread clientT = new clientT();
		serverT.start();
   
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		System.out.println("Insert 'go' to start to building a tree: ");
		goTree = br.readLine();
		
		if(goTree.equals("go"))
		{
            clientT.start();
		}
    }
	
	public static class ThreadServer extends Thread
	{
		public void run()
		{
			
			while(isRunning)
			{
				try
				{
					TCPSampleServer server = new TCPSampleServer(Integer.parseInt(portLocal), Integer.parseInt(NodeID));
                    for(int i = 0; i < TCPSampleServer.neighbors.size(); i++)
					{
						myNeighbors.add(TCPSampleServer.neighbors.get(i));
					}
                                                                        
                    for(int i = 0; i < myNeighbors.size(); i++)
		            {
			            System.out.println(myNeighbors.get(i));
		            }
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					try
					{
						Thread.sleep(1000);
					}
					catch(Exception e)
					{
						
					}
				}
			}
		}
	}
	
	public static class ThreadBroadcast extends Thread
	{
 
		public void run()
		{
      
			while(isRunning)
			{
        
				try
				{
          
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
					System.out.println("Insert the message for broadcast: ");
					msgBroadcast = br.readLine();
					System.out.println("Sending message: " + msgBroadcast);
                    isBroadcasting = true;
                    parentNode = 99;
                    dealBroadcast(99, msgBroadcast, "2");
          
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					try
					{
						Thread.sleep(1000);
					}
					catch(Exception e)
					{
						
					}
				}
			}
		}		
	}

    public static void dealBroadcast(int parent, String msg, String msgType) throws NumberFormatException, IOException, InterruptedException
	{
    
    msgBroadcast = msg;
	if(msgType.equals("2"))
	{
		for(int i = 0; i < tableNeighbors.size(); i++)
		{
			for(int j = 0; j < ReadTop.ControlReadIndex; j++)
			{
				int temp = Integer.parseInt(ReadTop.ControlReadTemp[j][0]);
				if(Integer.parseInt(tableNeighbors.get(i)) == (temp + 1) && Integer.parseInt(tableNeighbors.get(i)) != parent)
				{
					TCPClientBroadcast clientB = new TCPClientBroadcast("2", ReadTop.ControlReadTemp[j][1] + ".utdallas.edu", Integer.parseInt(ReadTop.ControlReadTemp[j][2]), Integer.parseInt(tableNeighbors.get(i)), msgBroadcast, Integer.parseInt(NodeID));
				}
			}
		}
        if((tableNeighbors.size() == 1 && Integer.parseInt(tableNeighbors.get(0)) == parent))
		{
			for(int i = 0; i < ReadTop.ControlReadIndex; i++)
		  	{
				if(parentNode == Integer.parseInt(ReadTop.ControlReadTemp[i][0]) + 1)
			   	{
				  	TCPClientBroadcast clientB = new TCPClientBroadcast("3", ReadTop.ControlReadTemp[i][1] + ".utdallas.edu", Integer.parseInt(ReadTop.ControlReadTemp[i][2]), 0, "", Integer.parseInt(NodeID));
			  	}
		  	}
		}
    }		
	else if(msgType.equals("3"))
	{
		for(int i = 0; i < ReadTop.ControlReadIndex; i++)
		{
			if(parentNode == Integer.parseInt(ReadTop.ControlReadTemp[i][0]) + 1)
			{

				TCPClientBroadcast clientB = new TCPClientBroadcast("3", ReadTop.ControlReadTemp[i][1] + ".utdallas.edu", Integer.parseInt(ReadTop.ControlReadTemp[i][2]), 0, "", Integer.parseInt(NodeID));
			}
		}
	}	
}
 
 public static class clientT extends Thread
	{
		public void run()
		{
			while(count == 0)
			{
				try
				{
          
					for(int i = 0; i < ReadTop.ControlReadIndex; i++)
					{
						if(Integer.parseInt(ReadTop.ControlReadTemp[i][0]) == (Integer.parseInt(NodeID) - 1))
						{
							for (int j = 0; j < ReadTop.ControlReadTemp[i].length - 3; j++)
							{
								for(int k = 0; k < ReadTop.ControlReadIndex; k++)
								{
									if(Integer.parseInt(ReadTop.ControlReadTemp[i][ReadTop.ControlReadTemp[i].length - j - 1]) == Integer.parseInt(ReadTop.ControlReadTemp[k][0]) + 1 && Integer.parseInt(ReadTop.ControlReadTemp[k][0]) + 1 != treeParent)
									{
										TCPSampleClient client = new TCPSampleClient(ReadTop.ControlReadTemp[k][1] + ".utdallas.edu", Integer.parseInt(ReadTop.ControlReadTemp[k][2]), Integer.parseInt(portLocal), (Integer.parseInt(ReadTop.ControlReadTemp[k][0]) + 1), Integer.parseInt(NodeID));
									}
								}								
							}
						}
					}
                    for(int i = 0; i < tableNeighbors.size(); i++)
    	            {
		        	    System.out.println(tableNeighbors.get(i));
		            }
                    Thread broadcastT = new ThreadBroadcast();
                    broadcastT.start();
					count = 1;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					try
					{
						Thread.sleep(1000);
					}
					catch(Exception e)
					{
						
					}
				}
			}
		}		
	}
 
    public static void buildTree() throws NumberFormatException, IOException, InterruptedException
	{
		for(int i = 0; i < ReadTop.ControlReadIndex; i++)
		{
			if(Integer.parseInt(ReadTop.ControlReadTemp[i][0]) == (Integer.parseInt(NodeID) - 1))
			{
				for (int j = 0; j < ReadTop.ControlReadTemp[i].length - 3; j++)
				{
					for(int k = 0; k < ReadTop.ControlReadIndex; k++)
					{
						if(Integer.parseInt(ReadTop.ControlReadTemp[i][ReadTop.ControlReadTemp[i].length - j - 1]) == Integer.parseInt(ReadTop.ControlReadTemp[k][0]) + 1 && Integer.parseInt(ReadTop.ControlReadTemp[k][0]) + 1 != treeParent)
						{
							TCPSampleClient client = new TCPSampleClient(ReadTop.ControlReadTemp[k][1] + ".utdallas.edu", Integer.parseInt(ReadTop.ControlReadTemp[k][2]), Integer.parseInt(portLocal), (Integer.parseInt(ReadTop.ControlReadTemp[k][0]) + 1), Integer.parseInt(NodeID));
						}
					}								
				}
			}
		}
        count = 1;
		for(int i = 0; i < tableNeighbors.size(); i++)
		{
			System.out.println(tableNeighbors.get(i));
		}
        Thread broadcastT = new ThreadBroadcast();
        broadcastT.start();
	}
	
	public static void stopThread()
	{
		//isRunning = false;
	}
  public void node()
	{

	}
}