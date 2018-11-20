package org.dhy.Components;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @ClassName: Reader
 * @Author: dhy
 * @Date: 2018/11/20 8:55 PM
 **/
public class Reader {

    public XWPFDocument getDoc(String filePath) throws IOException {
        return new XWPFDocument(new FileInputStream(filePath));
    }


}
