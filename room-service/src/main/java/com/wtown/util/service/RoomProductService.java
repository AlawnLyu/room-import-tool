/**
 * @author LYU
 * @create 2017-12-12-14:22
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.service;

import com.wtown.util.entity.dto.ResultDTO;
import com.wtown.util.entity.room.Room_product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomProductService {
    Room_product getOne(String rtCode);

    void updateRoom(Room_product roomProduct,List<Object> lo ,List<MultipartFile> imgs);

    void insertRoom(List<Object> lo,List<MultipartFile> imgs);

    String getSuffix(String fileName);

    String testFeign(MultipartFile file);
}
