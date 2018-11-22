package org.dhy.Template;

import com.google.common.base.Strings;
import org.dhy.Util.TextToFile;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

/**
 * @author longy
 * @Title: Test1
 * @ProjectName poiWord
 * @Description: TODO
 * @date 2018/11/21 21:42
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "/Users/dhy/Downloads/chromedriver");
        //初始化一个火狐浏览器实例，实例名称叫driver
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        ChromeDriver webDriver = new ChromeDriver(ChromeDriverService.createDefaultService(), options);
        webDriver.get("https://wenku.baidu.com/view/4847517a0640be1e650e52ea551810a6f524c8ef.html");

        Integer pagesNum = 3;

        WebElement pagesContent = null;
        try {
            pagesContent = webDriver.findElementByXPath("//*[@id=\"html-reader-go-more\"]/div[2]/div[1]/span/span[1]");
        }catch (Exception e){

        }
        if (pagesContent != null) {
            String text = pagesContent.getText();
            pagesNum += Integer.valueOf(text.replace("还剩", "").replace("页未读，", ""));
        }

        Integer currentScor = 0;

        for (int i = 1; i <= pagesNum; i++) {
            Thread.sleep(500);
            WebElement elementById = webDriver.findElementById("pageNo-" + i);
            if (Strings.isNullOrEmpty(elementById.getText())) {
                i--;
                webDriver.executeScript("scrollTo(0," + (currentScor += 100) + ")");
                continue;
            }
            webDriver.executeScript("scrollTo(0," + (currentScor += 500) + ")");
            System.out.println(elementById.getText().replaceAll("\n", ""));
            TextToFile.convert2Txt(System.getProperty("user.home") + File.separator + "test.txt", elementById.getText().replaceAll("\n", ""));
            if (i == 3) {
                while (true) {
                    try {
                        webDriver.findElementByXPath("//*[@id=\"html-reader-go-more\"]/div[2]/div[1]/span/span[2]").click();
                        break;
                    } catch (Exception e) {
                        webDriver.executeScript("scrollTo(0," + (currentScor += 100) + ")");
                    }
                }
            }

            if (i % 50 == 0) {
                webDriver.get(webDriver.getCurrentUrl() + "&pn=" + (i + 1));
                currentScor = 0;
            }
        }

        webDriver.close();
        webDriver.quit();
    }
}
