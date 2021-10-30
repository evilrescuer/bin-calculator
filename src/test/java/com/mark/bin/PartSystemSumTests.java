package com.mark.bin;

import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class PartSystemSumTests {

    @Test
    void contextLoads() {
        readPartRecordExcel();
    }

    private void readPartRecordExcel() {
        String inputExcelFilename = Thread.currentThread().getContextClassLoader().getResource("").getPath() + File
        .separator + "excel_template" + File.separator  + "每月耗材.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        PartRecordListener listener = new PartRecordListener();
        // 忽略表头行
        EasyExcel.read(inputExcelFilename, PartRecord.class, listener).sheet("9配件").headRowNumber(2).doRead();
    }

}
