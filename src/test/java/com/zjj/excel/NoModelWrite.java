package com.zjj.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhijiaju
 * @version 1.0
 * @date 2020/7/27 21:06
 */
public class NoModelWrite {

    /**
     * 不创建对象的写
     */
    @Test
    public void noModelWrite() {
        // 写法1
        String fileName = "C:\\Users\\Administrator\\Desktop\\" + "noModelWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).head(head()).sheet("模板").doWrite(dataList());
    }

    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("字符串" + System.currentTimeMillis());
        List<String> head1 = new ArrayList<String>();
        head1.add("数字" + System.currentTimeMillis());
        List<String> head2 = new ArrayList<String>();
        head2.add("日期" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (int i = 0; i < 10; i++) {
            List<Object> data = new ArrayList<Object>();
            data.add("字符串" + i);
            data.add(new Date());
            data.add(0.56);
            list.add(data);
        }
        return list;
    }
}
