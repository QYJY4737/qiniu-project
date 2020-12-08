package cn.congee.api.controller;


import cn.congee.api.common.BaseResult;
import cn.congee.api.service.QiNiuService;
import cn.congee.api.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
/**
 * @Author: yang
 * @Date: 2020-12-02 18:19
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private QiNiuService qiNiuService;

    @PostMapping("/uploadByFile")
    public BaseResult<HashMap<String, Object>> uploadByFile(@RequestBody MultipartFile file){
        return new BaseResult<>(qiNiuService.uploadByFile(file));
    }

    @PostMapping("/uploadByStream")
    public BaseResult<HashMap<String, Object>> uploadByStream(@RequestBody MultipartFile file){
        try {
            return new BaseResult<>(this.qiNiuService.uploadByStream(new FileInputStream(FileUtils.multipartFileToFile(file))));
        }catch (FileNotFoundException fe){
            fe.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/deleteByKey/{key}")
    public BaseResult deleteByKey(@PathVariable("key")String key){
        return new BaseResult(qiNiuService.deleteByKey(key));
    }

}
