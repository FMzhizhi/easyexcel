package com.zjj.excel.service;

import java.util.List;
import java.util.Map;

/**
 * @author zhijiaju
 * @version 1.0
 * @date 2020/7/27 14:56
 */
public interface GzdrService {
    List<String> save(List<Map<Integer, String>> list, int ztId, String uuid, String qj, String gzlbId);

    void init(int ztId, String qj, String gzlbId);
}
