package testBase;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.beust.jcommander.Parameter;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class BaseClass 
{
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
	@BeforeClass(groups= {"Sanity","Regression","datadriven","Master"})
	@Parameters({"browser", "os"})
	 public void setup(String br, String os) throws IOException, InterruptedException
	 {
		//read property file
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		
		logger=LogManager.getLogger(this.getClass());
		System.setProperty("webdriver.edge.driver","C:\\Webdriver\\edgedriver_win64\\msedgedriver.exe");
		
		//for remote execution
		if(p.getProperty("execution_evn").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			//os
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("Linux"))
			{
				capabilities.setPlatform(Platform.LINUX);
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No matching os");
				return;
			}
			
			//browser
			switch(br)
			{
			case "chrome": capabilities.setBrowserName("chrome"); 
			break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge");
			break;
			case "firefox": capabilities.setBrowserName("firefox");
			break;
			default: System.out.println("invalid browser");
			return;
			}
			
			
			driver=new RemoteWebDriver(new URL("http://192.168.0.104:4444"),capabilities);
		}
		
		//for local execution
		if(p.getProperty("execution_evn").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome": driver= new ChromeDriver(); break;
			case "edge": driver = new EdgeDriver(); break;
			case "firefox": driver = new FirefoxDriver(); break;
			default: System.out.println("Invalid browser"); return;
			}
		}
					
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		//driver.get("http://localhost/opencart/upload/");
		driver.get(p.getProperty("appURL"));
		
		driver.manage().window().maximize();
		
		
	 }
		
	 @AfterClass(groups= {"Sanity","Regression","datadriven","Master"})
	 public void tearDown()
	 {
		driver.quit();
	 }

	 
	 public String randomStrings()
	 {
		 String generatestring = RandomStringUtils.randomAlphabetic(4);
		 return generatestring;
		 
	 }
	 
	 public String randomnum()
	 {
		 String generatenum = RandomStringUtils.randomNumeric(10);
		 return generatenum;
	 }
	 
	 public String randomAlphaNum()
	 {
		 String generatestring = RandomStringUtils.randomAlphabetic(2);
		 String generatenum = RandomStringUtils.randomNumeric(3);
		 return (generatestring+generatenum);
	 }
	 
	 public String captureScreen(String tname)
	 {
		 String timeStamp = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss").format(new Date());
		 
		 TakesScreenshot takescreenshot= (TakesScreenshot)driver;
		 File sourceFile = takescreenshot.getScreenshotAs(OutputType.FILE);
		 
		 String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+ tname + "_" + timeStamp + ".png";
		 File targetFile = new File (targetFilePath);
		 
		 sourceFile.renameTo(targetFile);
		 
		 return targetFilePath;
		 
	 }
}
