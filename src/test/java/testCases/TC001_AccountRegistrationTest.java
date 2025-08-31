package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.AccountRegistrationPage;
import pageObject.HomePage;
import testBase.BaseClass;



public class TC001_AccountRegistrationTest extends BaseClass
{
	
  
 @Test(groups={"Regression","Master"})
 public void verify_account_registration()
 {
	 logger.info("***** Strting TC001_AccountRegistrationTest *****");
	
	 try
	 {
	HomePage hp =new HomePage(driver);
	hp.clickMyAccount();
	logger.info("Clicking on MyAccount CTA");
	hp.clickRegister();
	logger.info("Clicking on Register CTA");
	
	AccountRegistrationPage regpage  = new AccountRegistrationPage(driver);
	logger.info("Providing user details***");
	regpage.setFristname(randomStrings().toUpperCase());
	regpage.setLastname(randomStrings().toUpperCase());
	regpage.setEmail(randomStrings()+"@example.com");
	regpage.setTelephone(randomnum());
	
	String password = randomAlphaNum();
	
	regpage.setPassword(password);
	regpage.setConfirmPassword(password);
	
	regpage.setPrivacyPolicy();
	regpage.clickContinue();
	logger.info("Validating expected massage");
	String confomsg = regpage.getConfirmation();
	if(confomsg.equals("Your Account Has Been Created!"))
	{
	  Assert.assertTrue(true);
	
	}
	else
	{
		logger.error("Test failed...");
		logger.debug("Debug logs....");
		Assert.assertTrue(false);
	}
	
	//Assert.assertEquals(confomsg, "Your Account Has Been Created!"); // no need
	 }
	 
	 catch(Exception e)
	 {
		 Assert.fail();
	 }
	logger.info("***** Testing Finished *****");
 }

}
