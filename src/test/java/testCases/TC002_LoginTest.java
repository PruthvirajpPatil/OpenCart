package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass
{
	
	@Test(groups={"Sanity","Master"})
	public void verifing_Login()
	{
	logger.info("***** Starting TC002_LoginTest *****");
	
	try
	{
		logger.info("***** open homepage *****");
	//HomePage
	HomePage hp = new HomePage(driver);
	hp.clickMyAccount();
	logger.info("***** Click on MyAccount CTA *****");
	hp.clickLogin();
	logger.info("***** Click on Login CTA *****");
	
	//LoginPage
	LoginPage lp = new LoginPage(driver);
	logger.info("***** Providing Login Deatails *****");
	lp.setEmail(p.getProperty("Email"));
	lp.setPass(p.getProperty("Password"));
	lp.clickLogin();
	
	//MyAccountPage
	MyAccountPage myacc = new MyAccountPage(driver);
	boolean targetpage = myacc.isMyAccountPageExists();
	logger.info("***** Validate page display or not *****");
	Assert.assertEquals(targetpage, true, "Login Failed");
	//Assert.assertTrue(targetpage); //another validation option
	myacc.clickLogout();
	}
	catch(Exception e)
	{
		Assert.fail();
	}
	logger.info("***** Finished TC002_LoginTest *****");
	
	}
}
