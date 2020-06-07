package com.zjj.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.zjj.excel.domain.Student;
import jdk.nashorn.internal.ir.annotations.Ignore;

@Ignore
public class StudentReadListener extends AnalysisEventListener<Student> {

    //没读一行数据 调用一次
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        System.out.println("=="+student);
    }

    //读完全部的数据后调用该方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("全部读完了哈·········");

    }
}
