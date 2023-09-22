package com.daniu.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public  final class BaseQRcode {

    public static void getBaseQRcode(String text,String downloadPath) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\fangd\\.cache\\selenium\\chromedriver\\win64\\116.0.5845.96\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--incognito");
        options.addArguments("--headless");
        options.setExperimentalOption("prefs", getDownloadPrefs(downloadPath));

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://qrcode.antfu.me");
        //Thread.sleep(5000);
        //点击L按钮
        driver.findElement(By.xpath("//input[@value='L']")).click();
        //点击180按钮
        driver.findElement(By.xpath("//input[@value='180']")).click();
        //点击random按钮
        driver.findElement(By.xpath("//input[@value='random']")).click();
        //设置margin
        WebElement margin = driver.findElement(By.xpath("//div[3]/input"));
        margin.clear();
        margin.sendKeys("3");

        //设置margin noise
        driver.findElement(By.cssSelector("div:nth-child(2) > input:nth-child(2)")).click();
        WebElement noise = driver.findElement(By.xpath("(//input[@type='number'])[2]"));
        noise.clear();
        noise.sendKeys("0.12");
        WebElement opacity = driver.findElement(By.xpath("(//input[@type='number'])[3]"));
        opacity.clear();
        opacity.sendKeys("0.79");

        //设置seed
        WebElement seed = driver.findElement(By.xpath("(//input[@type='number'])[4]"));
        seed.clear();
        seed.sendKeys("684159");
        //设置crystalize值
        driver.findElement(By.xpath("//input[@value='crystalize']")).click();
        WebElement crystalize = driver.findElement(By.xpath("(//input[@type='number'])[8]"));
        crystalize.clear();
        crystalize.sendKeys("6.5");

        //设置内容
        WebElement inputText = driver.findElement(By.xpath("//textarea"));
        inputText.clear();
        inputText.sendKeys(text);

        Thread.sleep(3000);
        //点击Download按钮
        driver.findElement(By.xpath("//button[contains(.,'Download')]")).click();
        Thread.sleep(10000);
        driver.quit();
    }

    private static Map getDownloadPrefs(String downloadPath) {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadPath);
        return prefs;
    }

}
