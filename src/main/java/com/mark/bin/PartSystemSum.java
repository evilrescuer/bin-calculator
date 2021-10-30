package com.mark.bin;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhimao.lin
 */
@Data
public class PartSystemSum {
    // 需要指定index

    @ExcelProperty(index = 0, value = "医生姓名")
    private String doctorName;
    @ExcelProperty(index = 1, value = "配件系统")
    private String partSystem;
    @ExcelProperty(index = 2, value = "转移杆总个数")
    private Integer transferRodCount;
    @ExcelProperty(index = 3, value = "替代体总个数")
    private Integer substituteCount;
    @ExcelProperty(index = 4, value = "愈合基台总个数")
    private Integer healingAbutmentCount;
    @ExcelProperty(index = 5, value = "封闭螺丝型号总个数")
    private Integer closureScrewCount;
}
