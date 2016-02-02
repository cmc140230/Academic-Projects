
import java.io.*;
import java.net.*;
public class TCPClientBroadcast 
{
	private String message = "";
	private Socket clientSocket;
	private boolean isRunning = true;
	private InetAddress address;
	public TCPClientBroadcast(String msgType, String host, int port, int neighborNode, String msgBroadcast, int NodeID) throws IOException, InterruptedException
	{
		try
		{
			address = InetAddress.getByName(host);
			//Create a client socket and connect to server at 127.0.0.1 port 5000
			clientSocket = new Socket(address, port);
			//Read messages from server. Input stream are in bytes. They are converted to characters by InputStreamReader
			//Characters from the InputStreamReader are converted to buffered characters by BufferedReader
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream clientWriter = new PrintStream(clientSocket.getOutputStream());
			if(msgType.equals("2"))
			{
				clientWriter.println("2" + "," + NodeID + "," + msgBroadcast);
				System.out.println("Forwarding message to " + neighborNode + ": " + msgBroadcast);
			}
			else if(msgType.equals("3"))
			{
				clientWriter.println("3" + "," + NodeID);
                System.out.print("ACK back to " + project1.parentNode);
			}
			//The method readLine is blocked until a message is received 
			reader.close();
			clientSocket.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();		
		}
		finally
		{
		    clientSocket.close();
		}
	}
	public static void main(String args[])
	{
	}
}