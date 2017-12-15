/**
 * @author LYU
 * @create 2017年12月12日 14:20
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.dao.impl;

import com.wtown.util.dao.RoomProductDao;
import com.wtown.util.dao.mapper.RoomProductMapper;
import com.wtown.util.entity.room.Room_product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoomProductDaoImpl implements RoomProductDao {

    @Autowired
    private RoomProductMapper mapper;

    @Override
    public Room_product getOne(String rt_code) {
        return mapper.getOne(rt_code);
    }

    @Override
    public void insert(Room_product roomProduct) {
        mapper.insert(roomProduct);
    }

    @Override
    public void update(Room_product roomProduct) {
        mapper.update(roomProduct);
    }
}
