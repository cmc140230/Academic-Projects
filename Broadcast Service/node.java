
import java.io.*;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.io.PrintWriter;
import java.util.ArrayList;
//import java.util.Timer;
//import java.util.TimerTask;

public class node //extends TimerTask
{
    static int ExecutionTime = 0;
	static ArrayList sourceNode = new ArrayList();
	static String source;
  	static ArrayList receiverNode = new ArrayList();
    static String[][] LSA = new String[10][12];
	static int[] LSALife = new int[10];
	static ArrayList Neighbor = new ArrayList();
	static ArrayList JoinMsg = new ArrayList();
	static ArrayList TreePath = new ArrayList();
	private static Writer receiveFrom;
	static ArrayList ShortestPath = new ArrayList();
	static ArrayList Child = new ArrayList();
	static ArrayList Parent = new ArrayList();
	static boolean onTree = false;
	public static void main(String args[])
    {
		//Timer timer = new Timer();
      	//timer.schedule(new node(), 5000, 5000);

      	//init the LSA
		for(int i = 0; i < 10; i++)
	    {
            LSA[i][2] = "0";
		    for (int j = 3; j < 12; j++)
            {
				LSA[i][j] = "";
		    }
			LSA[i][0] = "";
		    LSALife[i] = 0;
		}
		
		//init the args[]
		String NodeID = args[0];
		String role = "";
		String message = "";
		
		if(args.length > 1)
		{
			role = args[1];
			if(role.equals("sender"))
	        {
				message = args[2];
				sourceNode.add(NodeID);
                source = NodeID;
                onTree = true;
			}				
			else if (role.equals("receiver"))
			{
				receiverNode.add(NodeID);
				sourceNode.add(args[2]);
                source = args[2];
                receiveFrom = new Writer(NodeID + "_received_from_" + source + ".txt");
                onTree = true;
			}
		}
		
		Writer SendMsg = new Writer("output_" + NodeID + ".txt");
		Reader ReceiveMsg = new Reader("input_" + NodeID + ".txt");
   
        Writer SenderWriteMsg = new Writer("output_" + source + ".txt");
        Reader SenderMsg = new Reader("output_" + source + ".txt");
        if(role.equals("sender"))
		{
			SenderWriteMsg.writeFile(message);
            //System.out.println(message);
		}
		
		//the total running time is 150s
		int flag;
		while (ExecutionTime <= 150)
		{
			SenderMsg.readFile();

			//if the role is receiver and the output msg is hello or linkstate, then write the R_received_from_S.txt
			if(role.equals("receiver"))
			{
				for(int i = 0; i < SenderMsg.ControlReadIndex; i++)
				{
					if(SenderMsg.ControlReadTemp[i][0].contains("hello") || SenderMsg.ControlReadTemp[i][0].contains("linkstate"))
					{					
					}
					else
					{
						receiveFrom.writeFile(SenderMsg.ControlReadLine[i]);
					    //System.out.println("123");
					}
				}
			}

			//read from input_x
			read_input_file(ReceiveMsg, SendMsg, NodeID);

			//if the lifetime of LSA > 30, remove it
			for(int i = 0; i < 10; i++)
			{
				LSALife[i]++;
			    if(LSALife[i] > 30)
				{
		            LSALife[i] = 0;
					LSA[i][0] = "";
					LSA[i][2] = "0";
					for(int j = 3; j < LSA[i].length; j++)
					{
						LSA[i][j] = "";
					}
					for(int j = 0; j < Neighbor.size(); j++)
					{
						if(Neighbor.get(j).equals(Integer.toString(i)))
						Neighbor.remove(j);
					}
				}
			}
			
			flag = ExecutionTime % 10;
			//System.out.println(ExecutionTime);
			//System.out.println(flag);

			//when the second == flag, do the right case
			switch(flag)
			{
				//send hello each 5s
			    case 0:
				    send_hello(SendMsg, NodeID);
				    //System.out.println("123");
				    break;

				//send LSA each 10s
			    case 1:
				    send_link_state_advertisement(SendMsg, NodeID);
				    //System.out.println("456");
				    break;

				//send hello each 5s
		    	case 5:
		    		send_hello(SendMsg, NodeID);
		    		//System.out.println("789");
			    	break;

			    //send join when the role is not sender
		    	case 9: 
				    if (onTree && (!role.equals("sender")))
				    {				    	
				    	for(int k = 0; k < receiverNode.size(); k++)
				    	{
                      //System.out.println("123");
				    		//refresh_parent(NodeID, (String) receiverNode.get(k));
				    		Join(SendMsg, NodeID, (String) receiverNode.get(k));
				    	}
				 	}
				 	if(role.equals("sender"))
				 	{
				 		SendData(NodeID,message,SendMsg);
				 	}
			 		break;
			 default: break;
			}

			
			 
			ExecutionTime++;
			//System.out.println(ExecutionTime);
			 
			
			//Sleep for 1 Second
			try 
			{
				Thread.sleep(1000);
				
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.print("Press Enter to Exit Node.java");
    }
	
	private static void send_hello(Writer sendMsg, String nodeID) 
	{
		// TODO Auto-generated method stub
		sendMsg.writeFile("hello " + nodeID);
		//System.out.println("Hello");
	}
	
	private static void send_link_state_advertisement(Writer sendMsg, String nodeID) 
	{
		// TODO Auto-generated method stub
		String nodeList = "";
		//System.out.println(Neighbor);
		for (int i = 0; i < Neighbor.size(); i++)
		{
			
			if (Neighbor.get(i) != null)
			{
				nodeList = nodeList + " " + Neighbor.get(i);
			}
		}
		sendMsg.writeFile("linkstate " + nodeID + " " + ExecutionTime + nodeList);
		//System.out.println("linkstate " + nodeID + " " + ExecutionTime+nodeList);
	}
	
	private static void Join(Writer sendMsg, String nodeID, String object) 
	{

		// TODO Auto-generated method stub
		ArrayList path = new ArrayList();
		String JoinList = "";
		String parent;
		JoinMsg.removeAll(JoinMsg);
		//construct Join
		JoinMsg.add("join");
		JoinMsg.add(nodeID);
		JoinMsg.add(object);
        TreePath = refresh_parent(nodeID, source);
		if (TreePath.size() == 0)
		{
			return;
		}
        //System.out.println(TreePath);
		parent = (String) TreePath.get(0);
		JoinMsg.add(parent);


		String[] tempP = new String[2];
		tempP[0] = parent;
		tempP[1] = object;
		Parent.add(tempP);
		path = refresh_parent(parent, nodeID);
   
        //System.out.println(path.size());
		if (path.size() > 2)
		{
			for(int i = path.size() - 2; i > 0; i--)
			{
      
				JoinMsg.add(path.get(i));
			}
		}
		for(int i = 0; i < JoinMsg.size(); i++)
		{
			JoinList = JoinList + JoinMsg.get(i) + " ";
		}
		sendMsg.writeFile(JoinList);
		
	}
	
	private static void SendData(String nodeID, String message, Writer sendMsg) 
	{
		// TODO Auto-generated method stub
		String dataMsg = "";
		dataMsg = "data " + nodeID + " " + nodeID + " " + message;
		sendMsg.writeFile(dataMsg);
	}
	
	private static ArrayList refresh_parent(String src, String dst)
	{
		int src_int = Integer.parseInt(dst);
		int dst_int = Integer.parseInt(src);
		int[][] matrix = new int[10][10];
		int INF = 99;
		FloydWarshall graph = new FloydWarshall(10);
		ArrayList path = new ArrayList();	
		ArrayList temppath = new ArrayList();
		
		for(int i = 0;i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				matrix[i][j] = INF;
			}
		}
		for(int i = 0; i < 10; i++)
		{
			int k = 3;
			if ((!LSA[i][0].equals("")) && (!LSA[i][0].equals(null)))
			{
				while(k < LSA[i].length)
				{
					matrix[Integer.parseInt(LSA[i][k])][i] = 1;
					//System.out.println(matrix[Integer.parseInt(LSA[i][k])][i]);
					k++;
				}
			}
		}

		//run the dps
		graph.floydwarshall(src_int, dst_int, matrix);
		temppath = graph.result;
   
   
		
		//reverse
		for(int i = temppath.size() - 1; i >= 0; i--)
		{
			path.add(temppath.get(i));
		}
   //System.out.println(path);
		return path;
	}
	
	private static void read_input_file(Reader receiveMsg, Writer sendMsg, String nodeID) 
	{
		// TODO Auto-generated method stub
		receiveMsg.readFile();
		String LSAinfo = "";
		//prosess the all msgs
		
		for (int i = 0; i < receiveMsg.ControlReadIndex; i++) 
		{
			
			LSAinfo = "";
			
			if (receiveMsg.ControlReadTemp[i][0].equals("hello"))
			{
				//System.out.println(receiveMsg.ControlReadTemp[i][1]);
				//process hello add a node to Neighbor
				boolean newNode = true;
				for (int j = 0; j < Neighbor.size(); j++)
				{
					
					if (Neighbor.get(j).equals(receiveMsg.ControlReadTemp[i][1]))
					{
						newNode = false;
					}
				}
				if (newNode)
				{
					Neighbor.add(receiveMsg.ControlReadTemp[i][1]);
				}
				//System.out.println(Neighbor);
			}
			
			//process Linkstate
			else if (receiveMsg.ControlReadTemp[i][0].equals("linkstate"))
			{
				
				if (LSALife[Integer.parseInt(receiveMsg.ControlReadTemp[i][1])] < 30)
				{
					if (Integer.parseInt(receiveMsg.ControlReadTemp[i][2]) > Integer.parseInt(LSA[Integer.parseInt(receiveMsg.ControlReadTemp[i][1])][2]))
					{					
						LSA[Integer.parseInt(receiveMsg.ControlReadTemp[i][1])] = receiveMsg.ControlReadTemp[i];
						
					    for(int n = 0; n < LSA[Integer.parseInt(receiveMsg.ControlReadTemp[i][1])].length; n++)
					    {
						    LSAinfo = LSAinfo + LSA[Integer.parseInt(receiveMsg.ControlReadTemp[i][1])][n] + " ";
					    }
					    sendMsg.writeFile(LSAinfo);
					    //System.out.println(LSAinfo);
					    LSALife[Integer.parseInt(receiveMsg.ControlReadTemp[i][1])] = 0;
					}
				}
			}
			
			//process Join
			else if (receiveMsg.ControlReadTemp[i][0].equals("join"))
			{
				// to see whether is the intermediate node
				if(receiveMsg.ControlReadTemp[i].length > 4)
				{
					if(receiveMsg.ControlReadTemp[i][4].equals(nodeID))
					{
						String JoinInfo = "";
						for(int j = 0; j < receiveMsg.ControlReadTemp[i].length; j++)
						{
							if (j != 4)
							{
								JoinInfo = JoinInfo + receiveMsg.ControlReadTemp[i][j]+" ";
							}
						}
					//forward Join message
					sendMsg.writeFile(JoinInfo);
					}
				}
			}
			
			//process Data
			else if (receiveMsg.ControlReadTemp[i][0].equals("data"))
			{
				boolean isReceiver = false;
				for(int ri = 0; ri < receiverNode.size(); ri++)
				{
					if(receiverNode.get(ri).equals(nodeID))
					{
						isReceiver = true;
					}
				}
				
				//if it's not a receiver, then forward data
				if(!isReceiver)
				{
					for(int pi = 0; pi < Parent.size(); pi++)
					{
						if(receiveMsg.ControlReadTemp[i][1].equals(Parent.get(pi)) && onTree && receiveMsg.ControlReadTemp[i][2].equals(Parent.get(pi)))
						{
							String forward = "";
							forward = "data " + nodeID + " "+ receiveMsg.ControlReadTemp[i][2];
							for(int j = 3; j < receiveMsg.ControlReadTemp[i].length; j++)
								forward = forward + "  "+ receiveMsg.ControlReadTemp[i][j];
							sendMsg.writeFile(forward);
							break;
						}
					}
				}
			}
		}
	}

   /* public void run()
    {
    	PrintWriter outputStream = null;
    	try 
    	{
    		outputStream = new PrintWriter(new FileOutputStream("C://CS6390//output_1.txt", true));
    		outputStream.println("hello 1");
    		outputStream.close();
    		System.out.println("123");
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//System.out.println("123");
    }    */
}