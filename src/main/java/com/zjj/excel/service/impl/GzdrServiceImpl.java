package com.zjj.excel.service.impl;

import com.zjj.excel.service.GzdrService;

import java.util.List;
import java.util.Map;

/**
 * @author zhijiaju
 * @version 1.0
 * @date 2020/7/27 15:09
 */
public class GzdrServiceImpl implements GzdrService {
    @Override
    public List<String> save(List<Map<Integer, String>> list, int ztId, String uuid, String qj, String gzlbId) {
        System.out.println("保存数据到数据库中。。。。。。。。。。。");
        return null;
    }

    @Override
    public void init(int ztId, String qj, String gzlbId) {
        System.out.println("初始化数据。。。。。。。。。。。。");
    }
}
