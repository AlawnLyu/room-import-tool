/**
 * @author LYU
 * @create 2017年12月13日 12:06
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "tfs", url = "${room.tfsServer}")
public interface TfsRemoteService {

    @RequestMapping(value = "/v1/tfs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    String updatePic(@RequestBody MultipartFile file, @RequestParam(value = "suffix", defaultValue = ".jpg") String suffix, @RequestParam(value = "simple_name", defaultValue = "0") Integer simple_name);

    @RequestMapping(value = "/v1/tfs/{fileName}", method = RequestMethod.DELETE)
    void deletePic(@PathVariable("fileName") String fileName);
}
