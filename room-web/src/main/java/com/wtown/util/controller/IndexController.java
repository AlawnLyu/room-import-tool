/**
 * @author LYU
 * @create 2017年12月07日 15:51
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.controller;

import com.wtown.util.common.ImportExcelUtil;
import com.wtown.util.entity.room.Room_product;
import com.wtown.util.service.RoomProductService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    public final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private RoomProductService service;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/uploads", method = RequestMethod.POST)
    public @ResponseBody
    String uploads(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        InputStream in = null;
        List<List<Object>> listob = null;

        MultipartFile excel = multipartRequest.getFile("excel");
        List<MultipartFile> imgs = multipartRequest.getFiles("file");

        in = excel.getInputStream();
        listob = new ImportExcelUtil().getRoomListByExcel(in, excel.getOriginalFilename());
        in.close();

        for (int i = 0; i < listob.size(); i++) {
            List<Object> lo = listob.get(i);

            if (logger.isDebugEnabled()) {
                logger.debug("房型代码：{}", lo.get(1));
            }

            String rt_code = lo.get(1).toString();
            Room_product roomProduct = service.getOne(rt_code);
            List<MultipartFile> pic = null;
            for (int j = 0; j < imgs.size(); j++) {
                if (imgs.get(j).getOriginalFilename().contains(rt_code)) {
                    if (pic == null) {
                        pic = new ArrayList<>();
                    }
                    String fileSuffix = service.getSuffix(imgs.get(j).getOriginalFilename());
                    if (".jpg".equals(fileSuffix) || ".png".equals(fileSuffix)) {
                        pic.add(imgs.get(j));
                    }
                }
            }
            if (roomProduct != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("执行房型更新操作");
                }
                service.updateRoom(roomProduct, lo, pic);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("执行房型新增操作");
                }
                service.insertRoom(lo, pic);
            }
        }
        return "执行完成！";
    }

    @RequestMapping("/test")
    public @ResponseBody
    String test(String rt_code) {
        return service.getOne(rt_code).getRt_name();
    }

    @RequestMapping("/testtfs")
    public String testTfsServer() {
        return "testtfs";
    }

    @RequestMapping(value = "/testupdatetfs", method = RequestMethod.POST)
    public @ResponseBody
    String testUpdateTfs(@Param("file") MultipartFile file) {
        return service.testFeign(file);
    }
}
