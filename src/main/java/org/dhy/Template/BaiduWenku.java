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
import java.util.Scanner;

/**
 * @author longy
 * @Title: BaiduWenku
 * @ProjectName poiWord
 * @Description: TODO
 * @date 2018/11/21 21:42
 */
public class BaiduWenku {

    public static void convertBd2Txt(String url) {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.home") + File.separator + "chromedriver");
        //初始化一个火狐浏览器实例，实例名称叫driver
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        ChromeDriver webDriver = new ChromeDriver(ChromeDriverService.createDefaultService(), options);
        try {
            webDriver.get(url);

            Integer pagesNum = 3;
            Boolean isTxt = false;
            String title = null;
            try {
                title = webDriver.findElementById("doc-tittle-2").getText();
            } catch (Exception e) {
                title = webDriver.findElementById("doc-tittle-0").getText();
            }
            String filePath = System.getProperty("user.home") + File.separator + title + ".txt";
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
                Thread.sleep(200);
                WebElement elementById = null;
                try {
                    elementById = webDriver.findElementById("pageNo-" + i);
                } catch (Exception e) {
                    elementById = webDriver.findElementById("reader-pageNo-" + i);
                    isTxt = true;
                }
                if (Strings.isNullOrEmpty(elementById.getText())) {
                    i--;
                    webDriver.executeScript("scrollTo(0," + (currentScor += 100) + ")");
                    continue;
                }
                System.out.println("共有" + pagesNum + "页，当前第" + i + "页面");
                webDriver.executeScript("scrollTo(0," + (currentScor += 500) + ")");
                dealWithParagraph(elementById, filePath, isTxt);
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
            System.out.println("文件生成路径为" + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webDriver.close();
            webDriver.quit();
        }

    }

    public static void dealWithParagraph(WebElement webPageElement, String filePath, boolean isTxt) {
        Integer lastTop = 0;
        if (isTxt) {
            List<WebElement> elements = webPageElement.findElements(By.className("p-txt"));
            for (int i = 0; i < elements.size(); i++) {
                TextToFile.convert2Txt(filePath, "\n" + elements.get(i).getText());
            }
            return;
        }
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
        System.out.println("请输入百度文库链接：");
        Scanner scanner = new Scanner(System.in);
        String url = scanner.next();
        System.out.println("请等待文件生成。。。");
        convertBd2Txt(url);
    }
}
