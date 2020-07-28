package com.zjj.excel.web.controller;

import com.alibaba.excel.EasyExcel;
import com.zjj.excel.service.GzdrService;
import com.zjj.excel.web.listener.GzdrListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author zhijiaju
 * @version 1.0
 * @date 2020/7/27 14:54
 */
@Controller
@RequestMapping("/gzdr")
public class GzdrController {

    private GzdrService gzdrService;

    public HashMap<String, Object> upload(@RequestParam(value = "file") MultipartFile file,
                                          @RequestParam(value = "ztId") int ztId,
                                          @RequestParam(value = "qj") String qj,
                                          @RequestParam(value = "gzlbId") String gzlbId) throws IOException {

        gzdrService.init(ztId, qj, gzlbId);
        //生成uuid
        String uuid = UUID.randomUUID().toString();
        GzdrListener gzdrListener = new GzdrListener(ztId, gzdrService, uuid, qj, gzlbId);
        EasyExcel.read(file.getInputStream(), gzdrListener).sheet("导入模板").doReadSync();
        HashMap<String, Object> hashMap = new HashMap<>();
        List<String> errMessage = gzdrListener.getErrMessage();
        if (errMessage.isEmpty()) {
            hashMap.put("success", true);
        } else {
            hashMap.put("success", false);
            hashMap.put("errMessage", errMessage);
        }
        return hashMap;
    }

}
