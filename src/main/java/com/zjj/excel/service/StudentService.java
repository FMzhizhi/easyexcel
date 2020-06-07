package com.zjj.excel.service;

import com.zjj.excel.domain.Student;

import java.util.List;

public interface StudentService {
    void readExcel(List<Student> students);
}
