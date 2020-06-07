package com.zjj.excel.service.impl;

import com.zjj.excel.domain.Student;
import com.zjj.excel.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {


    @Override
    public void readExcel(List<Student> students) {
        for (Student student:students){
            System.out.println(student);
        }

    }
}
