package com.mark.bin;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhimao.lin
 */
@Data
public class PartRecord implements Serializable {
    @ExcelProperty(index = 0) //医生姓名
    private String doctorName;
    @ExcelProperty(index = 1) //领取日期
    private String collectDate;
    @ExcelProperty(index = 2) //患者姓名
    private String patientName;
    @ExcelProperty(index = 3) //配件系统
    private String partSystem;
    @ExcelProperty(index = 4) //转移杆型号
    private String transferRodModel;
    @ExcelProperty(index = 5) //个数
    private Integer transferRodCount;
    @ExcelProperty(index = 6) //替代体型号
    private String substituteModel;
    @ExcelProperty(index = 7) //个数
    private Integer substituteCount;
    @ExcelProperty(index = 8) //愈合基台型号
    private String healingAbutmentModel;
    @ExcelProperty(index = 9) //个数
    private Integer healingAbutmentCount;
    @ExcelProperty(index = 10) //封闭螺丝型号
    private String closureScrewModel;
    @ExcelProperty(index = 11) //个数
    private Integer closureScrewCount;
    @ExcelProperty(index = 12) //几期
    private String periodDescription;
    @ExcelProperty(index = 13) //备注
    private String note;
}
