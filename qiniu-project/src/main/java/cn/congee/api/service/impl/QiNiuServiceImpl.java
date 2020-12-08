package cn.congee.api.service.impl;

import cn.congee.api.service.QiNiuService;
import cn.congee.api.util.UploadUtils;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;

/**
 * @Author: yang
 * @Date: 2020-12-08 10:08
 */
@Service
public class QiNiuServiceImpl implements QiNiuService {

    @Autowired
    private UploadUtils uploadUtils;

    @Override
    public HashMap<String, Object> uploadByFile(MultipartFile file) {
        return uploadUtils.uploadByFile(file);
    }

    @Override
    public HashMap<String, Object> uploadByStream(InputStream stream) {
        return uploadUtils.uploadByStream(stream);
    }

    @Override
    public Response deleteByKey(String key) {
        return uploadUtils.deleteByKey(key);
    }
}
