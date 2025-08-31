package utilities;
import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener
{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	String repName;

	 public void onStart(ITestContext testContext) 
	  {
		 // set and store time stamp formate
	   /*SimpleDateFormat sd = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	   Date d = new Date();
	   String currenttimeStamp= sd.format(d);*/
		 
		 String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		 repName= "Test-Report-" +timeStamp+ ".html";
		 sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName);
	   
		 sparkReporter.config().setDocumentTitle("opencart Automation Report");
		 sparkReporter.config().setReportName("opencart Functional Testing");
		 sparkReporter.config().setTheme(Theme.DARK);
		 
		 extent=new ExtentReports();
		 extent.attachReporter(sparkReporter);
		 extent.setSystemInfo("Application", "opencart");
		 extent.setSystemInfo("Module", "Admin");
		 extent.setSystemInfo("Sub-Module", "Customers");
		 extent.setSystemInfo("User Name",System.getProperty("user.name"));
		 extent.setSystemInfo("Environment", "QA");
		 
		 String os = testContext.getCurrentXmlTest().getParameter("os");
		 extent.setSystemInfo("Operating System", os);
		 
		 String browser= testContext.getCurrentXmlTest().getParameter("browser");
		 extent.setSystemInfo("Browser Name", browser);
		 
		 List<String> includeGroups= testContext.getCurrentXmlTest().getIncludedGroups();
		 if(!includeGroups.isEmpty())
		 {
			 extent.setSystemInfo("Groups", includeGroups.toString());
		 }
	  }
	

	   	 public void onTestSuccess(ITestResult result) 
			 {
				   test=extent.createTest(result.getTestClass().getName());
				   test.assignCategory(result.getMethod().getGroups());  // to display groups in reports
				   test.log(Status.PASS, result.getName()+"Test Pass");	   
			 }

	   	 public void onTestFailure(ITestResult result) 
		   	 {
			  test= extent.createTest(result.getTestClass().getName());
			  test.assignCategory(result.getMethod().getGroups());
			  test.log(Status.FAIL, result.getName()+"Test Fail");
			  test.log(Status.INFO, result.getThrowable().getMessage());
			  
			  try
			  {
				  String imgpath = new BaseClass().captureScreen(result.getName());
				  test.addScreenCaptureFromPath(imgpath);
				  
			  }
			  catch(Exception e1)
			  {
				  e1.printStackTrace();
			  }
			  
			 }

		 
		  public void onTestSkipped(ITestResult result) 
		  {
		   test=extent.createTest(result.getTestClass().getName());
		   test.assignCategory(result.getMethod().getGroups());
		   test.log(Status.SKIP, result.getName()+"Test Skiped");
		   test.log(Status.INFO, result.getThrowable().getMessage());
		  }

		 
		 
		  public void onFinish(ITestContext context) 
		  {
			  extent.flush();
			  
			  String pathofExtentReport= System.getProperty("user.dir")+"\\reports\\"+repName;
			  File extentReport= new File(pathofExtentReport);
			  
			  try
			  {
				  Desktop.getDesktop().browse(extentReport.toURI());
				  
			  }
			  catch(Exception e)
			  {
				  e.printStackTrace();
				  
			  }
			  
			  // sending report through email 
			 /* try { 
				  URL url = new URL ("file:///"+System.getProperty("user.dir")+"\\(reports\\"+repName);
					 
				  // Create the email message
					  ImageHtmlEmail email = new ImageHtmlEmail();
					  email. setDataSourceResolver(new DataSourceUrlResolver(url));
					  email. setHostName ("smtp-googlemail.com") ;
					  email. setSmtpPort (465);
					  email. setAuthenticator(new DefaultAuthenticator("pavanoltraining@gmail.com", "password"));
					  email. setSSLOnConnect(true);
					  
					  email. setFrom("pavanoltraining@gmail.com"); //Sender
					  email. setSubject ("Test Results");
					  email. setMsg("Please find Attached Report....") ;
					  email. addTo("pavankumar.busyqa@gmail.com"); // Receiver.
					  email. attach(url, "extent report", "please check report...");
					  email.send(); // send the email
			  		}
			  catch(Exception e) 
  					{ 
				  e.printStackTrace(); 
  					}*/
		  }	   
}
