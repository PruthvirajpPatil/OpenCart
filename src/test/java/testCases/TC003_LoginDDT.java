package testCases;

import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass
{
	@Test(dataProvider="LoginData", dataProviderClass = DataProviders.class, groups="datadriven")
	public void verify_LoginDDT(String user, String pass, String exp) throws InterruptedException
	{
		
		logger.info("***** Starting TC003_LoginDDT *****");
		//HomePage
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info("***** Click on MyAccount CTA *****");
		hp.clickLogin();
		logger.info("***** Click on Login CTA *****");
		
		
		try
		{
		//LoginPage
		LoginPage lp = new LoginPage(driver);
		logger.info("***** Providing Login Deatails *****");
		lp.setEmail(user);
		lp.setPass(pass);
		lp.clickLogin();
		
		//MyAccountPage
		MyAccountPage myacc = new MyAccountPage(driver);
		boolean targetpage = myacc.isMyAccountPageExists();
		
		if(exp.equalsIgnoreCase("valid"))
		{
			if(targetpage==true)
			{
				myacc.clickLogout();
				Assert.assertTrue(true);
				
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
			
		if(exp.equalsIgnoreCase("invalid"))
		{
			if(targetpage==true)
			{
				myacc.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
			
		
		
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		

		
		logger.info("***** Finish TC003_LoginDDT *****");
	}
	

}
