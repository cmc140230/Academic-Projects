


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
			this.FileName = filename;
			count = 0;

		} 
		catch (Exception e) 
		{
			System.out.println(e + "in InitReader");
		}
	}

	//read output files and write into input files
	void readFile() 
	{
		try 
		{
			String ControlReadTempCover[][] = new String[RowNumber][ColumnNumber];
			String ControlReadLineCover[] = new String[RowNumber];
			ControlReadTemp = ControlReadTempCover;
			ControlReadLine = ControlReadLineCover;
			ControlReadIndex = 0;
			
			String strLineT = null;
			String strLine = null;
			BufferedReader ReadFile = new BufferedReader(new FileReader(FileName));
			int temp = 0;
			while ((strLineT = ReadFile.readLine()) != null) 
			{
				if(strLineT.isEmpty() || strLineT.substring(0, 1).equals("#"))
				{

				}
				else
				{
					strLine = ControlReadIndex + "   " + strLineT;
					++temp;
					String str = strLineT.substring(0, 2);
					
					if (temp > count && str.equals("dc"))
					{
						
						ControlReadTemp[ControlReadIndex] = strLine.split("\\s+");
						ControlReadLine[ControlReadIndex] = strLine;
						ControlReadIndex++;
					}
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