package com.zjj.excel.web.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.zjj.excel.domain.Student;
import com.zjj.excel.listener.StudentReadListener;
import com.zjj.excel.web.listener.WebStudentListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private WebStudentListener webStudentListener;

    /**
     * 导入
     * @param uploadExcel
     * @return
     */
    @RequestMapping("/read")
    @ResponseBody
    public String readExcel(MultipartFile uploadExcel){
        try {
            ExcelReaderBuilder readWorkBook = EasyExcel.read(uploadExcel.getInputStream(), Student.class, webStudentListener);
            ExcelReaderSheetBuilder sheet = readWorkBook.sheet();
            sheet.doRead();
            return "success";

        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * 导出
     * @param response
     * @throws IOException
     */
    @RequestMapping("/write")
    @ResponseBody
    public void writeExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 防止中文乱码
        String fileName = URLEncoder.encode("测试111", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        //获取一个工作簿
        ExcelWriterBuilder writeWorkBook = EasyExcel.write(outputStream, Student.class);
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
