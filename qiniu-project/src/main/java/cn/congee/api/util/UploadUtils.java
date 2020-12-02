package cn.congee.api.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
/**
 * @Author: yang
 * @Date: 2020-12-02 18:18
 */
@Component
public class UploadUtils {

    private static final Logger log = LoggerFactory.getLogger(UploadUtils.class);

    @Value("${qiniu.access-key}")
    private String QINIUI_ACCESS_KEY;

    @Value("${qiniu.secret-key}")
    private String QINIU_SECRET_KEY;

    @Value("${qiniu.bucket}")
    private String QINIU_BUCKET;

    @Value("${qiniu.domain-of-bucket}")
    private String QINIU_DOMAIN_OF_BUCKET;

    private static String EXTERNAL_ACCESS_KEY;
    private static String EXTERNAL_SECRET_KEY;
    private static String EXTERNAL_BUCKET;
    private static String EXTERNAL_CHAIN_DOMAIN_NAME;

    @PostConstruct
    private void init(){
        EXTERNAL_ACCESS_KEY = QINIUI_ACCESS_KEY;
        EXTERNAL_SECRET_KEY = QINIU_SECRET_KEY;
        EXTERNAL_BUCKET = QINIU_BUCKET;
        EXTERNAL_CHAIN_DOMAIN_NAME = QINIU_DOMAIN_OF_BUCKET;
    }

    /**
     * 七牛云普通文件上传
     * @param localFilePath
     * @return
     */
    public static HashMap<String, Object> uploadFile(String localFilePath){
        HashMap<String, Object> map = new HashMap<>();
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = EXTERNAL_ACCESS_KEY;
        String secretKey = EXTERNAL_SECRET_KEY;
        String bucket = EXTERNAL_BUCKET;
        //localFilePath = "/home/swifthealth/图片/small4bdc01d290838558dfaea5f390b287121593277398.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        DefaultPutRet putRet = null;
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println("putRet.key: " + putRet.key);
            System.out.println("putRet.hash: " + putRet.hash);
            String url = "http://" + UploadUtils.EXTERNAL_CHAIN_DOMAIN_NAME + "/" + putRet.hash;
            map.put(putRet.key, "http://" + UploadUtils.EXTERNAL_CHAIN_DOMAIN_NAME + "/" + putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
                ex2.printStackTrace();
                log.error("文件上传失败: " + ex2.getMessage());
            }finally {
                log.info("文件上传成功外链为: " + "http://" + UploadUtils.EXTERNAL_CHAIN_DOMAIN_NAME + map.get(putRet.key));
            }
        }
        return map;
    }

    /**
     * 字节数组上传
     * @return
     */
    public static HashMap<String, Object> uploadFileAndByte(){
        HashMap<String, Object> map = new HashMap<>();
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = EXTERNAL_ACCESS_KEY;
        String secretKey = EXTERNAL_SECRET_KEY;
        String bucket = EXTERNAL_BUCKET;
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        DefaultPutRet putRet = null;
        try {
            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(uploadBytes, key, upToken);
                //解析上传成功的结果
                putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println("putRet.key: " + putRet.key);
                System.out.println("putRet.hash: " + putRet.hash);
                map.put(putRet.key, "http://" + UploadUtils.EXTERNAL_CHAIN_DOMAIN_NAME + "/" + putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                    ex2.printStackTrace();
                    log.error("文件上传失败: " + ex2.getMessage());
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
            ex.printStackTrace();
            log.error("文件上传失败: " + ex.getMessage());
        }
        return map;
    }


}