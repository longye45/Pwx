package org.dhy.Template;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

/**
 * @ClassName: Test
 * @Author: dhy
 * @Date: 2018/11/21 4:43 PM
 **/
public class Test {
    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "/Users/dhy/Downloads/chromedriver");
        ChromeOptions a = new ChromeOptions();
//        a.setHeadless(true);
        ChromeDriver webDriver = new ChromeDriver(ChromeDriverService.createDefaultService(),a);
        webDriver.get("https://wenku.baidu.com/view/6aab52740242a8956aece411.html");
        int start = webDriver.findElementsByClassName("reader-txt-layer").size();

        System.out.println(webDriver.findElementsByXPath("//div[@id='html-reader-go-more']/div[2]/div/span/span[2]").size());
        webDriver.findElementByXPath("//div[@id='html-reader-go-more']/div[2]/div/span/span[2]").click();
        while (webDriver.findElementsByClassName("reader-txt-layer").size() >= start){
            System.out.println(webDriver.findElementsByClassName("reader-txt-layer").size());
        }
//        List<WebElement> elementsByClassName = webDriver.findElementsByClassName("reader-txt-layer");
//        for (int i = 0; i < elementsByClassName.size(); i++) {
//            System.out.println(elementsByClassName.get(i).getText());
//        }
        webDriver.close();
    }
}
