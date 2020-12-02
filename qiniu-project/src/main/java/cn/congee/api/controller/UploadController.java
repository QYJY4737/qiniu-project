package cn.congee.api.controller;


import cn.congee.api.common.BaseResult;
import cn.congee.api.util.UploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
/**
 * @Author: yang
 * @Date: 2020-12-02 18:19
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    private final static Logger log = LoggerFactory.getLogger(UploadController.class);

    @PostMapping("/qiniu")
    public BaseResult<HashMap<String, Object>> uploadFile(@RequestBody MultipartFile file){
        String filename = file.getOriginalFilename();
        System.out.println("filename: " + filename);
        String localFilePath = "/home/swifthealth/图片/" + filename;
        System.out.println("localFilePath: " + localFilePath);
        HashMap<String, Object> map = UploadUtils.uploadFile(localFilePath);
        System.out.println("map: " + map.toString());
        BaseResult<HashMap<String, Object>> result = new BaseResult<>();
        if(CollectionUtils.isEmpty(map)){
            log.error("上传文件地址有误! ");
            result.setMsg("上传失败");
            result.setCode(-1);
        }
        result.setData(map);
        result.setCode(200);
        result.setMsg("上传成功");
        return result;
    }

}
