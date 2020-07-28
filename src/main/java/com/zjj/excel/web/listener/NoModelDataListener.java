package com.zjj.excel.web.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.NonWritableChannelException;
import java.util.*;

/**
 * @author zhijiaju
 * @version 1.0
 * @date 2020/7/27 15:32
 */
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoModelDataListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();

    private static final Map<Integer, String> fields=new LinkedHashMap<>();
    private static final List<Map<String, String>> maps=new ArrayList<>();

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        Map<String, String> map=new LinkedHashMap<>();
        Set<Integer> keySet = fields.keySet();
        for (Integer key:keySet){
            map.put(fields.get(key),data.get(key));
        }
        maps.add(map);
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        fields.putAll(headMap);
        Set<Integer> keySet = headMap.keySet();
        LOGGER.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        System.out.println(maps);
        LOGGER.info("所有数据解析完成！");

    }

    private void saveData() {
        System.out.println("保存数据到数据中");
    }
}
