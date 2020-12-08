package cn.congee.api.service;

import com.qiniu.http.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;

/**
 * @Author: yang
 * @Date: 2020-12-08 10:07
 */
public interface QiNiuService {

    /**
     * 以文件形式上传
     *
     * @param file
     * @return
     */
    HashMap<String, Object> uploadByFile(MultipartFile file);

    /**
     * 以流形式上传
     *
     * @param stream
     * @return
     */
    HashMap<String, Object> uploadByStream(InputStream stream);

    /**
     * 根据key删除七牛云相关文件
     *
     * @param key
     * @return
     */
    Response deleteByKey(String key);

}
