package com.zjj.excel.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 *
 *  没有index的时候 顺序要与excel表中的表头对应
 */
@Data
//@HeadRowHeight(40)  //表头行高
//@ContentRowHeight(50) //内容行高
public class Student {


    /*
    *  @ExcelProperty(value = "编号",index = 3)  建议使用一个属性 ，都会根据其中一个属性来匹配的
    * */
    /**
     * id
     */
    @ExcelIgnore  //忽略该字段
    //@ExcelProperty(value = "编号",index = 3)
    private String id;

    /**
     * 学生姓名
     */
    //@ColumnWidth(20)
    //@ExcelProperty(value = {"学生信息表","学生姓名"} ,index = 0)
    @ExcelProperty("学生姓名")
    private String name;

    /**
     * 学生出生日期
     */
    //@DateTimeFormat("yyyy-MM-dd")
    //@ColumnWidth(20)
    //@ExcelProperty(value = {"学生信息表","出生日期"},index = 1)
    @ExcelProperty("出生日期")
    private Date birthday;
    /**
     * 学生性别
     */
    //@ExcelProperty(value = {"学生信息表","学生性别"},index = 2)
    @ExcelProperty("性别")
    private String gender;

}
