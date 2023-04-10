package com.FanHA.util;

import com.FanHA.pojo.Items;
import com.FanHA.pojo.Topic.Topic;
import com.FanHA.pojo.Topic.TopicSelect;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author HeTao
 * @data 2023/4/10
 **/
public class BulkImport {
    private String path;
    private String type;
    private final List<TopicSelect> topics;

    public BulkImport(String path, String type) throws ClassNotFoundException {
        this.path = path;
        this.type = "com.FanHA.pojo.Topic." + "Topic" + type;
        topics = new ArrayList<>();
    }

    public void Bulk() {
        this.path = java.net.URLDecoder.decode(this.path, StandardCharsets.UTF_8);
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            HSSFWorkbook wb = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheetAt = wb.getSheetAt(0);

            int lastRowNum = sheetAt.getLastRowNum();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

            for (int i = 0; i < lastRowNum; i++) {
                HSSFRow row = sheetAt.getRow(i);
                if (row.getCell(0) == null) continue;

                for (int j = 0; j < 5; j++) row.getCell(j).setCellType(CellType.STRING);
                row.getCell(5).setCellType(CellType.NUMERIC);

                String title = row.getCell(0).getStringCellValue();
                String date = format.format(System.currentTimeMillis());
                String[] options = new String[4];
                options[0] = row.getCell(1).getStringCellValue();
                options[1] = row.getCell(2).getStringCellValue();
                options[2] = row.getCell(3).getStringCellValue();
                options[3] = row.getCell(4).getStringCellValue();
                int answer = (int) row.getCell(5).getNumericCellValue();
                String Answer = row.getCell(answer).getStringCellValue();

                TopicSelect topicSelect = new TopicSelect(title, 4, format.format(System.currentTimeMillis()), options, Answer);

                topics.add(topicSelect);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        BulkImport select = new BulkImport("C:\\Users\\F Sweet\\Desktop\\demo.xls", "Select");
        select.Bulk();

        for (TopicSelect topic : select.topics) System.out.println(topic);

    }

}
