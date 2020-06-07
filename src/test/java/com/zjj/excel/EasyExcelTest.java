package com.zjj.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.zjj.excel.domain.Student;
import com.zjj.excel.listener.StudentReadListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EasyExcelTest {
    @Test
    public void testRead(){
        /**
         * String pathName, 文件路径
         * Class head,每一行数据要接收的对象（接收数据的对象）
         * ReadListener readListener 监听器 每读一行数据 都会去调用invoke方法（可以将数据存储到数据库中）
         */
        //获取一个工作簿
        ExcelReaderBuilder readWorkBook = EasyExcel.read("学员信息表.xlsx", Student.class, new StudentReadListener());
        //从第三行开始读去数据
        readWorkBook.headRowNumber(3);
        //获取第一个一个工作表 （sheet）
        ExcelReaderSheetBuilder sheet = readWorkBook.sheet().sheetNo(0);

        //读取数据
        sheet.doRead();

    }

    @Test
    public void testWrite(){
        //获取一个工作簿
        ExcelWriterBuilder writeWorkBook = EasyExcel.write("学员信息表-write.xlsx", Student.class);
        //获取一个工作表 （sheet）
        ExcelWriterSheetBuilder sheet = writeWorkBook.sheet();
        List<Student> list = initData();
        //写入数据
        sheet.doWrite(list);
    }
    private static List<Student> initData() {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student data = new Student();
            data.setName("学号00110" + i);
            data.setBirthday(new Date());
            data.setGender("男");
            students.add(data);
        }
        return students;
    }
}
