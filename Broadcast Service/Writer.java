import java.io.*;

public class Writer 
{

	int count;
	String FileName = null;
	String Content = null;

	public Writer(String filename) 
	{
		try 
		{
			//create new files each time
			this.FileName = filename;
			File SharedFile = new File(FileName);
			FileWriter SFile = new FileWriter(SharedFile);
			SFile.close();
		} 
		catch (Exception e) 
		{
			System.out.println(e + "in writer");
		}
	}

	void writeFile(String content) 
	{
		this.Content = content;
		try 
		{
			BufferedWriter WriteFile = new BufferedWriter(new FileWriter(FileName, true)); 

			WriteFile.write(Content);
			WriteFile.write("\n");
			WriteFile.close();

			count++;
		} 
		catch (Exception e) 
		{
			System.out.println(e + " in writeFile()");
		}
	}
}