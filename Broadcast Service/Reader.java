import java.io.*;

public class Reader 
{
	int count;
	int RowNumber = 1000;
	int ColumnNumber = 20;
	String FileName = null;
	String ControlReadTemp[][] = new String[RowNumber][ColumnNumber];
	String ControlReadLine[] = new String[RowNumber];
	int ControlReadIndex = 0;

	/** Creates a new instance of Reader */
	public Reader(String filename) 
	{

		try 
		{
			//don't regenerate new file cause it would delete the topology.txt
			this.FileName = filename;
			count = 0;

		} 
		catch (Exception e) 
		{
			System.out.println(e + "in InitReader");
		}
	}

	void readFile() 
	{
		try 
		{
			//read the digit of each line
			String ControlReadTempCover[][] = new String[RowNumber][ColumnNumber];

			//read each line
			String ControlReadLineCover[] = new String[RowNumber];

			ControlReadTemp = ControlReadTempCover;
			ControlReadLine = ControlReadLineCover;

			//record where you're reading
			ControlReadIndex = 0;
			
			String strLine = null;
			BufferedReader ReadFile = new BufferedReader(new FileReader(FileName));
			int temp = 0;
			while ((strLine = ReadFile.readLine()) != null) 
			{
				++temp;
				if (temp > count)
				{
					ControlReadTemp[ControlReadIndex] = strLine.split("\\s+");
					ControlReadLine[ControlReadIndex] = strLine;
					ControlReadIndex++;

				}
			    
			}
			count = temp;
			ReadFile.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}