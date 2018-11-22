package org.dhy.Util;

import com.google.common.base.Strings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @ClassName: TextToFile
 * @Author: dhy
 * @Date: 2018/11/22 2:32 PM
 **/
public class TextToFile {

    public static void convert2Txt(String filePath, String content){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath),true));
            writer.write("\n"+content);
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
