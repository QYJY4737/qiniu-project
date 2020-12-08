package cn.congee.api.util;


import cn.congee.api.config.QiNiuProperties;
import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
/**
 * @Author: yang
 * @Date: 2020-12-02 18:18
 */
@Slf4j
@Component
public class UploadUtils {

    @Autowired
    private Auth auth;
    // 定义七牛云上传的相关策略
    private StringMap putPolicy;
    @Autowired
    private UploadManager uploadManager;
    @Autowired
    private BucketManager bucketManager;
    @Autowired
    private QiNiuProperties qiniuProperties;


    /**
     * 以文件形式上传
     *
     * @param file
     * @return
     */
    public HashMap<String, Object> uploadByFile(MultipartFile file) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            Response response = this.uploadManager.put(FileUtils.multipartFileToFile(file), null, getUploadToken());
            int retry = 0;
            while (response.needRetry() && retry < 3) {
                response = this.uploadManager.put(FileUtils.multipartFileToFile(file), null, getUploadToken());
                retry++;
            }
            //解析结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            System.out.println("putRet.key: " + putRet.key);
            System.out.println("putRet.hash: " + putRet.hash);
            String url = "http://" + qiniuProperties.getPrefix() + "/" + putRet.hash;
            map.put(putRet.key, url);
        }catch (QiniuException qe){
            log.error("以文件形式上传报错: " + qe.getMessage());
            qe.printStackTrace();
        }finally {
            return map;
        }
    }

    /**
     * 以流形式上传
     *
     * @param stream
     * @return
     */
    public HashMap<String, Object> uploadByStream(InputStream stream) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            Response response = this.uploadManager.put(stream, null, getUploadToken(), null, null);
            int retry = 0;
            while (response.needRetry() && retry < 3) {
                response = this.uploadManager.put(stream, null, getUploadToken(), null, null);
                retry++;
            }
            //解析结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            System.out.println("putRet.key: " + putRet.key);
            System.out.println("putRet.hash: " + putRet.hash);
            String url = "http://" + qiniuProperties.getPrefix() + "/" + putRet.hash;
            map.put(putRet.key, url);
        }catch (QiniuException qe){
            log.error("以流形式上传报错: " + qe.getMessage());
            qe.printStackTrace();
        }finally {
            return map;
        }
    }

    /**
     * 根据key删除七牛云相关文件
     *
     * @param key
     * @return
     */
    public Response deleteByKey(String key) {
        Response response = null;
        try {
            response = bucketManager.delete(qiniuProperties.getBucket(), key);
            log.info("根据key删除七牛云相关文件返回结果为response=[{}]", JSON.toJSONString(response));
            int retry = 0;
            while (response.needRetry() && retry++ < 3) {
                response = bucketManager.delete(qiniuProperties.getBucket(), key);
            }
        }catch (QiniuException qe){
            log.error("根据key删除七牛云相关文件报错: " + qe.getMessage());
            qe.printStackTrace();
        }finally {
            return response;
        }
    }

    /**
     * 获取上传凭证
     *
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(qiniuProperties.getBucket(), null, 3600, putPolicy);
    }




}