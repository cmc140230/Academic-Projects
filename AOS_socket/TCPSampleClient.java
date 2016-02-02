

import java.io.*;
import java.net.*;
public class TCPSampleClient 
{
	private String message = "";
	private Socket clientSocket;
	private boolean isRunning = true;
	private InetAddress address;
    private InputStream input;
	public TCPSampleClient(String host, int port, int portLocal, int nodeID, int localID) throws IOException, InterruptedException
	{
        project1 nodeLocal = new project1();
		try
		{
      
			address = InetAddress.getByName(host);
			//Create a client socket and connect to server at 127.0.0.1 port 5000
			clientSocket = new Socket(address, port);
			//Read messages from server. Input stream are in bytes. They are converted to characters by InputStreamReader
			//Characters from the InputStreamReader are converted to buffered characters by BufferedReader
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintStream clientWriter = new PrintStream(clientSocket.getOutputStream());
      
            clientWriter.println("1" + "," + InetAddress.getLocalHost().getHostName().substring(0, 4) + "," +  localID + "," + portLocal );

			//The method readLine is blocked until a message is received 

			message = reader.readLine();

			if(message != null && !message.isEmpty() && !message.equals("999"))
			{

                project1.tableNeighbors.add(message);
                project1.joined = true;
				isRunning = false;
			}
            reader.close();
			clientSocket.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
            System.out.println("refused:" + address);
		
		}
        finally
        {

        }
	}
	public static void main(String args[])
	{

	}
}