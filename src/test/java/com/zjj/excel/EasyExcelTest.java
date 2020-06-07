package com.zjj.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.zjj.excel.domain.FillData;
import com.zjj.excel.domain.Student;
import com.zjj.excel.listener.StudentReadListener;
import org.junit.Test;

import java.util.*;

public class EasyExcelTest {
    @Test
    public void testRead() {
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
    public void testWrite() {
        //获取一个工作簿
        ExcelWriterBuilder writeWorkBook = EasyExcel.write("学员信息表-write.xlsx", Student.class);
        //获取一个工作表 （sheet）
        ExcelWriterSheetBuilder sheet = writeWorkBook.sheet();
        List<Student> list = initData();
        //写入数据
        sheet.doWrite(list);
    }


    //单组数据填充
    @Test
    public void testFill1() {
        //工作簿
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write("单组填充表单数据.xlsx", FillData.class).withTemplate("fill_data_template1.xlsx");
        ExcelWriterSheetBuilder sheet = excelWriterBuilder.sheet();
        FillData fillData = new FillData();
        fillData.setName("小明");
        fillData.setAge(18);
        sheet.doFill(fillData);


    }

    //多组组数据填充
    @Test
    public void testFill2() {
        //工作簿
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write("多组填充表单数据.xlsx", FillData.class).withTemplate("fill_data_template2.xlsx");
        ExcelWriterSheetBuilder sheet = excelWriterBuilder.sheet();

        List<FillData> fillData1s = initFillData();
        sheet.doFill(fillData1s);


    }

    //组合填充数据
    @Test
    public void testFill3(){
        //创建一个自己手动关闭流的工作簿
        ExcelWriter workBook = EasyExcel.write("组合填充表单数据.xlsx", FillData.class).withTemplate("fill_data_template3.xlsx").build();
        WriteSheet sheet = EasyExcel.writerSheet().build();
        // 填充配置，开启组合填充换行
        FillConfig fillConfig = FillConfig.builder().forceNewRow(true).build();
        //准备数据
        List<FillData> fillData1s = initFillData();
        Map<String,String> map=new HashMap<>();
        map.put("date","2020-05-23");
        map.put("total","122222");
        workBook.fill(fillData1s,fillConfig, sheet);

        workBook.fill(map,sheet);
        //手动关闭流
        workBook.finish();
    }

    //水平填充数据
    @Test
    public void testFill4(){
        //创建一个自己手动关闭流的工作簿
        ExcelWriter workBook = EasyExcel.write("水平填充表单数据.xlsx", FillData.class).withTemplate("fill_data_template4.xlsx").build();
        WriteSheet sheet = EasyExcel.writerSheet().build();
        // 填充配置，开启组合填充换行
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        //准备数据
        List<FillData> fillData1s = initFillData();
        Map<String,String> map=new HashMap<>();

        workBook.fill(fillData1s,fillConfig, sheet);


        //手动关闭流
        workBook.finish();
    }


    //综合填充数据
    @Test
    public void testFill5(){
        //创建一个自己手动关闭流的工作簿
        ExcelWriter workBook = EasyExcel.write("报表数据.xlsx", FillData.class).withTemplate("report_template.xlsx").build();
        WriteSheet sheet = EasyExcel.writerSheet().build();

        // ****** 准备数据 *******
        // 日期
        HashMap<String, String> dateMap = new HashMap<String, String>();
        dateMap.put("date", "2020-03-16");

        // 总会员数
        HashMap<String, String> totalCountMap = new HashMap<String, String>();
        dateMap.put("totalCount", "1000");

        // 新增员数
        HashMap<String, String> increaseCountMap = new HashMap<String, String>();
        dateMap.put("increaseCount", "100");

        // 本周新增会员数
        HashMap<String, String> increaseCountWeekMap = new HashMap<String, String>();
        dateMap.put("increaseCountWeek", "50");

        // 本月新增会员数
        HashMap<String, String> increaseCountMonthMap = new HashMap<String, String>();
        dateMap.put("increaseCountMonth", "100");


        // 新增会员数据
        List<Student> students = initData();
        // **** 准备数据结束****

        // 写入统计数据
        workBook.fill(dateMap, sheet);
        workBook.fill(totalCountMap, sheet);
        workBook.fill(increaseCountMap, sheet);
        workBook.fill(increaseCountWeekMap, sheet);
        workBook.fill(increaseCountMonthMap, sheet);
        // 写入新增会员
        workBook.fill(students, sheet);




        //手动关闭流
        workBook.finish();
    }

    private static List<FillData> initFillData() {
        ArrayList<FillData> fillDatas = new ArrayList<FillData>();
        for (int i = 0; i < 10; i++) {
            FillData fillData = new FillData();
            fillData.setName("学生" + i);
            fillData.setAge(10 + i);
            fillDatas.add(fillData);
        }
        return fillDatas;
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
