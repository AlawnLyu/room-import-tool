/**
 * @author LYU
 * @create 2017年12月12日 14:23
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.service.impl;

import com.wtown.util.common.FileUtil;
import com.wtown.util.config.FeignEncoder;
import com.wtown.util.convert.json.JsonDataUtil;
import com.wtown.util.dao.RoomProductDao;
import com.wtown.util.entity.enums.RoomTypeEnum;
import com.wtown.util.entity.room.Room_product;
import com.wtown.util.entity.dto.ResultDTO;
import com.wtown.util.feign.TfsFeign;
import com.wtown.util.feign.TfsRemoteService;
import com.wtown.util.service.RoomProductService;
import feign.Feign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RoomProductServiceImpl implements RoomProductService {

    public final static Logger logger = LoggerFactory.getLogger(RoomProductServiceImpl.class);

    private static final String splitReg = "\\|";

    @Value("${room.resourceLocation}")
    private String resourceLocation;

    @Value("${room.scenicCode}")
    private String scenicCode;

    @Value("${room.tfsServer}")
    private String tfsServer;

    @Autowired
    private RoomProductDao dao;

    @Autowired
    private TfsRemoteService tfsRemoteService;

    @Autowired
    private JsonDataUtil jsonDataUtil;

    @Override
    public Room_product getOne(String rtCode) {
        return dao.getOne(rtCode);
    }

    @Override
    public void updateRoom(Room_product roomProduct, List<Object> lo, List<MultipartFile> imgs) {
        dao.update(getRoomProduct(roomProduct, lo, imgs, 2));
    }

    @Override
    public void insertRoom(List<Object> lo, List<MultipartFile> imgs) {
        Room_product roomProduct = new Room_product();
        dao.insert(getRoomProduct(roomProduct, lo, imgs, 1));
    }

    @Override
    public String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Override
    public String testFeign(MultipartFile file) {
        TfsFeign service = Feign.builder()
                .encoder(new FeignEncoder())
                .target(TfsFeign.class, tfsServer);
        String resultDTO = service.update(file, getSuffix(file.getOriginalFilename()), 0);
        return resultDTO;
    }

    /**
     * @param roomProduct
     * @param lo
     * @param imgs        图片list
     * @param optType     操作类型：1 insert 2 update
     * @author: LYU
     * @description: 获取房型实体对象
     * @method: getRoomProduct
     * @return: com.wtown.util.entity.room.Room_product
     * @date: 2017年12月14日 09:33:12
     */
    private Room_product getRoomProduct(Room_product roomProduct, List<Object> lo, List<MultipartFile> imgs, int optType) {
        if (optType == 1) {
            roomProduct.setRt_code(String.valueOf(lo.get(1)));
        }
        roomProduct.setRt_name(String.valueOf(lo.get(2)));
        String picurls = optType == 1 ? "" : roomProduct.getPicurls();
        if (imgs != null) {
            if (optType == 2 && !"".equals(picurls)) {
                //删除旧图片
                deleteOldPic(picurls);
            }
            //上传图片到TFS服务器
            //picurls = updatePic(imgs);
            //上传图片到本地
            picurls = savePic(imgs);
        }
        roomProduct.setPicurls(picurls);

        String roomType = optType == 1 ? "" : roomProduct.getRoom_type();
        if (lo.get(16) != null) {
            for (RoomTypeEnum e :
                    RoomTypeEnum.values()) {
                if (e.getDesc().equals(lo.get(16))) {
                    roomType = e.getCode();
                }
            }
        }
        roomProduct.setRoom_type(roomType);

        roomProduct.setArea(null);

        Long stayNum = optType == 1 ? 2L : roomProduct.getStay_num();
        if (lo.get(4) != null) {
            try {
                stayNum = Long.valueOf(lo.get(4).toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        roomProduct.setStay_num(stayNum);
        String env = optType == 1 ? "不临水" : roomProduct.getEnvir();
        if (lo.get(6) != null) {
            if ("是".equals(lo.get(6).toString())) {
                env = "临水";
            } else {
                env = "不临水";
            }
        }
        roomProduct.setEnvir(env);
        Long noSmoke = optType == 1 ? 1L : roomProduct.getNosmoke();
        if (lo.get(7) != null) {
            if ("是".equals(lo.get(7).toString())) {
                noSmoke = 1L;
            } else {
                noSmoke = 0L;
            }
        }
        roomProduct.setNosmoke(noSmoke);
        Long wifi = optType == 1 ? 1L : roomProduct.getWififree();
        if (lo.get(8) != null) {
            if ("是".equals(lo.get(8).toString())) {
                wifi = 1L;
            } else {
                wifi = 0L;
            }
        }
        roomProduct.setWififree(wifi);
        Long addBed = optType == 1 ? 0L : roomProduct.getAddbed();
        if (lo.get(9) != null) {
            if ("是".equals(lo.get(9).toString())) {
                addBed = 1L;
            } else {
                addBed = 0L;
            }
        }
        roomProduct.setAddbed(addBed);

        String serviceHtml = getServiceHtml(lo, picurls);
        roomProduct.setService(serviceHtml);

        Double minPrice = optType == 1 ? 0.00 : roomProduct.getMin_price();
        if (lo.get(13) != null) {
            try {
                minPrice = Double.valueOf(lo.get(13).toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        roomProduct.setMin_price(minPrice);
        Double maxPrice = optType == 1 ? 0.00 : roomProduct.getMax_price();
        if (lo.get(13) != null) {
            try {
                maxPrice = Double.valueOf(lo.get(14).toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        roomProduct.setMax_price(maxPrice);
        roomProduct.setScenic_code(scenicCode);
        String hotelCode = optType == 1 ? "" : roomProduct.getHotel_code();
        if (lo.get(15) != null) {
            hotelCode = lo.get(15).toString();
        }
        roomProduct.setHotel_code(hotelCode);
        Long sort = optType == 1 ? 1L : roomProduct.getSort();
        if (lo.get(17) != null) {
            try {
                sort = Long.valueOf(lo.get(17).toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        roomProduct.setSort(sort);
        Long status = optType == 1 ? 1L : roomProduct.getStatus();
        if (lo.get(18) != null) {
            try {
                status = Long.valueOf(lo.get(18).toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        roomProduct.setStatus(status);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (optType == 1) {
            roomProduct.setCreatetime(df.format(new Date()));
        } else {
            roomProduct.setUpdatetime(df.format(new Date()));
        }
        return roomProduct;
    }

    private String getServiceHtml(List<Object> lo, String picurls) {
        String html = "";
        html += getDetailTableHtml(lo);
        if (lo.get(19) != null && lo.get(19).toString().length() > 0) {
            html += getExtarServHtml(lo.get(19).toString());
        }
        html += getPicHtml(picurls);
        return html;
    }

    private String getDetailTableHtml(List<Object> lo) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        sb.append("&lt;table style=&quot;color:#895A47;font-family:'Microsoft Yahei';font-size:14px;background-color:#FFFFFF;&quot;&gt;\n" +
                "\t&lt;tbody&gt;\n" +
                "\t\t&lt;tr class=&quot;firstRow&quot;&gt;");

        if (lo.get(2) != null) {
            sb.append("&lt;td width=&quot;307&quot; valign=&quot;top&quot; align=&quot;left&quot;&gt;\n" +
                    "\t\t\t\t&lt;span style=&quot;font-size:14px;font-family:微软雅黑, 'Microsoft YaHei';&quot;&gt;&lt;span style=&quot;color:#333333;&quot;&gt;房型：&lt;/span&gt;&lt;span style=&quot;color:#333333;&quot;&gt;" + lo.get(2) + "&lt;/span&gt;&lt;/span&gt; \n" +
                    "\t\t\t&lt;/td&gt;");
            count++;
        }
        if (lo.get(3) != null) {
            sb.append("&lt;td width=&quot;307&quot; valign=&quot;top&quot; align=&quot;left&quot;&gt;\n" +
                    "\t\t\t\t&lt;span style=&quot;font-size:14px;font-family:微软雅黑, 'Microsoft YaHei';&quot;&gt;&lt;span style=&quot;color:#333333;&quot;&gt;面积：&lt;/span&gt;&lt;span style=&quot;color:#333333;&quot;&gt;" + lo.get(3) + "㎡&lt;/span&gt;&lt;/span&gt; \n" +
                    "\t\t\t&lt;/td&gt;");
            count++;
        }
        if (lo.get(4) != null) {
            sb.append("&lt;td width=&quot;307&quot; valign=&quot;top&quot; align=&quot;left&quot;&gt;\n" +
                    "\t\t\t\t&lt;span style=&quot;font-size:14px;font-family:微软雅黑, 'Microsoft YaHei';color:#333333;&quot;&gt;入住人数：" + lo.get(4) + "&lt;/span&gt; \n" +
                    "\t\t\t&lt;/td&gt;");
            count++;
        }
        sb.append(getTableTrEnd(count));
        if (lo.get(5) != null) {
            sb.append(getTableTrStart(count));
            sb.append("&lt;td width=&quot;307&quot; valign=&quot;top&quot; align=&quot;left&quot;&gt;\n" +
                    "\t\t\t\t&lt;span style=&quot;font-size:14px;font-family:微软雅黑, 'Microsoft YaHei';color:#333333;&quot;&gt;&lt;span style=&quot;color:#333333;&quot;&gt;景观：" + lo.get(5) + "&lt;/span&gt;&lt;/span&gt; \n" +
                    "\t\t\t&lt;/td&gt;");
            count++;
        }
        sb.append(getTableTrEnd(count));
        if (lo.get(7) != null) {
            sb.append(getTableTrStart(count));
            sb.append("&lt;td width=&quot;307&quot; valign=&quot;top&quot; align=&quot;left&quot;&gt;\n" +
                    "\t\t\t\t&lt;span style=&quot;font-size:14px;font-family:微软雅黑, 'Microsoft YaHei';color:#333333;&quot;&gt;是否无烟房：" + lo.get(7) + "&lt;/span&gt; \n" +
                    "\t\t\t&lt;/td&gt;");
            count++;
        }
        sb.append(getTableTrEnd(count));
        if (lo.get(8) != null) {
            sb.append(getTableTrStart(count));
            sb.append("&lt;td width=&quot;307&quot; valign=&quot;top&quot; align=&quot;left&quot;&gt;\n" +
                    "\t\t\t\t&lt;span style=&quot;font-size:14px;font-family:微软雅黑, 'Microsoft YaHei';color:#333333;&quot;&gt;是否免费宽带：" + lo.get(8) + "&lt;/span&gt; \n" +
                    "\t\t\t&lt;/td&gt;");
            count++;
        }
        sb.append(getTableTrEnd(count));
        if (lo.get(9) != null) {
            sb.append(getTableTrStart(count));
            sb.append("&lt;td valign=&quot;top&quot; colspan=&quot;1&quot; rowspan=&quot;1&quot; align=&quot;left&quot;&gt;\n" +
                    "\t\t\t\t&lt;span style=&quot;font-size:14px;font-family:微软雅黑, 'Microsoft YaHei';color:#333333;&quot;&gt;是否可加床：" + lo.get(9) + "&lt;/span&gt; \n" +
                    "\t\t\t&lt;/td&gt;");
            count++;
        }
        sb.append(getTableTrEnd(count));
        if (lo.get(10) != null) {
            sb.append(getTableTrStart(count));
            sb.append("&lt;td valign=&quot;top&quot; colspan=&quot;1&quot; rowspan=&quot;1&quot; align=&quot;left&quot;&gt;\n" +
                    "\t\t\t\t&lt;span style=&quot;font-size:14px;font-family:微软雅黑, 'Microsoft YaHei';color:#333333;&quot;&gt;&lt;span style=&quot;color:#333333;font-family:微软雅黑, 'Microsoft YaHei';font-size:14px;line-height:21px;background-color:#FFFFFF;&quot;&gt;总楼层&lt;/span&gt;：" + lo.get(10) + "&lt;/span&gt; \n" +
                    "\t\t\t&lt;/td&gt;");
            count++;
        }
        sb.append(getTableTrEnd(count));
        if (lo.get(11) != null) {
            sb.append(getTableTrStart(count));
            sb.append("&lt;td valign=&quot;top&quot; colspan=&quot;1&quot; rowspan=&quot;1&quot; align=&quot;left&quot;&gt;\n" +
                    "\t\t\t\t&lt;span style=&quot;font-size:14px;font-family:微软雅黑, 'Microsoft YaHei';color:#333333;&quot;&gt;&lt;span style=&quot;color:#333333;font-family:微软雅黑, 'Microsoft YaHei';font-size:14px;line-height:21px;background-color:#FFFFFF;&quot;&gt;可选楼层&lt;/span&gt;：" + lo.get(11) + "&lt;/span&gt; \n" +
                    "\t\t\t&lt;/td&gt;");
            count++;
        }
        sb.append(getTableTrEnd(count));
        if (lo.get(12) != null) {
            sb.append(getTableTrStart(count));
            sb.append("&lt;td valign=&quot;top&quot; colspan=&quot;3&quot; rowspan=&quot;1&quot; align=&quot;left&quot;&gt;\n" +
                    "\t\t\t\t&lt;span style=&quot;line-height:24px;font-size:14px;font-family:微软雅黑, 'Microsoft YaHei';color:#333333;&quot;&gt;房间描述：" + lo.get(12) + "&lt;/span&gt; \n" +
                    "\t\t\t&lt;/td&gt;");
            count++;
        }
        sb.append("&lt;/tr&gt;");
        sb.append("\t&lt;/tbody&gt;\n" +
                "&lt;/table&gt;");
        return sb.toString();
    }

    private String getTableTrEnd(int count) {
        if (count > 0 && count % 3 == 0) {
            return "&lt;/tr&gt;";
        }
        return "";
    }

    private String getTableTrStart(int count) {
        if (count > 0 && count % 3 == 0) {
            return "&lt;tr&gt;";
        }
        return "";
    }

    private String getExtarServHtml(String extraServ) {
        StringBuilder sb = new StringBuilder();
        String[] serv = extraServ.split(splitReg);
        sb.append("&lt;p style=&quot;color:#895A47;font-family:'Microsoft Yahei';font-size:14px;background-color:#FFFFFF;&quot;&gt;\n" +
                "\t&lt;span style=&quot;font-family:微软雅黑, 'Microsoft YaHei';color:#333333;&quot;&gt;增 值 服 务：&lt;/span&gt; \n" +
                "&lt;/p&gt;");
        for (int i = 0; i < serv.length; i++) {
            sb.append("&lt;p style=&quot;color:#895A47;font-family:'Microsoft Yahei';font-size:14px;background-color:#FFFFFF;&quot;&gt;\n" +
                    "\t&lt;span style=&quot;font-family:微软雅黑, 'Microsoft YaHei';&quot;&gt;&lt;span style=&quot;color:#333333;&quot;&gt;" + (i + 1) + ".&lt;/span&gt;&lt;span style=&quot;color:#333333;&quot;&gt;" + serv[i] + "；&lt;/span&gt;&lt;/span&gt; \n" +
                    "&lt;/p&gt;");
        }
        sb.append("&lt;p style=&quot;text-align:center;color:#895A47;font-family:'Microsoft Yahei';font-size:14px;background-color:#FFFFFF;&quot;&gt;\n" +
                "\t&lt;br /&gt;\n" +
                "&lt;/p&gt;");
        return sb.toString();
    }

    private String getPicHtml(String picurls) {
        StringBuilder sb = new StringBuilder();
        sb.append("&lt;p style=&quot;text-align:center;color:#895A47;font-family:'Microsoft Yahei';font-size:14px;background-color:#FFFFFF;&quot;&gt;\n" +
                "\t&lt;br /&gt;\n" +
                "&lt;/p&gt;");
        if (!"".equals(picurls)) {
            String[] pics = picurls.split(splitReg);
            for (int i = 0; i < pics.length; i++) {
                sb.append("&lt;p style=&quot;text-align:center;&quot;&gt;\n" +
                        "\t&lt;img src=&quot;http://www.wtown.com.cn/Upload/Reservation/" + pics[i] + "&quot; alt=&quot;&quot; /&gt; \n" +
                        "&lt;/p&gt;");
                //sb.append("&lt;p style=&quot;text-align:center;&quot;&gt;\n" +
                //        "\t&lt;img src=&quot;" + tfsServer + "/v1/tfs/" + pics[i] + "&quot; alt=&quot;&quot; /&gt; \n" +
                //        "&lt;/p&gt;");
            }
            sb.append("&lt;p style=&quot;text-align:center;color:#895A47;font-family:'Microsoft Yahei';font-size:14px;background-color:#FFFFFF;&quot;&gt;\n" +
                    "\t&lt;br /&gt;\n" +
                    "&lt;/p&gt;");
        }
        return sb.toString();
    }

    private void deleteOldPic(String picurls) {
        if (!"".equals(picurls)) {
            String[] pics = picurls.split(splitReg);
            for (int i = 0; i < pics.length; i++) {
                String fileName = resourceLocation + pics[i];
                if (logger.isDebugEnabled()) {
                    logger.debug("删除文件名:" + fileName);
                }
                FileUtil.deleteFile(fileName);
                //删除TFS服务器中的图片
                //tfsRemoteService.deletePic(fileName);
            }
        }
    }

    /**
     * @param imgs
     * @author: LYU
     * @description: 通过feign上传图片到tfs服务器（目前不使用）
     * @method: updatePic
     * @return: java.lang.String
     * @date: 2017年12月13日 16:48:44
     */
    private String updatePic(List<MultipartFile> imgs) {
        String picurls = "";
        for (int i = 0; i < imgs.size(); i++) {
            String suffix = getSuffix(imgs.get(i).getOriginalFilename());
            String result = tfsRemoteService.updatePic(imgs.get(i), suffix, 0);
            if (logger.isDebugEnabled()) {
                logger.debug("上传图片返回结果：{}", result);
            }
            //还需解析返回json字符串，并组装picurls
            try {
                ResultDTO resultDTO = jsonDataUtil.readObject(result, ResultDTO.class);
                picurls += resultDTO.getTFS_FILE_NAME() + "|";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (picurls.length() > 0) {
            picurls = picurls.substring(0, picurls.length() - 1);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("上传图片组装结果：{}", picurls);
        }
        return picurls;
    }

    private String savePic(List<MultipartFile> imgs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < imgs.size(); i++) {
            // 获取文件名
            String fileName = imgs.get(i).getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            if (logger.isDebugEnabled()) {
                logger.debug("上传的文件名为：" + fileName);
                logger.debug("上传的后缀名为：" + suffixName);
            }
            // 文件上传后的路径
            String filePath = resourceLocation;
            fileName = UUID.randomUUID() + suffixName;
            if (logger.isDebugEnabled()) {
                logger.debug("上传的路径为：" + filePath);
                logger.debug("上传后的文件名为：" + fileName);
            }
            File dest = new File(filePath + fileName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                imgs.get(i).transferTo(dest);
                sb.append(fileName);
                sb.append("|");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (sb.length() > 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("生成的文件名字符串：" + sb.toString().substring(0, sb.length() - 1));
            }
            return sb.toString().substring(0, sb.length() - 1);
        } else {
            return "";
        }
    }
}
