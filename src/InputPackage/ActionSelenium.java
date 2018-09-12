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
		/**
		 * document.getElementById('J_GotoTop').style.display='none'
		 * 用JS隐藏右侧遮罩
		 */	
		String jsScrip="document.getElementById('J_GotoTop').style.display='none'";
		//将driver转换成js执行
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript(jsScrip);
		
		//点击‘编辑’按钮进入编辑个人信息页面
		driver.findElement(By.className("pull-right")).click();
		WebElement sex_node=driver.findElement(By.xpath(".//*[@id='setting-profile']/div[2]/a"));
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
	/*
	 * 复选框操作   clear()方法不适用多选框，只能适用于可编辑输入框*/
	public void checkBox(){
		WebElement check= driver.findElement(By.id("auto-signin"));
		System.out.println("是否是选择了呢？"+check.isSelected());
		System.out.println("是否是可选择的呢？"+check.isEnabled());
		check.click();
		System.out.println("是否是选择了呢？"+check.isSelected());
	}
	/*
	 * 按钮  isEnabled()方法不适用于Button*/
	public void button(){
		WebElement login_button= driver.findElement(By.className("moco-btn"));
		//模拟手动将登陆按钮隐藏掉
		String jScript="document.getElementsByClassName('moco-btn')[0].style.display='none'";
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript(jScript);
		boolean is_display=login_button.isDisplayed();
        System.out.println("登录按钮是否显示呢"+is_display);
        //boolean is_enabled=login_button.isEnabled();
        //System.out.println(is_enabled);
		login_button.click();	
		
	}
	/*
	 * Form表单提交
	 * 整个登录的Form表单的ID为signup-form*/
	public void webForm(){
		
		driver.findElement(By.id("signup-form")).submit();
	}
	/*
	 * 上传文件*/
	public void upHeader(){
		//点击更换头像
		driver.get("https://www.imooc.com/user/setbindsns");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement click_header=driver.findElement(By.linkText("更换头像"));
		/*由于更换头像是鼠标悬停时才能显示，此时需要手动将<div class="update-avator" style="bottom: -30px;">
		 * 中 -30px 改为 0px 才可以直接点击更换头像*/
		
		String jScrip="document.getElementsByClassName('update-avator')[0].style.bottom='0'";
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript(jScrip);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		click_header.click();
	    try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//点击上传头像并上传
		driver.findElement(By.id("upload")).sendKeys("F:\\selenium\\示例图片_03.JPG");
		  try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//头像上传成功后点击确定按钮
		driver.findElement(By.linkText("确定")).click();		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ActionSelenium action = new ActionSelenium();
		action.InitDriver();
		//action.checkBox();
		//action.button();
		action.InputBox();
		action.upHeader();
		//action.webForm();
		//action.RedioBox();
	}

}
