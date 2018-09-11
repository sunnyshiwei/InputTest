package InputPackage;


import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;

public class ActionSelenium {

	public WebDriver driver;

	/*
	 * 打开浏览器，初始化浏览器
	 */
	public void InitDriver() {
		System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
		System.setProperty("webdriver.gecko.driver", "F:\\selenium\\geckodriver-v0.15.0-win64\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get("https://www.imooc.com/user/newlogin/from_url/");
	}

	/*
	 * <input class="xa-emailOrPhone ipt ipt-email js-own-name" value=""
	 * maxlength="37" name="email" data-validate="require-mobile-phone"
	 * autocomplete="off" placeholder="请输入登录邮箱/手机号" type="text">
	 * 
	 * 定义输入框Input
	 */
	public void InputBox() {
		WebElement user = driver.findElement(By.name("email"));
		user.sendKeys("");
		user.clear();
		// getAttribute是获取元素的属性
		String user_info = user.getAttribute("placeholder");
		System.out.println(user_info);
		// getAttribute获取输入框内的值
		String user_value = user.getAttribute("value");
		System.out.println(user_value);
		boolean user_isenabled = user.isEnabled();
		System.out.println(user_isenabled);
		//
		//定位用户名密码，并且点击登录按钮
		user.sendKeys("13998538053");
		driver.findElement(By.name("password")).sendKeys("ww744934");
		driver.findElement(By.xpath(".//*[@id='signup-form']/div[5]/input")).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * <div class="moco-control-input rlf-group rlf-radio-group">
	 * <label> <input hidefocus="true" value="0" name="sex" type="radio"> 保密</label> 
	 * <label> <input hidefocus="true" value="1" name="sex" type="radio"> 男 </label> 
	 * <label> <input hidefocus="true" value="2" checked="checked" name="sex" type="radio"> 女 </label></div>
	 *  单选框操作
	 */
	public void RedioBox() {
		driver.get("https://www.imooc.com/user/setprofile");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//点击‘编辑’按钮进入编辑个人信息页面
		driver.findElement(By.className("pull-right")).click();
		WebElement sex_node=driver.findElement(By.className("moco-control-input"));
		//获取元素组，之后通过for循环模式取到值
		List<WebElement> sex_list=sex_node.findElements(By.tagName("input"));
		for(WebElement sex:sex_list){
			boolean flag=sex.isSelected();
			if(flag==true){
				continue;
			}else{
				sex.click();
				break;
			}
			
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ActionSelenium action = new ActionSelenium();
		action.InitDriver();
		action.InputBox();
		action.RedioBox();
	}

}
