import java.util.*;



public class FloydWarshall
{
    private int distancematrix[][];
    private int numberofvertices = 10;
    public int s;
    public boolean[] b = new boolean [10];
    static ArrayList result = new ArrayList();
    static ArrayList temp = new ArrayList();
 
    public FloydWarshall(int numberofvertices)
    {
        distancematrix = new int[10][10];
    }
 
    public void floydwarshall(int src, int dst, int adjacencymatrix[][])
    {
    	
    	Stack st = new Stack();
    	
    	int count = 0;
    	int min = 99;
    	s = src;
        temp.clear();
        result.clear();
        //distancematrix[10][10] = adjacencymatrix[10][10];
        
        result.add(Integer.toString(src));
        for (int i = 0; i < 10; i++) 
		{
			b[i] = false;
		}
        //System.out.println(b[s]);
        if(b[s] == false)
        {
            temp.add(Integer.toString(src));
        }
        b[s] = true;
        
        //x(src, dst);
        //System.out.println(temp.size());
        //System.out.println(temp);
        for(int i = 0; i < 10; i++)
        {
        	//Integer k = Integer.valueOf((String) temp.get(temp.size())).intValue();
        	if(i == Integer.valueOf((String) temp.get(temp.size()-1)).intValue())
        	{
        		for(int j = 0; j < 10; j++)
            	{
            		if(adjacencymatrix[i][j] == 1 && b[j] == false)
            		{
            			temp.add(Integer.toString(j));
            			b[j] = true;
            			if(j == dst)
            			{
            				break;
            			}
            		}
            		//System.out.println(adjacencymatrix[i][j]);
            		//System.out.println("123");
            	}
        	}        	
        }
        result = temp;
        /*if(result.size() == 0)
        {
        	result = temp;
        }
        else if(temp.size() < result.size())
        {
        	result = temp;
        }*/
        //System.out.println(temp);
        //System.out.println(result);
        /*
        if(result.size() == 0)
        {
        	result = temp;
        }
        else if(temp.size() < result.size())
        {
        	result = temp;
        }*/
        
        
        
        /*for(int temp_dst = 0; temp_dst < 10; temp_dst++)
        {
        	if(temp_dst != src || temp_dst != dst)
        	{
        		for(int intermediate = 1; intermediate <= numberofvertices; intermediate++)
                {
                	if(intermediate != src || intermediate != temp_dst || intermediate != dst)
                	{
                		if(distancematrix[src][intermediate] + distancematrix[intermediate][temp_dst] < distancematrix[src][temp_dst])
                    	{
                        	distancematrix[src][temp_dst] = distancematrix[src][intermediate] + distancematrix[intermediate][temp_dst];                        	
                    	}
                	}
                }
        	}
        }*/

       /* for(int intermediate = 1; intermediate <= numberofvertices; intermediate++)
        {
        	if(intermediate != src || intermediate != dst)
        	{
        		if(distancematrix[src][intermediate] + distancematrix[intermediate][dst] < distancematrix[src][dst])
            	{
                	distancematrix[src][dst] = distancematrix[src][intermediate] + distancematrix[intermediate][dst];
            	}
        	}
        }     */   
    }
    
    /*public void result()
    {
    	for (int source = 1; source <= numberofvertices; source++)
            System.out.print("\t" + source);
 
        System.out.println();
        for (int source = 1; source <= numberofvertices; source++)
        {
            System.out.print(source + "\t");
            for (int destination = 1; destination <= numberofvertices; destination++)
            {
                System.out.print(distancematrix[source][destination] + "\t");
            }
            System.out.println();
        }
    }*/
 
    /*void x(int src, int dst)
    {
    	
    	for(int i = 0; i < 10; i++)
        {
        	if(distancematrix[src][i] == 1 && b[i] == false)
        	{
        		b[i] = true;
        		
        		if(i == dst)
        		{
        			break;
        		}
        		else
        		{        		
        			x(i, dst);
        		}
        	}
        	else
        	{
        		break;
        	}
        }
    }*/
    
    public static void main(String args[])
    {
    }
}