package InputPackage;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionSelenium {

	public WebDriver driver;
	public String windowHandle;

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

		// 定位用户名密码，并且点击登录按钮
		user.sendKeys("13998538053");
		driver.findElement(By.name("password")).sendKeys("ww744934");
		driver.findElement(By.xpath(".//*[@id='signup-form']/div[5]/input")).click();
		this.sleepTime(3000);
	}

	/*
	 * <div class="moco-control-input rlf-group rlf-radio-group"> <label> <input
	 * hidefocus="true" value="0" name="sex" type="radio"> 保密</label> <label>
	 * <input hidefocus="true" value="1" name="sex" type="radio"> 男 </label>
	 * <label> <input hidefocus="true" value="2" checked="checked" name="sex"
	 * type="radio"> 女 </label></div> 单选框操作
	 */
	public void RadioBox() {
		driver.get("https://www.imooc.com/user/setprofile");
		this.sleepTime(3000);
		/**
		 * document.getElementById('J_GotoTop').style.display='none' 用JS隐藏右侧遮罩
		 */
		String jsScrip = "document.getElementById('J_GotoTop').style.display='none'";
		// 将driver转换成js执行
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(jsScrip);

		// 点击‘编辑’按钮进入编辑个人信息页面
		driver.findElement(By.className("pull-right")).click();
		WebElement sex_node = driver.findElement(By.xpath(".//*[@id='setting-profile']/div[2]/a"));
		// 获取元素组，之后通过for循环模式取到值
		List<WebElement> sex_list = sex_node.findElements(By.tagName("input"));
		for (WebElement sex : sex_list) {
			boolean flag = sex.isSelected();
			if (flag == true) {
				continue;
			} else {
				sex.click();
				break;
			}
		}
	}

	/*
	 * 复选框操作 clear()方法不适用多选框，只能适用于可编辑输入框
	 */
	public void checkBox() {
		WebElement check = driver.findElement(By.id("auto-signin"));
		System.out.println("是否是选择了呢？" + check.isSelected());
		System.out.println("是否是可选择的呢？" + check.isEnabled());
		check.click();
		System.out.println("是否是选择了呢？" + check.isSelected());
	}

	/*
	 * 按钮 isEnabled()方法不适用于Button
	 */
	public void button() {
		WebElement login_button = driver.findElement(By.className("moco-btn"));
		// 模拟手动将登陆按钮隐藏掉
		String jScript = "document.getElementsByClassName('moco-btn')[0].style.display='none'";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(jScript);
		boolean is_display = login_button.isDisplayed();
		System.out.println("登录按钮是否显示呢" + is_display);
		// boolean is_enabled=login_button.isEnabled();
		// System.out.println(is_enabled);
		login_button.click();
	}

	/*
	 * Form表单提交 整个登录的Form表单的ID为signup-form
	 */
	public void webForm() {

		driver.findElement(By.id("signup-form")).submit();
	}

	/*
	 * 上传文件 方法一：用js手动改动js代码，使其【更换头像】直接显示 且使用sendkeys方法时，标签必须是input
	 */
	public void upHeader() {
		// 点击更换头像
		driver.get("https://www.imooc.com/user/setbindsns");
		this.sleepTime(3000);
		WebElement click_header = driver.findElement(By.linkText("更换头像"));
		/*
		 * 由于更换头像是鼠标悬停时才能显示，此时需要手动将<div class="update-avator"
		 * style="bottom: -30px;"> 中 -30px 改为 0px 才可以直接点击更换头像
		 */

		String jScrip = "document.getElementsByClassName('update-avator')[0].style.bottom='0'";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(jScrip);
		this.sleepTime(3000);
		click_header.click();
		this.sleepTime(3000);
		// 点击上传头像并上传
		driver.findElement(By.id("upload")).sendKeys("F:\\selenium\\示例图片_03.JPG");
		this.sleepTime(3000);
		// 头像上传成功后点击确定按钮
		driver.findElement(By.linkText("确定")).click();
	}

	/*
	 * 上传文件 方法二：使用鼠标滑动方法 且使用sendkeys方法时，标签必须是input
	 */
	public void upFile() {
		driver.get("https://www.imooc.com/user/setbindsns");
		this.sleepTime(3000);
		WebElement header = driver.findElement(By.className("avator-img"));
		Actions action = new Actions(driver);
		// 鼠标滑动方法
		action.moveToElement(header).perform();
		this.sleepTime(2000);
		driver.findElement(By.linkText("更换头像")).click();
		this.sleepTime(3000);
		// 点击上传头像并上传
		driver.findElement(By.id("upload")).sendKeys("F:\\selenium\\示例图片_03.JPG");
		this.sleepTime(3000);
		// 头像上传成功后点击确定按钮
		driver.findElement(By.linkText("确定")).click();
	}

	/*
	 * 上传文件 方法三：由于系统文件脚本无法识别，推荐使用AutoIT工具实现文件上传
	 */
	public void upFileWithAutoIt() {
		driver.get("https://www.imooc.com/user/setbindsns");
		this.sleepTime(3000);
		WebElement header = driver.findElement(By.className("avator-img"));
		Actions action = new Actions(driver);
		// 鼠标滑动方法
		action.moveToElement(header).perform();
		this.sleepTime(2000);
		driver.findElement(By.linkText("更换头像")).click();
		this.sleepTime(3000);
		// 点击上传头像
		driver.findElement(By.linkText("上传头像")).click();
		try {
			Runtime.getRuntime().exec("F:\\selenium\\TestForUploadFile\\testAutoIt.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 头像上传成功后点击确定按钮
		driver.findElement(By.linkText("确定")).click();
	}

	/*
	 * 下拉框操作
	 */
	public void downSelectBox() {
		driver.get("https://www.imooc.com/user/setprofile");
		this.sleepTime(3000);
		// 进入个人信息页面
		driver.findElement(By.linkText("个人信息")).click();
		this.sleepTime(3000);
		// 点击编辑进入编辑页面
		driver.findElement(By.className("pull-right")).click();
		this.sleepTime(2000);

		WebElement jobSelect = driver.findElement(By.xpath("//form[@id='profile']/div[2]/*/select"));

		Select jobDownList = new Select(jobSelect);
		// 方法一：通过selectByIndex选择下拉列表中内容
		// jobDownList.selectByIndex(3);
		// 方法二：通过selectByValue选择下拉列表中内容
		// jobDownList.selectByValue("10");
		// 方法三：通过selectByVisibleText选择下拉列表中内容
		jobDownList.selectByVisibleText("Web前端工程师");
		System.out.println("验证当前下拉框是否为多选" + jobDownList.isMultiple());
		// deselectByVisibleText方法只能适用于多选下拉列表
		// jobDownList.deselectByVisibleText("Web前端工程师");
		// 下拉框操作：getAllSelectedOptions()//getFirstSelectedOption().getText()
		// getAllSelectedOptions方法也一般用于多选下拉列表
		List<WebElement> list = jobDownList.getAllSelectedOptions();
		for (WebElement option : list) {
			System.out.println(option.getText());
		}
		// 获取当前页面选中的值getFirstSelectedOption().getText()
		jobDownList.getFirstSelectedOption().getText();
		System.out.println(jobDownList.getFirstSelectedOption().getText());
	}

	/*
	 * 元素进阶 Actions action=new Actions(dirver) 鼠标左击：action.click().perform()
	 * 鼠标右击：action.contextClick().perform() 鼠标双击：action.doubleClick().perform()
	 * 鼠标悬停：action.moveToElement().perform();
	 */
	public void mouseClick() {
		// WebElement loginElement=driver.findElement(By.id("js-signin-btn"));
		// 将driver装换成action
		Actions action = new Actions(driver);

		WebElement loginElement = driver.findElement(By.id("js-signin-btn"));

		action.click(loginElement).perform();

	}

	/*
	 * 特殊窗口处理----iframe切换
	 */
	public void iframe() {
		driver.get("https://www.imooc.com/u/5535563/articles");
		//点击写文章进入编辑页面
		driver.findElement(By.linkText("写文章")).click();
		this.sleepTime(3000);
		WebElement iframe = driver.findElement(By.id("ueditor_0"));
		driver.switchTo().frame(iframe);
		driver.findElement(By.tagName("body")).sendKeys("this is a test");
	}	
    /*
     * 特殊窗口处理----窗口切换
     * */	
	public void windowHand(){		
		driver.get("https://www.imooc.com");
		WebElement webHtml= driver.findElement(By.linkText("前端 / 小程序 / JS"));
		Actions mouse=new Actions(driver);		
		mouse.moveToElement(webHtml).perform();
		//获取当前window的handle
		windowHandle= driver.getWindowHandle();
	    driver.findElement(By.linkText("HTML/CSS")).click();
		this.sleepTime(3000);
		//获取到所有的handles
		Set<String> hanldes =driver.getWindowHandles();
		for(String s:hanldes){
			if(s.equals(windowHandle)){
				continue;			
			}
			System.out.println(s);
		    driver.switchTo().window(s);
			//System.out.println(driver.switchTo().window(s));			
		}
		driver.findElement(By.linkText("高级")).click();
	}

	/*等待*/
	public void waitForElement(){
		//强制等待
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//显示等待：在规定的时间内找到元素就继续操作
		WebDriverWait wait = new WebDriverWait(driver, 10);		
		//隐示等待：适用于全局应用，10秒内中找不到元素就报错
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("test")));
	}
	/* 等待时间 */
	public void sleepTime(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ActionSelenium action = new ActionSelenium();
		action.InitDriver();
		// action.checkBox();
		// action.button();
		action.InputBox();
		action.windowHand();
		//action.mouseClick();
		//action.iframe();
		// action.downSelectBox();
		// action.upFileWithAutoIt();
		// action.upFile();
		// action.upHeader();
		// action.webForm();
		// action.RedioBox();
	}

}
