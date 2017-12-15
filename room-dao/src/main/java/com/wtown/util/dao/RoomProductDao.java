/**
 * @author LYU
 * @create 2017-12-12-14:20
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.dao;

import com.wtown.util.entity.room.Room_product;

public interface RoomProductDao {
    Room_product getOne(String rt_code);

    void insert(Room_product roomProduct);

    void update(Room_product roomProduct);
}
