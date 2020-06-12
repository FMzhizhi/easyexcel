package com.zjj.excel.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author zhijiaju
 * @version 1.0
 * @date 2020/6/12 16:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Teacher implements  Comparable<Teacher>{

    private String id;


    private String name;


    private Date birthday;

    private String gender;

    @Override
    public int compareTo(Teacher o) {
        return this.name.compareTo(o.name);
    }
}
