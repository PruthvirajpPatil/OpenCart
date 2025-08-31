package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders
{
	
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException
	{
		
		String path= "C:\\Users\\Admin\\eclipse-workspace\\OpenCart\\testData\\Opencart_LoginData.xlsx"; //taking excel file path
		
		ExcelUtility xlutil = new ExcelUtility(path);
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcol= xlutil.getCellCount("Sheet1", 1);
		
		String logindata[][] = new String [totalrows][totalcol];
		
		for(int i=1; i<=totalrows;i++) //read data from excel file and storing in 2d array
		{
			for(int j=0;j<totalcol;j++) // i = row and j = col
			{
				logindata[i-1][j] = xlutil.getCellData("Sheet1", i, j);
			}
		}
		return logindata; //returning 2d array
	}

}
