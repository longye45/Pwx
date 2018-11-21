package org.dhy.Template;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;

import java.io.File;
import java.util.List;

/**
 * @ClassName: Test
 * @Author: dhy
 * @Date: 2018/11/21 4:43 PM
 **/
public class Test {

    public static void main(String[] args) throws Exception {
        File pathToBinary = new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        FirefoxBinary firefoxBinary = new FirefoxBinary(pathToBinary);
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        System.setProperty("webdriver.gecko.driver", "E:\\geckodriver.exe");

        //初始化一个火狐浏览器实例，实例名称叫driver
        FirefoxDriver webDriver = new FirefoxDriver();
        webDriver.get("https://wenku.baidu.com/view/554dc4f576eeaeaad1f330d3.html?from=search");
        WebElement elementByXPath = null;
        try {
            elementByXPath = ((FirefoxDriver) webDriver).findElementByXPath("//*[@id=\"html-reader-go-more\"]/div[2]/div[1]/span/span[2]");
            webDriver.executeScript("window.scrollTo(0,4000)");
            elementByXPath.click();
        } catch (Exception e) {

        }

        List<WebElement> elementsByClassName = ((FirefoxDriver) webDriver).findElementsByClassName("reader-txt-layer");
        for (int i = 0; i < elementsByClassName.size(); i++) {
            WebElement webElement = elementsByClassName.get(i);
            System.out.println(webElement.getText().replaceAll("\n",""));
        }
        webDriver.close();
    }
}
