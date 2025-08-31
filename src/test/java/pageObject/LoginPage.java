package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage
{

public LoginPage(WebDriver driver)
{
	super(driver);
}

@FindBy(xpath = "//input[@id='input-email']")
WebElement txtEmailAdd;

@FindBy(xpath = "//input[@id='input-password']")
WebElement txtPass;

@FindBy(xpath = "//input[@value='Login']")
WebElement btnLogin;

public void setEmail (String email)
{
	txtEmailAdd.sendKeys(email);
}

public void setPass(String pass)
{
	txtPass.sendKeys(pass);
}

public void clickLogin()
{
	btnLogin.click();
}




}
