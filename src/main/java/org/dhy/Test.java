package org.dhy;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @ClassName: Test
 * @Author: dhy
 * @Date: 2018/11/20 7:14 PM
 **/
public class Test {
    public static final String ANSER = "ANSER";
    public static final String QUESTION = "QUESTION";

    public static void main(String[] args) throws Exception {
        String file = "/Users/dhy/Downloads/20180910福建同安中医岗真题解析/MMC福建-同安-中医岗-马瑞鸿.docx";
        version(file, ANSER);
        version(file, QUESTION);

    }

    public static void version(String filePath, String type) throws Exception {
        XWPFDocument doc = new XWPFDocument(new FileInputStream(filePath));
        XWPFDocument newDoc = new XWPFDocument();
        List<XWPFParagraph> paragraphs = doc.getParagraphs();
        int n = 0;
        for (int i = 0; i < paragraphs.size(); i++) {
            XWPFParagraph xwpfParagraph = paragraphs.get(i);
            xwpfParagraph.setSpacingLineRule(LineSpacingRule.AUTO);
            xwpfParagraph.setSpacingBetween(1.5);
            if (type.equalsIgnoreCase(ANSER) ? filterAnser(xwpfParagraph) : filterQuestion(xwpfParagraph)) {
                continue;
            }
            newDoc.createParagraph();
            newDoc.setParagraph(xwpfParagraph, n++);
        }
        FileOutputStream out = new FileOutputStream(type + ".docx");
        newDoc.write(out);
        out.close();
    }

    public static void removeIllagleEndStr(XWPFParagraph xwpfParagraph) {
        if (checkIllagle(xwpfParagraph.getText().replaceAll(" ", ""), ",", ".", "()", "（）", "，", "。")) {
            int size = xwpfParagraph.getRuns().size();

            for (int i = 0; i < size; i++) {
                xwpfParagraph.removeRun(i);
            }
            if (xwpfParagraph.getRuns().size()==0){
                return;
            }
            xwpfParagraph.getRuns().get(0).setText(xwpfParagraph.getText().replaceAll("[,|.|\\(\\)|（）|，|。]", ""));
        }
    }

    public static boolean checkIllagle(String... str) {
        boolean flag = false;
        for (int i = 1; i < str.length; i++) {
            flag = str[0].endsWith(str[i]);
        }
        return flag;
    }

    public static boolean filterQuestion(XWPFParagraph xwpfParagraph) {
        return xwpfParagraph.getText().matches("[A-Z].*") ||
                xwpfParagraph.getText().matches("【[0-9A-Z]+】.*")
                || xwpfParagraph.getText().matches("【知识点】.*");
    }

    public static boolean filterAnser(XWPFParagraph xwpfParagraph) {
        return xwpfParagraph.getText().matches("【(知识点|答案|解析)】.*") || xwpfParagraph.getText().matches("[0-9]+\\..*");
    }

    public static final void test() throws Exception {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph p = doc.createParagraph();
        p.setAlignment(ParagraphAlignment.CENTER);
        p.setBorderBottom(Borders.DOUBLE);
        p.setBorderTop(Borders.DOUBLE);
        p.setBorderRight(Borders.DOUBLE);
        p.setBorderLeft(Borders.DOUBLE);
        XWPFRun r = p.createRun();
        r.setText("POI创建的Word段落文本");
        r.setBold(true);
        r.setColor("FF0000");
        r.setFontFamily("宋体");

        p = doc.createParagraph();
        r = p.createRun();
        r.setText("POI读写Excel功能强大、操作简单。");
        r.setFontSize(12);
        XWPFTable table = doc.createTable(3, 3);
        table.getRow(0).getCell(0).setText("表格1");
        table.getRow(1).getCell(1).setText("表格2");
        table.getRow(2).getCell(2).setText("表格3");
        FileOutputStream out = new FileOutputStream(System.getProperty("user.dir") + "sample.docx");
        doc.write(out);
        out.close();
    }
}
