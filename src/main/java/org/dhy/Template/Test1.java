package org.dhy.Template;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * @author longy
 * @Title: Test1
 * @ProjectName poiWord
 * @Description: TODO
 * @date 2018/11/21 21:42
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "E:\\软件备份\\chromedriver.exe");
        //初始化一个火狐浏览器实例，实例名称叫driver
        ChromeDriver webDriver = new ChromeDriver();
        webDriver.get("https://wenku.baidu.com/view/3eb27b7c561252d380eb6e77.html?from=search");
        WebElement elementByXPath = null;
        int siz = 500;
        while (true){
            try {
                elementByXPath = webDriver.findElementByXPath("//*[@id=\"html-reader-go-more\"]/div[2]/div[1]/span/span[2]");
                webDriver.executeScript("window.scrollTo(0," + siz + ")");
                elementByXPath.click();
                break;
            } catch (Exception e) {
                siz+=500;
            }
        }
        Thread.sleep(10000);
        List<WebElement> elementsByClassName = webDriver.findElementsByClassName("reader-word-layer");
        for (int i = 0; i < elementsByClassName.size(); i++) {
            WebElement webElement = elementsByClassName.get(i);
            System.out.println(webElement.getText().replaceAll("\n", ""));
        }
        System.out.println(elementsByClassName.get(elementsByClassName.size()-1).toString());
        webDriver.close();
    }
}
