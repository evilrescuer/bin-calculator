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

    /**
     * 步骤：
     * 1.删除test-classes/excel_template下的《测试.xlsx》
     * 2.运行测试
     * 3.打开《测试.xlsx》（之前关闭相关文件，防止阅读软件（如wps）的缓存）
     */
    private void readPartRecordExcel() {
        String inputExcelFilename = Thread.currentThread().getContextClassLoader().getResource("").getPath() + File
        .separator + "excel_template" + File.separator  + "每月耗材.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        PartRecordListener listener = new PartRecordListener();
        // 忽略表头行
        EasyExcel.read(inputExcelFilename, PartRecord.class, listener).sheet("9配件").headRowNumber(2).doRead();
    }

}
