package com.mark.bin;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;

import javax.management.monitor.StringMonitor;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhimao.lin
 */
public class PartRecordListener extends AnalysisEventListener<PartRecord> {
    private List<PartRecord> inputList = new ArrayList();
    private List<PartSystemSum> outputList = new ArrayList();
    private String lastDoctorName = "";


    /**
     * 这个每一条数据解析都会来调用
     * @param partRecord
     * @param analysisContext
     */
    @Override
    public void invoke(PartRecord partRecord, AnalysisContext analysisContext) {

        // 忽略空行
        if(partRecord.getPartSystem()== null ||
                partRecord.getPartSystem().trim().isEmpty()
        ) {
            return;
        }
        // 设置默认值
        if (partRecord.getTransferRodCount() == null) {
            partRecord.setTransferRodCount(0);
        }
        if (partRecord.getSubstituteCount() == null) {
            partRecord.setSubstituteCount(0);
        }
        if (partRecord.getHealingAbutmentCount() == null) {
            partRecord.setHealingAbutmentCount(0);
        }
        if (partRecord.getClosureScrewCount() == null) {
            partRecord.setClosureScrewCount(0);
        }

        if (partRecord.getDoctorName() == null || partRecord.getDoctorName().trim().isEmpty()) {
            // 设置为上一次的
            partRecord.setDoctorName(lastDoctorName);
        } else {
            // 记住上次的 医生名字
            lastDoctorName = partRecord.getDoctorName();
        }
        inputList.add(partRecord);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        super.invokeHead(headMap, context);
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        // 防止多表头 数据转化错误
//        super.onException(exception, context);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        writePartSystemSumExcel();
    }

    private void writePartSystemSumExcel() {
        generateDataForWrite();
        String outputExcelFilename = Thread.currentThread().getContextClassLoader().getResource("").getPath() + File.separator + "excel_template" + File.separator + "测试.xlsx";
        EasyExcel.write(outputExcelFilename, PartSystemSum.class).sheet("10月配件-统计").doWrite(outputList);
    }

    /**
     * 要求：需要保证【配件系统】完整性
     * 1.对于需要统计的行，【配件系统】不能为空
     * 2.工作模式：先按照【医生姓名】分组，再按照【配件系统】分组
     * 3.某些软件，如WPS对输出对Excel有缓存，需要关闭后，再打开新生成对excel
     */
    private void generateDataForWrite() {
        System.out.println(inputList);
        // 第一层分组：医生姓名 分组
        LinkedHashMap<String, List<PartRecord>> doctorNameGroup = inputList.stream().collect(
                Collectors.groupingBy(PartRecord::getDoctorName, LinkedHashMap::new, Collectors.toList())
        );
        for (Map.Entry entry: doctorNameGroup.entrySet()) {
            String doctorName = (String) entry.getKey();
            List<PartRecord> sameDoctorPartRecordList = (List<PartRecord>) entry.getValue();

            // 第二层分组：配件系统 分组
            LinkedHashMap<String, List<PartRecord>> samePartSystemGroup = sameDoctorPartRecordList.stream().collect(
                    Collectors.groupingBy(PartRecord::getPartSystem, LinkedHashMap::new, Collectors.toList())
            );

            for (Map.Entry entry2: samePartSystemGroup.entrySet()) {
                String partSystem = (String) entry2.getKey();
                List<PartRecord> samePartSystemPartRecordList = (List<PartRecord>) entry2.getValue();

                int totalTransferRodCount = samePartSystemPartRecordList.stream().mapToInt(partRecord -> partRecord.getTransferRodCount()).sum();
                int totalSubstituteCount = samePartSystemPartRecordList.stream().mapToInt(partRecord -> partRecord.getSubstituteCount()).sum();
                int totalHealingAbutmentCount = samePartSystemPartRecordList.stream().mapToInt(partRecord -> partRecord.getHealingAbutmentCount()).sum();
                int totalClosureScrewCount = samePartSystemPartRecordList.stream().mapToInt(partRecord -> partRecord.getClosureScrewCount()).sum();

                PartSystemSum partSystemSum = new PartSystemSum();
                partSystemSum.setDoctorName(doctorName);
                partSystemSum.setPartSystem(partSystem);
                partSystemSum.setTransferRodCount(totalTransferRodCount);
                partSystemSum.setSubstituteCount(totalSubstituteCount);
                partSystemSum.setHealingAbutmentCount(totalHealingAbutmentCount);
                partSystemSum.setClosureScrewCount(totalClosureScrewCount);

                outputList.add(partSystemSum);
            }
        }
        System.out.println(outputList);
    }
}
