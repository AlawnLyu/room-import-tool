/**
 * @author LYU
 * @create 2017-12-14-15:14
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.feign;

import feign.Param;
import feign.RequestLine;
import org.springframework.web.multipart.MultipartFile;

public interface TfsFeign {
    @RequestLine("POST /v1/tfs?suffix={suffix}&simple_name={simple_name}")
    String update(MultipartFile file, @Param("suffix") String suffix, @Param("simple_name") Integer simple_name);

    @RequestLine("DELETE /v1/tfs/{fileName}")
    void delete(@Param("fileName") String fileName);
}
