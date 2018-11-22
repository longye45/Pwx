package org.dhy.Template;

import com.google.common.base.Strings;
import org.dhy.Util.TextToFile;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.List;

/**
 * @author longy
 * @Title: Test1
 * @ProjectName poiWord
 * @Description: TODO
 * @date 2018/11/21 21:42
 */
public class Test1 {

    public static void convertBd2Txt(String url, String filePath) {
        System.setProperty("webdriver.chrome.driver", "/Users/dhy/Downloads/chromedriver");
        //初始化一个火狐浏览器实例，实例名称叫driver
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        ChromeDriver webDriver = new ChromeDriver(ChromeDriverService.createDefaultService(), options);
        try {
            webDriver.get(url);

            Integer pagesNum = 3;

            WebElement pagesContent = null;
            try {
                pagesContent = webDriver.findElementByXPath("//*[@id=\"html-reader-go-more\"]/div[2]/div[1]/span/span[1]");
            } catch (Exception e) {

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
                dealWithParagraph(elementById, filePath);
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
        } catch (Exception e) {

        } finally {
            webDriver.close();
            webDriver.quit();
        }

    }

    public static void dealWithParagraph(WebElement webPageElement, String filePath) {
        Integer lastTop = 0;
        List<WebElement> elements = webPageElement.findElements(By.className("reader-word-layer"));
        for (int i = 0; i < elements.size(); i++) {
            WebElement webElement = elements.get(i);
            String topStr = webElement.getCssValue("top").replace("px", "");
            if (!lastTop.equals(Integer.valueOf(topStr))) {
                lastTop = Integer.valueOf(topStr);
                TextToFile.convert2Txt(filePath, "\n" + webElement.getText());
            } else {
                TextToFile.convert2Txt(filePath, webElement.getText());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        convertBd2Txt("https://wenku.baidu.com/view/4309e57e27284b73f2425026.html", System.getProperty("user.home") + File.separator + "b.txt");
    }
}
