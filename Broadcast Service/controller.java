import java.io.*;
//import java.util.*;
//import java.lang.*;
//import java.nio.file.FileSystems;       
//import java.nio.file.Path;       
//import java.nio.file.Paths;       
//import java.nio.file.WatchEvent;       
//import java.nio.file.WatchKey;       
//import java.nio.file.WatchService;       

public class controller
{
	//private WatchService watcher;
    static int ExecutionTime = 0;
    public static void main(String[] args) //throws IOException
    {
        Reader ReadTop = new Reader("./topology.txt"); //"./topology.txt"
    	    ReadTop.readFile();
    	    //FileReader fr = new FileReader("c://CS6390//topology.txt");
    	    //BufferedReader br = new BufferedReader(fr);
    	    boolean[][] x = new boolean [10][10];
    	    int rownumber[] = new int [10];
    	
    	    //String tempstring;
    	    //String[] tempArray = new String[14];
    	    //int t = 0;
    	    //while ((tempstring = br.readLine()) != null)
      	    //{
    	    //	tempArray[t] = tempstring;
      	    //	t++;
      	    //}
      	    //fr.close();
    	
    	    //Set up Link based on topology.txt
		    for (int i = 0; i < 10; i++) 
            {
			    for (int j = 0; j < 10; j++) 
                {
				     x[i][j] = false;
			    }
		    }
		    //System.out.println("123");
		    //System.out.println(ReadTop.ControlReadTemp[0][1]);
		    for (int i = 0; i < 14; i++) 
		    {
		        x[Integer.parseInt(ReadTop.ControlReadTemp[i][0])][Integer.parseInt(ReadTop.ControlReadTemp[i][1])] = true;
		        /*if(x[Integer.parseInt(ReadTop.ControlReadTemp[i][0])][Integer.parseInt(ReadTop.ControlReadTemp[i][1])] == true)
		        {
		    	      System.out.println("true");
		        }*/
		    }
		
		    // Set up Reader and Writer
	    	Reader ReadNode[] = new Reader[10];
	    	Writer WriteNode[] = new Writer[10];

	    	for (int i = 0; i < 10; i++) 
    		{
			    try
			    {
				    File f = new File("input_" + i + ".txt");
				    f.createNewFile();
				    File ff = new File("output_" + i + ".txt");
			        ff.createNewFile();
			    } 
			    catch (IOException e) 
			    {
			        e.printStackTrace();
			    }
			    ReadNode[i] = new Reader("output_" + i + ".txt");
			    WriteNode[i] = new Writer("input_" + i + ".txt");
			    //System.out.println(i);
			 
			    ReadNode[i].readFile();
			    rownumber[i] = ReadNode[i].count;
			    //System.out.println(ReadNode[i].count);
		    }   
		
		
		    //the total time is 150s
		    while (ExecutionTime <= 150) 
		    {
                for(int i = 0; i < 10; i++)
			    {
			    	//read the output file
				    ReadNode[i].readFile();

					if (ReadNode[i].ControlReadIndex > 0) 
                    {
						for (int j = 0; j < ReadNode[i].ControlReadIndex; j++) 
                        {
							//System.out.println(ReadNode[i].ControlReadTemp[j][0]);
                            // Broadcast
							if (ReadNode[i].ControlReadTemp[j][0] != null)
                            {
							    for (int k = 0; k < 10; k++) 
                                {
									if (x[i][k]) 
                                    {
                                    	//write the input file
										WriteNode[k].writeFile(ReadNode[i].ControlReadLine[j]);
									}
								}
							}
                        }
					}
				}
			    //System.out.println("ExecutionTime = "+ExecutionTime);
			    /*for(int i = 0; i < 14; i++)
			    {
				    ReadNode[Integer.parseInt(ReadTop.ControlReadTemp[i][0])].readFile();
				    //System.out.println(ReadNode[Integer.parseInt(ReadTop.ControlReadTemp[i][0])].count);
				    //System.out.println(rownumber[Integer.parseInt(ReadTop.ControlReadTemp[i][0])]);

				    //if(ReadNode[Integer.parseInt(ReadTop.ControlReadTemp[i][0])].count != rownumber[Integer.parseInt(ReadTop.ControlReadTemp[i][0])])
				    //{
					    //if(x[Integer.parseInt(ReadTop.ControlReadTemp[i][0])][Integer.parseInt(ReadTop.ControlReadTemp[i][1])] == true)
					    //{
            
					        //System.out.println("gotcha!");
					        for(int j = 0; j < ReadNode[Integer.parseInt(ReadTop.ControlReadTemp[i][0])].ControlReadIndex; j++)
						    {
						        if(ReadNode[Integer.parseInt(ReadTop.ControlReadTemp[i][0])].ControlReadLine[j].contains("hello") || ReadNode[Integer.parseInt(ReadTop.ControlReadTemp[i][0])].ControlReadLine[j].contains("linkstate"))
						        {
                                    System.out.println(Integer.parseInt(ReadTop.ControlReadTemp[i][1]) + "," + Integer.parseInt(ReadTop.ControlReadTemp[i][0]));
                                    //System.out.println("gotcha!" + Integer.parseInt(ReadTop.ControlReadTemp[i][0]) + Integer.parseInt(ReadTop.ControlReadTemp[i][1]));
							        WriteNode[Integer.parseInt(ReadTop.ControlReadTemp[i][1])].writeFile(ReadNode[Integer.parseInt(ReadTop.ControlReadTemp[i][0])].ControlReadLine[j]);
							        //System.out.println(ReadNode[Integer.parseInt(ReadTop.ControlReadTemp[i][0])].ControlReadLine[j]);
						        }
						    //}
					    //}
                        /*
						for (int k = 0; k < Integer.parseInt(ReadTop.ControlReadTemp[0][2]); k++) 
						{
							if (x[i][k]) 
							{
								WriteNode[k].writeFile(ReadNode[Integer.parseInt(ReadTop.ControlReadTemp[i][1])].ControlReadLine[j]);
							}
						}							

				}
			    */
			//add 1 second
			ExecutionTime++;
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
		System.out.print("Press Enter to Exit controller.java");
    }
    	
    	/*for(int i = 0; i < tempArray.length; i++)
		{ 
    		for(int j = 0; j < 10; j++)
    		{
    			int tempInt1 = (int)tempArray[i].charAt(0) - 48;
    			int tempInt2 = (int)tempArray[i].charAt(2) - 48;
    			
		        if (tempInt1 == j)
		        {
		        	//System.out.println(tempInt1); 
	    			//System.out.println(j);
		        	for(int k = 0; k < 10; k++)
		        	{
		    	    	if(tempInt2 == k)
		    		    {
		    		    	x[i][j] = true;
		    		    	System.out.println(j + "," + k + "," + x[i][j]);
		    		    }
		    	    }
		        }
    		}  
		}
    	
    	for (int i = 0; i < 14; i++)
		{
			for (int j = 0; j < 14; j++)
			{
				if (i == j)
				{
					x[i][j] = true;
				}
			}
		}*/
    
    /*public void watchservice(Path path) throws IOException
    {
    	watcher = FileSystems.getDefault().newWatchService();
    	
    }*/
}