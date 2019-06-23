package com.mLukasik;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KantynaApplicationTests {

	@Test
	public void contextLoads() 
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\asu\\Desktop\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8888/logowanie");
        //driver.findElement(By.xpath("//a[contains(text(),'Zaloguj')]")).click();
        driver.findElement(By.id("pole1")).sendKeys("Admin");
        driver.findElement(By.id("pole2")).sendKeys("Haslo");
        driver.findElement(By.id("logowanko")).click();
	}

}
