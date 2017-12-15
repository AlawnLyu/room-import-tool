/**
 * @author LYU
 * @create 2017-12-12-13:55
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.dao.mapper;

import com.wtown.util.entity.room.Room_product;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RoomProductMapper {

    @Select("SELECT * FROM room_product WHERE rt_code = #{rt_code};")
    Room_product getOne(@Param("rt_code") String rt_code);

    @Insert("INSERT INTO room_product(\n" +
            "  rt_code\n" +
            "  ,rt_name\n" +
            "  ,picurls\n" +
            "  ,room_type\n" +
            "  ,area\n" +
            "  ,stay_num\n" +
            "  ,envir\n" +
            "  ,nosmoke\n" +
            "  ,wififree\n" +
            "  ,addbed\n" +
            "  ,service\n" +
            "  ,min_price\n" +
            "  ,max_price\n" +
            "  ,scenic_code\n" +
            "  ,hotel_code\n" +
            "  ,sort\n" +
            "  ,status\n" +
            "  ,createtime\n" +
            ") VALUES (\n" +
            "  #{rt_code} -- rt_code - IN varchar(20)\n" +
            "  ,#{rt_name} -- rt_name - IN varchar(128)\n" +
            "  ,#{picurls}  -- picurls - IN varchar(255)\n" +
            "  ,#{room_type} -- room_type - IN varchar(128)\n" +
            "  ,#{area}   -- area - IN decimal(10,2)\n" +
            "  ,#{stay_num}   -- stay_num - IN int(11)\n" +
            "  ,#{envir}  -- envir - IN varchar(128)\n" +
            "  ,#{nosmoke}   -- nosmoke - IN tinyint(1)\n" +
            "  ,#{wififree}   -- wififree - IN tinyint(1)\n" +
            "  ,#{addbed}   -- addbed - IN tinyint(1)\n" +
            "  ,#{service}  -- service - IN text\n" +
            "  ,#{min_price}   -- min_price - IN decimal(10,2)\n" +
            "  ,#{max_price}   -- max_price - IN decimal(10,2)\n" +
            "  ,#{scenic_code} -- scenic_code - IN varchar(20)\n" +
            "  ,#{hotel_code} -- hotel_code - IN varchar(20)\n" +
            "  ,#{sort}   -- sort - IN int(11)\n" +
            "  ,#{status} -- status - IN tinyint(1)\n" +
            "  ,#{createtime} -- createtime - IN varchar(20)\n" +
            ");")
    void insert(Room_product roomProduct);

    @Update("UPDATE room_product\n" +
            "SET\n" +
            "  rt_name = #{rt_name} -- varchar(128)\n" +
            "  ,picurls = #{picurls} -- varchar(255)\n" +
            "  ,room_type = #{room_type} -- varchar(128)\n" +
            "  ,area = #{area} -- decimal(10,2)\n" +
            "  ,stay_num = #{stay_num} -- int(11)\n" +
            "  ,envir = #{envir} -- varchar(128)\n" +
            "  ,nosmoke = #{nosmoke} -- tinyint(1)\n" +
            "  ,wififree = #{wififree} -- tinyint(1)\n" +
            "  ,addbed = #{addbed} -- tinyint(1)\n" +
            "  ,service = #{service} -- text\n" +
            "  ,min_price = #{min_price} -- decimal(10,2)\n" +
            "  ,max_price = #{max_price} -- decimal(10,2)\n" +
            "  ,scenic_code = #{scenic_code} -- varchar(20)\n" +
            "  ,hotel_code = #{hotel_code} -- varchar(20)\n" +
            "  ,sort = #{sort} -- int(11)\n" +
            "  ,status = #{status} -- tinyint(1)\n" +
            "  ,updatetime = #{updatetime} -- varchar(20)\n" +
            "WHERE sqlid = #{sqlid};")
    void update(Room_product roomProduct);
}
