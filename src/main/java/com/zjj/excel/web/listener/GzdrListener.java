package com.zjj.excel.web.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.zjj.excel.service.GzdrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhijiaju
 * @version 1.0
 * @date 2020/7/27 14:55
 */
public class GzdrListener  extends AnalysisEventListener<Map<Integer, String>> {
    private final static Logger logger = LoggerFactory.getLogger(GzdrListener.class);
    /**
     * 每隔128条存储数据库，然后清理list ，方便内存回收
     */

    private static final int BATCH_COUNT = 128;
    private int ztId;
    private List<String> errMessage;
    private GzdrService gzdrService;
    List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
    private String uuid;
    private String qj;
    private String gzlbId;

    public GzdrListener(int ztId, GzdrService gzdrService, String uuid, String qj, String gzlbId) {
        this.ztId = ztId;
        this.gzdrService = gzdrService;
        this.uuid = uuid;
        this.qj = qj;
        this.gzlbId = gzlbId;
        errMessage = new ArrayList<>();
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        //logger.info("解析到一条头数据：{}, currentRowHolder: {}", headMap.toString(), context.readRowHolder().getRowIndex());
        list.add(headMap);
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
       // logger.info("解析到一条数据：{}, currentRowHolder: {}", data.toString(), context.readRowHolder().getRowIndex());
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            errMessage.addAll(saveData());
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        errMessage.addAll(saveData());
        //logger.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private List<String> saveData() {
        logger.info("{}条数据，开始存储数据库！,ztId:{}  ", list.size(), ztId);
        return gzdrService.save(list, ztId, uuid, qj, gzlbId);
    }

    public List<String> getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(List<String> errMessage) {
        this.errMessage = errMessage;
    }

    public GzdrService getGzdrService() {
        return gzdrService;
    }

    public void setGzdrService(GzdrService gzdrService) {
        this.gzdrService = gzdrService;
    }


}
