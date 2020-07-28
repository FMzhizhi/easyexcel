package com.zjj.excel.web.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.zjj.excel.domain.Student;
import com.zjj.excel.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class WebStudentListener extends AnalysisEventListener<Student> {

    @Autowired
    private StudentService studentService;

    List<Student> list=new ArrayList<>();


    @Override
    public void invoke(Student data, AnalysisContext context) {

        list.add(data);
        if (list.size() %5 == 0){
            studentService.readExcel(list);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("为什么啊啊啊啊啊啊 ");

    }
}
